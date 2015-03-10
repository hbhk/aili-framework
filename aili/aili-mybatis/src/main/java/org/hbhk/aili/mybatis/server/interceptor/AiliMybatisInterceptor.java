package org.hbhk.aili.mybatis.server.interceptor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.hbhk.aili.mybatis.server.interceptor.OffsetLimitInterceptor.BoundSqlSqlSource;
import org.hbhk.aili.mybatis.server.support.GnericInterfaceTypeContext;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.hbhk.aili.mybatis.share.model.BaseModel;
import org.hbhk.aili.mybatis.share.util.FieldColumn;
import org.hbhk.aili.mybatis.share.util.SqlUtil;

/**
 * 处理mybatis不支持泛型
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = {
		MappedStatement.class, Object.class, RowBounds.class,
		ResultHandler.class }) })
public class AiliMybatisInterceptor implements Interceptor {

	private static Map<String, Class<?>> modelClass = new ConcurrentHashMap<String, Class<?>>();

	private static List<Class<?>>  notModelClass = new ArrayList<Class<?>>();
	static{
		//notModelClass.add(Pagination.class);
	}
	private static List<String>  dealmethod = new ArrayList<String>();
	static{
		dealmethod.add("get");
		dealmethod.add("getById");
		dealmethod.add("getPage");
		dealmethod.add("deleteById");
		dealmethod.add("updateStatusById");
	}
	
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] queryArgs = invocation.getArgs();
		MappedStatement ms = (MappedStatement) queryArgs[0];
		String id = ms.getId();
		String className = id.substring(0,id.lastIndexOf(".")); 
		String methodName = id.substring(id.lastIndexOf(".")+1,id.length()); 
		Class<?> gnericInterfaceType = getGenericInterfaces(className);
		GnericInterfaceTypeContext.setType(gnericInterfaceType);
		Object parameter = queryArgs[1];
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql();
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
				boundSql.getParameterMappings(), boundSql.getParameterObject());
		// 将原有的BoundSql中的MetaParameter复制到新的BoundSql中
		copyMetaParameters(boundSql, newBoundSql);
		ms.getResultMaps().get(0).getPropertyResultMappings();
		MappedStatement newMs = copyFromMappedStatement(ms,
				new BoundSqlSqlSource(newBoundSql), gnericInterfaceType);
		queryArgs[0] = newMs;
		//清除threadLocal数据
		if(dealmethod.contains(methodName)){
			GnericInterfaceTypeContext.remove();
		}
		//解决分页一种方式,不再aop里面再调用查询
		return invocation.proceed();
		
	}

	private Class<?> getGenericInterfaces(String className) throws Exception {
		if (modelClass.containsKey(className)) {
			return modelClass.get(className);
		}
		Class<?> clazz = Class.forName(className);
		Type[] types = clazz.getGenericInterfaces();
		ParameterizedType pType = (ParameterizedType) types[0];
		Class<?> cls = (Class<?>) pType.getActualTypeArguments()[0];
		modelClass.put(className, cls);
		return cls;
	}

	private void copyMetaParameters(BoundSql lhs, BoundSql rhs) {
		for (ParameterMapping map : lhs.getParameterMappings()) {
			String key = map.getProperty();
			Object value = lhs.getAdditionalParameter(key);
			if (null != value) {
				rhs.setAdditionalParameter(key, value);
			}
		}
	}

	/**
	 * <p>
	 * 获取MappedStatement
	 * </p>
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms,
			SqlSource newSqlSource, Class<?> gnericInterfaceType) throws Exception {
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
				ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(ms.getKeyProperties() != null
				&& ms.getKeyProperties().length > 0 ? ms.getKeyProperties()[0]
				: null);

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());
		// setStatementResultMap()
		
		ResultMap resultMap = ms.getResultMaps().get(0);
		Class<?> type = resultMap.getType();
		String id = ms.getId();
		//方法名
		String methodName = id.substring(id.lastIndexOf(".")+1,id.length());
		if(!notModelClass.contains(type)){
			List<ResultMap> resultMaps = new ArrayList<ResultMap>();
			if(dealmethod.contains(methodName)){
				List<ResultMapping> resultMappings= getResultMapping(gnericInterfaceType, ms);
				//处理泛型
				ResultMap.Builder reBuilder = new ResultMap.Builder(
						ms.getConfiguration(), resultMap.getId(), gnericInterfaceType,
						resultMappings, resultMap.getAutoMapping());
				resultMap = reBuilder.build();
				resultMaps.add(resultMap);
				builder.resultMaps(resultMaps);
			}else {
				if(id.indexOf("!") < 0){
					if(type.isAssignableFrom(BaseModel.class) || type.isAssignableFrom(Pagination.class)){
						//处理属性和列名不一样的
						List<ResultMapping> resultMappings= getResultMapping(gnericInterfaceType, ms);
						ResultMap.Builder reBuilder = new ResultMap.Builder(
								ms.getConfiguration(), resultMap.getId(), gnericInterfaceType,
								resultMappings, resultMap.getAutoMapping());
						resultMap = reBuilder.build();
						resultMaps.add(resultMap);
						builder.resultMaps(resultMaps);
					}else{
						builder.resultMaps(ms.getResultMaps());
					}
				}else{
					builder.resultMaps(ms.getResultMaps());
				}
			}
		}else{
			builder.resultMaps(ms.getResultMaps());
		}
		builder.resultSetType(ms.getResultSetType());
		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
	
	private  List<ResultMapping> getResultMapping(Class<?> cls,MappedStatement ms){
		List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
		List<FieldColumn> mappers = SqlUtil.getFieldColumnMap(cls);
		for (FieldColumn fieldColumn : mappers) {
			ResultMapping.Builder resultMapping = new ResultMapping.Builder(ms.getConfiguration(),
					fieldColumn.getField(),fieldColumn.getColumn(),fieldColumn.getJavaType());
			resultMappings.add(resultMapping.build());
		}
		return resultMappings;
	}

}
