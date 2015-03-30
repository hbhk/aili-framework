package org.hbhk.aili.mybatis.server.aop;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.mybatis.server.support.DynamicSqlTemplate;
import org.hbhk.aili.mybatis.server.support.GnericInterfaceTypeContext;
import org.hbhk.aili.mybatis.server.support.Page;
import org.hbhk.aili.mybatis.server.support.Pagination;
import org.hbhk.aili.mybatis.server.support.Sort;
import org.hbhk.aili.mybatis.share.util.AopTargetUtil;
import org.hbhk.aili.mybatis.share.util.MybatisSqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
@Aspect
@Component
public class QueryPageAspect implements Ordered {

	protected static final Logger logger = LoggerFactory.getLogger(QueryPageAspect.class);
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int getOrder() {
		return 20;
	}
	@Around("this(org.hbhk.aili.mybatis.server.dao.IBaseDao)")
	//@Around("execution(* org.hbhk.*.*.server.dao.*.*(..))")
	public Object doQuery(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();
		Class<?> rt = method.getReturnType();
		String mapperIdPrefix= AopTargetUtil.getJdkDynamicProxyTargeInterface(pjp.getThis());
		if(rt.isAssignableFrom(Pagination.class)){
			Class<?> cls = method.getDeclaringClass();
			SelectProvider selectProvider = method.getAnnotation(SelectProvider.class);
			if(selectProvider !=null){
				Class<?> type =  selectProvider.type();
				if(type.equals(DynamicSqlTemplate.class)){
					Class<?> gnericInterfaceType = getGenericInterfaces(cls);
					GnericInterfaceTypeContext.setType(gnericInterfaceType);
				}
			}
			SqlSession session = sqlSessionFactory.openSession();
			String methodName = method.getName();
			String statement = mapperIdPrefix+"."+methodName;
			
			Object[] args = pjp.getArgs();
			Map<String, Object> params = getParams(args);
			String sql = MybatisSqlHelper.getSql(session, statement, params);
			sql = getCountQueryStringForSql(sql, false);
			Object[] qargs = MybatisSqlHelper.getParams(session, statement, params);
			int count = getCount(sql, qargs);
			if(count == 0){
				return null;
			}
			Page page = getPage(args);
			Sort[] sorts = getSorts(args);
			params.put("page.sorts", sorts);
			RowBounds rowBounds = new RowBounds(page.getPageNum(),page.getPageSize());
			List<Object> list = session.selectList(statement, params,rowBounds);
			
			Pagination<Object> pagination = new Pagination<Object>();
			pagination.setDatas(list);
			pagination.setCount(count);
			int totalPages = count/page.getPageSize();
			if(count%page.getPageSize()!=0){
				totalPages =totalPages+1;
			}
			pagination.setTotalPages(totalPages);
			pagination.setPageNum(page.getPageNum());
			pagination.setPageSize(page.getPageSize());
			if(selectProvider !=null){
				GnericInterfaceTypeContext.remove();
			}
			return pagination;
		}else{
			Object obj = pjp.proceed(pjp.getArgs());
			return obj;
		}
		
	}
	
	private Class<?> getGenericInterfaces(Class<?> clazz) throws Exception {
		Type[] types = clazz.getGenericInterfaces();
		ParameterizedType pType = null;
		if(types.length==0){
			//pType = clazz.;
			return clazz;
		}else{
			pType = (ParameterizedType) types[0];
		}
	
		Class<?> cls = (Class<?>) pType.getActualTypeArguments()[0];
		return cls;
	}
	
	private int getCount(String sql , Object[] args){
		int count = jdbcTemplate.query(sql,args,new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				int num = rs.getInt("num");
				return num;
			}
		});
		return count;
	}
	private String getCountQueryStringForSql(String sql, boolean withGroupby){
		if(sql == null) return null;
		sql = sql.trim();
        String lowercaseSQL = sql.toLowerCase();
        int delim1 = lowercaseSQL.indexOf("from");
        int delim2 = lowercaseSQL.indexOf("order by");
        if(delim1 <0){
        	if(logger.isDebugEnabled()){
        		logger.debug("It seemed that current sql is not one valid one.");
        		logger.debug("SQL:{}", sql);
        	}
        	return null;
        }
        if (delim2 == -1) delim2 = sql.length();
        String countSQL = "";
        if(withGroupby){
        	countSQL = "select count(1) as num from (" + sql.substring(0,delim2) + ") tmp_tbl";
        }else{
        	countSQL =  "select count(1) as num " + sql.substring(delim1,delim2);
        }
        logger.debug("Count SQL:{}", countSQL);
        return countSQL;
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> getParams(Object[] args ){
		Map<String, Object> params = new HashMap<String, Object>();
		if(args!=null){
			for (Object arg : args) {
				if(arg instanceof Map ){
					return  (Map<String, Object>) arg;
				}
			}
		}
		return params;
	}
	
	private Page getPage(Object[] args ){
		if(args!=null){
			for (Object arg : args) {
				if(arg instanceof Page ){
					return   (Page) arg;
				}
			}
		}
		Page page = new Page();
		page.setPageNum(0);
		page.setPageSize(10);
		return page;
	}
	private Sort[] getSorts(Object[] args ){
		if(args!=null){
			for (Object arg : args) {
				if(arg instanceof Sort[] ){
					return   (Sort[]) arg;
				}
			}
		}
		return null;
	}
}
