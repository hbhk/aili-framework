package org.hbhk.aili.orm.server.handler;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.orm.server.annotation.ColumnTranslator;
import org.hbhk.aili.orm.server.annotation.NativeQuery;
import org.hbhk.aili.orm.server.annotation.NativeQuery.DEFAULT;
import org.hbhk.aili.orm.server.annotation.NativeSave;
import org.hbhk.aili.orm.server.annotation.NativeUpdate;
import org.hbhk.aili.orm.server.mapper.BaseRowMapper;
import org.hbhk.aili.orm.server.mapper.CommonBeanRowMapper;
import org.hbhk.aili.orm.server.mapper.DummyColumnTranslator;
import org.hbhk.aili.orm.server.mapper.UpperCaseColumnTranslator;
import org.hbhk.aili.orm.server.service.IDaoService;
import org.hbhk.aili.orm.server.service.IGetbrickTemplate;
import org.hbhk.aili.orm.server.service.impl.GetbrickTemplate;
import org.hbhk.aili.orm.server.surpport.ModelClassSupport;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;

public class NativeQueryHandler extends AbstractQueryHandler {

	@Autowired
	@Qualifier("getbrickTemplate")
	private IGetbrickTemplate getbrickTemplate = new GetbrickTemplate();

	public IGetbrickTemplate getGetbrickTemplate() {
		return getbrickTemplate;
	}

	public void setGetbrickTemplate(IGetbrickTemplate getbrickTemplate) {
		this.getbrickTemplate = getbrickTemplate;
	}

	public NativeQueryHandler(IDaoService daoService) {
		super(daoService);

	}

	public Object handleNativeQuery(NativeQuery nativeQuery,
			MethodInvocation invocation) {
		return handleNativeQueryNative(nativeQuery, invocation.getThis(),
				invocation.getMethod(), invocation.getArguments());
	}

