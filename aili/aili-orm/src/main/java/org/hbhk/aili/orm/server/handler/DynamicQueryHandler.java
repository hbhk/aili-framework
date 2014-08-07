package org.hbhk.aili.orm.server.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.hbhk.aili.orm.server.annotation.DynamicQuery;
import org.hbhk.aili.orm.server.service.IDaoService;
import org.hbhk.aili.orm.server.service.IGetbrickTemplate;
import org.hbhk.aili.orm.server.service.impl.GetbrickTemplate;
import org.hbhk.aili.orm.server.surpport.ModelClassSupport;
import org.hbhk.aili.orm.server.surpport.Page;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.model.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DynamicQueryHandler extends AbstractQueryHandler {
	
	@Autowired
	@Qualifier("getbrickTemplate")
	private IGetbrickTemplate getbrickTemplate = new GetbrickTemplate();

	public IGetbrickTemplate getGetbrickTemplate() {
		return getbrickTemplate;
	}

	public void setGetbrickTemplate(IGetbrickTemplate getbrickTemplate) {
		this.getbrickTemplate = getbrickTemplate;
	}

	public DynamicQueryHandler(IDaoService daoService) {
		super(daoService);
	}
	
	public Object handleDynamicQuery(DynamicQuery dynamicQuery, MethodInvocation invocation){
		return handleDynamicQueryNative(dynamicQuery, invocation.getThis(), invocation.getMethod(), invocation.getArguments());
	}

	public Object handleDynamicQuery(DynamicQuery dynamicQuery, ProceedingJoinPoint pjp){
		MethodSignature ms = (MethodSignature)pjp.getSignature();
		return handleDynamicQueryNative(dynamicQuery, pjp.getThis(), ms.getMethod(), pjp.getArgs());
	}
	
	private Object handleDynamicQueryNative(DynamicQuery dynamicQuery, Object obj, Method m, Object[] args){
		Map<String, Object> params = getParams(m, args);
		
		Page page = getPage(args);
		boolean pagable = (page != null) ||dynamicQuery.pagable();		
		
		String queryName = dynamicQuery.value();
		if(queryName.equals("")){
			if(!(obj instanceof ModelClassSupport)) 
				throw new RuntimeException("QueryName can not be empty");
			ModelClassSupport mcs = (ModelClassSupport)obj;
			queryName += mcs.getModelClass().getSimpleName();
			queryName += "." + m.getName();				
		}
		
		String queryString = getDynamicQuery(queryName, params);			
		if(logger.isDebugEnabled()){
			logger.debug("DynamicQuery[{}] will be executed", queryName);
			logger.debug("{}",queryString);
		}
		
		Sort[] sorts = getSorts(args);
		
		if(sorts != null){
			logger.debug("Query need be sorted with :" + Arrays.asList(sorts));
		}
		
		if(List.class.isAssignableFrom(m.getReturnType())){
			if(pagable){
				if(page != null)
					return daoService.findByQueryEx(queryString, params, sorts, page.getStart(), page.getSize());
				else if(args[0] instanceof Integer &&
						args[1] instanceof Integer)				
					return daoService.findByQueryEx(queryString, params, sorts, (Integer)args[0], (Integer)args[1]); 
				else
					throw new IllegalArgumentException("Startindex and pagesize must be set for pagable query.");
			}else{
				return daoService.findByQueryEx(queryString, params, sorts, -1, -1);
			}
		}else if(Pagination.class.isAssignableFrom(m.getReturnType())){
			if(pagable){
				if(page != null)
					return daoService.findByQueryEx(queryString, params, sorts, page.getStart(), page.getSize(), dynamicQuery.withGroupby());
				else if(args[0] instanceof Integer &&
						args[1] instanceof Integer)				
					return daoService.findByQueryEx(queryString, params, sorts, (Integer)args[0], (Integer)args[1], dynamicQuery.withGroupby());
				else
					throw new IllegalArgumentException("Startindex and pagesize must be set for pagable query.");
			}else{
				throw new IllegalStateException("Please set pagable to true");
			}
		}else
			return daoService.findOneByQueryEx(queryString, params, sorts);
	}
	/**
	 * 
	* @author 何波
	* @Description: 查找对于模板生成sql语句
	* @param queryName
	* @param params
	* @return   
	* String   
	* @throws
	 */
	protected String getDynamicQuery(String queryName, Map<String, Object> params) {
		return getbrickTemplate.setContextData(params, queryName);
	}
}