	public Object handleNativeQuery(NativeQuery nativeQuery,
			ProceedingJoinPoint pjp) {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		return handleNativeQueryNative(nativeQuery, pjp.getThis(),
				ms.getMethod(), pjp.getArgs());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object handleNativeQueryNative(NativeQuery nativeQuery, Object obj,
			Method m, Object[] args) {
		Map<String, Object[]> paramsEx = getParamsEx(m, args);
		Map<String, Object> templateParams = new HashMap<String, Object>();
		for (String key : paramsEx.keySet())
			templateParams.put(key, paramsEx.get(key)[0]);

		Page page = getPage(args);
		boolean pagable = (page != null) || nativeQuery.pagable();

		String queryName = nativeQuery.value();
		if (queryName.equals("")) {
			if (!(obj instanceof ModelClassSupport)) {
				throw new RuntimeException("QueryName can not be empty");
			}
			ModelClassSupport mcs = (ModelClassSupport) obj;
			queryName += mcs.getModelClass().getSimpleName();
			queryName += "." + m.getName();
		}

		String queryStringWithName = getDynamicQuery(queryName, templateParams);
		List<Object> conditions = new ArrayList<Object>();
		String queryString = getNativeQuery(queryStringWithName, paramsEx,
				conditions);

		if (logger.isDebugEnabled()) {
			logger.debug("NativeQuery[{}] will be executed", queryName);
			logger.debug("{}", queryString);
		}

		Sort[] sorts = getSorts(args);
		RowMapper<?> rowMapper = getRowMapper(args);
		if (rowMapper == null && (!nativeQuery.model().equals(DEFAULT.class))) {
			ColumnTranslator t = null;
			try {
				if (!DummyColumnTranslator.class.equals(nativeQuery
						.translator())) {
					t = nativeQuery.translator().newInstance();
					t.setModelClass(nativeQuery.model());
				}
			} catch (Exception e) {
				// do nothing
			}
			rowMapper = new CommonBeanRowMapper(nativeQuery.model(), t,
					nativeQuery.alias());
		}
		if (rowMapper == null
				&& (nativeQuery.alias() == null
						|| nativeQuery.clazzes() == null
						|| nativeQuery.alias().length == 0 || nativeQuery
						.clazzes().length == 0))
			throw new IllegalArgumentException(
					"No return type definition found.");

		if (rowMapper == null
				&& nativeQuery.alias().length != nativeQuery.clazzes().length)
			throw new IllegalArgumentException(
					"Return alias and class definition are not matched.");

		if (rowMapper == null)
			rowMapper = new MapRowMapper(nativeQuery.alias(),
					nativeQuery.clazzes());

		if (sorts != null) {
			logger.debug("Query need be sorted with :" + Arrays.asList(sorts));
		}

		if (List.class.isAssignableFrom(m.getReturnType())) {
			if (pagable) {
				if (page != null)
					return daoService.findByNativeQuery(queryString,
							conditions.toArray(), sorts, page.getStart(),
							page.getSize(), rowMapper);
				else if (args[0] instanceof Integer
						&& args[1] instanceof Integer)
					return daoService.findByNativeQuery(queryString,
							conditions.toArray(), sorts, (Integer) args[0],
							(Integer) args[1], rowMapper);
				else
					throw new IllegalArgumentException(
							"Startindex and pagesize must be set for pagable query.");
			} else {
				return daoService.findByNativeQuery(queryString,
						conditions.toArray(), sorts, -1, -1, rowMapper);
			}
		} else if (Pagination.class.isAssignableFrom(m.getReturnType())) {
			if (pagable) {
				if (page != null)
					return daoService.findByNativeQuery(queryString,
							conditions.toArray(), sorts, page.getStart(),
							page.getSize(), nativeQuery.withGroupby(),
							rowMapper);
				else if (args[0] instanceof Integer
						&& args[1] instanceof Integer)
					return daoService.findByNativeQuery(queryString,
							conditions.toArray(), sorts, (Integer) args[0],
							(Integer) args[1], nativeQuery.withGroupby(),
							rowMapper);
				else
					throw new IllegalArgumentException(
							"Startindex and pagesize must be set for pagable query.");
			} else {
				throw new IllegalStateException("Please set pagable to true");
			}
		} else
			return daoService.findOneByNativeQuery(queryString,
					conditions.toArray(), rowMapper, sorts);
	}

	public int handleNativeUpdate(NativeUpdate nativeUpdate,
			MethodInvocation invocation) {
		return handleNativeUpdateNative(nativeUpdate, invocation.getThis(),
				invocation.getMethod(), invocation.getArguments());
	}

	public Object handleNativeSave(NativeSave nativeSave,
			ProceedingJoinPoint pjp) {
		return daoService.save(pjp.getArgs()[0]);
	}

	public Object handleSimpleUpdate(ProceedingJoinPoint pjp) {
		return daoService.update(pjp.getArgs()[0]);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object handleSimpleQuery(ProceedingJoinPoint pjp) {
		Object model = pjp.getArgs()[0];
		if (model == null) {
			return null;
		}
		Class<?> clazz = model.getClass();
		ColumnTranslator t = new UpperCaseColumnTranslator();
		t.setModelClass(clazz);
		RowMapper rowMapper = new CommonBeanRowMapper(clazz, t, new String[] {});
		return daoService.get(model, rowMapper);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object handleSimpleQueryPage(ProceedingJoinPoint pjp) {
		Object model = pjp.getArgs()[0];
		Page page = (Page) pjp.getArgs()[1];
		if (model == null) {
			return null;
		}
		Class<?> clazz = model.getClass();
		ColumnTranslator t = new UpperCaseColumnTranslator();
		t.setModelClass(clazz);
		RowMapper rowMapper = new CommonBeanRowMapper(clazz, t, new String[] {});
		return daoService.get(model, page, rowMapper);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object handleSimpleQueryOne(ProceedingJoinPoint pjp) {
		Object model = pjp.getArgs()[0];
		if (model == null) {
			return null;
		}
		Class<?> clazz = pjp.getArgs()[0].getClass();

		ColumnTranslator t = new UpperCaseColumnTranslator();
		t.setModelClass(clazz);
		RowMapper rowMapper = new CommonBeanRowMapper(clazz, t, new String[] {});
		return daoService.getOne(model, rowMapper);
	}

	public int handleNativeUpdate(NativeUpdate nativeUpdate,
			ProceedingJoinPoint pjp) {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		return handleNativeUpdateNative(nativeUpdate, pjp.getThis(),
				ms.getMethod(), pjp.getArgs());
	}

	private int handleNativeUpdateNative(NativeUpdate nativeUpdate, Object obj,
			Method m, Object[] args) {
		Map<String, Object[]> paramsEx = getParamsEx(m, args);
		Map<String, Object> templateParams = new HashMap<String, Object>();
		for (String key : paramsEx.keySet())
			templateParams.put(key, paramsEx.get(key)[0]);

		String queryName = nativeUpdate.value();
		if (queryName.equals("")) {
			if (!(obj instanceof ModelClassSupport))
				throw new RuntimeException("QueryName can not be empty");
			ModelClassSupport mcs = (ModelClassSupport) obj;
			queryName += mcs.getModelClass().getSimpleName();
			queryName += "." + m.getName();
		}

		String queryStringWithName = getDynamicQuery(queryName, templateParams);
		List<Object> conditions = new ArrayList<Object>();
		List<Class<?>> types = new ArrayList<Class<?>>();
		String queryString = getNativeUpdateQuery(queryStringWithName,
				paramsEx, conditions, types);

		logger.debug("Update[{}] will be executed.", queryString);
		return daoService.batchUpdateByNativeQuery(queryString,
				conditions.toArray(), types.toArray(new Class<?>[] {}));
	}

	private String getNativeQuery(String queryName,
			Map<String, Object[]> paramsEx, List<Object> conditions) {
		StringBuffer sb = new StringBuffer();
		boolean inParamName = false;
		StringBuffer paramNameSb = new StringBuffer();
		for (char c : queryName.toCharArray()) {
			if (c == ':') {
				if (inParamName)
					throw new RuntimeException("Wrong query.");
				inParamName = true;
			} else {
				if (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == ','
						|| c == ')') {
					if (inParamName) {
						inParamName = false;
						sb.append('?');
						conditions.add(getParamValueAndType(
								paramNameSb.toString(), paramsEx)[0]);
						paramNameSb = new StringBuffer();
					}
					sb.append(c);
				} else {
					if (inParamName) {
						paramNameSb.append(c);
					} else
						sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	private String getNativeUpdateQuery(String queryName,
			Map<String, Object[]> paramsEx, List<Object> conditions,
			List<Class<?>> types) {
		StringBuffer sb = new StringBuffer();
		boolean inParamName = false;
		StringBuffer paramNameSb = new StringBuffer();
		for (char c : queryName.toCharArray()) {
			if (c == ':') {
				if (inParamName)
					throw new RuntimeException("Wrong query.");
				inParamName = true;
			} else {
				if (c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == ','
						|| c == ')') {
					if (inParamName) {
						inParamName = false;
						sb.append('?');
						Object[] v = getParamValueAndType(
								paramNameSb.toString(), paramsEx);
						conditions.add(v[0]);
						types.add((Class<?>) v[1]);
						paramNameSb = new StringBuffer();
					}
					sb.append(c);
				} else {
					if (inParamName) {
						paramNameSb.append(c);
					} else
						sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	private RowMapper<?> getRowMapper(Object[] args) {
		RowMapper<?> rowMapper = null;
		for (Object arg : args) {
			if (arg instanceof RowMapper) {
				if (rowMapper == null)
					rowMapper = (RowMapper<?>) arg;
				else
					throw new IllegalArgumentException(
							"More than one definitions found for RowMapper.");
			}
		}
		return rowMapper;
	}

	private class MapRowMapper extends BaseRowMapper<Object> {
		public MapRowMapper(String[] alias, Class<?>[] clazzes) {
			this.alias = alias;
			this.clazzes = clazzes;
		}

		private String[] alias;
		private Class<?>[] clazzes;

		public Object mapRow(ResultSet rs, int index) throws SQLException {
			if (alias.length == 1) {
				return getResultFromRs(rs, alias[0], clazzes[0]);
			} else {
				Map<String, Object> result = new HashMap<String, Object>();
				for (int i = 0; i < alias.length; i++) {
					result.put(alias[i],
							getResultFromRs(rs, alias[i], clazzes[i]));
				}
				return result;
			}
		}
	}

	/**
	 * 
	 * @author 何波
	 * @Description: 查找对于模板生成sql语句
	 * @param queryName
	 * @param params
	 * @return String
	 * @throws
	 */
	protected String getDynamicQuery(String queryName,
			Map<String, Object> params) {
		return getbrickTemplate.setContextData(params, queryName);
	}
}
