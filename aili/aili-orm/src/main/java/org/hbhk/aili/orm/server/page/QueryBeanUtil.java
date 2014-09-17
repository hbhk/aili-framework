package org.hbhk.aili.orm.server.page;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hbhk.aili.orm.server.page.bean.QueryBean;
import org.hbhk.aili.orm.server.page.bean.QueryPara;
import org.hbhk.aili.orm.server.surpport.Sort;
import org.hbhk.aili.orm.share.util.DateUtil;

public class QueryBeanUtil {
	/**
	 * 解析request中参数，转换成queryBean
	 * @param parameterMap
	 * @return
	 * @throws ParseException 
	 */
	public static QueryBean parseParameter(HttpServletRequest request) throws ParseException{
		QueryBean queryBean = new QueryBean();
		Object size = request.getParameter("page.size");
		if(size!=null){
			try {
				queryBean.getPage().setSize(Integer.parseInt(size.toString()));
			} catch (NumberFormatException e) {
				queryBean.getPage().setSize(QueryBean.DEFULT_SIZE);
			}
		}
		Object startObject = request.getParameter("page.start");
		if(startObject!=null){
			try {
				int start = Integer.parseInt(startObject.toString());
				
				queryBean.getPage().setStart((start-1)*queryBean.getPage().getSize());
			} catch (NumberFormatException e) {
				queryBean.getPage().setStart(QueryBean.DEFULT_START);
			}
		}
		String sortStr = request.getParameter("sortStr");
		if(sortStr!=null){
			queryBean.setSorts(Sort.parse(sortStr));
		}
		Map<String, Object> queryParaMap = new HashMap<String, Object>();
		String qkey =  request.getParameter("searchField");
		String qvalue =  request.getParameter("searchString");
		//jqgrid查询支持
		if(StringUtils.isNotEmpty(qkey) &&StringUtils.isNotEmpty(qvalue)){
			QueryPara queryPara = QueryPara.parse(qkey);
			queryParaMap.put(queryPara.getName(), dealType(queryPara.getType(),qvalue));
		}else{
			for(Object keyObject:request.getParameterMap().keySet()){
				String key = keyObject.toString();
				if(QueryPara.isQueryPara(key)){
					String value = request.getParameter(key);
					//若值为null不做处理
					if(StringUtils.isBlank(value)){
						continue;
					}
					//去空格处理
					value=value.trim();
					QueryPara queryPara = QueryPara.parse(key);
					queryParaMap.put(queryPara.getName(), dealType(queryPara.getType(),value));
				}
			}
		}
		queryBean.setParaMap(queryParaMap);
		return queryBean;
	}
	/**
	 * 根据传入的参数，生成sql进行like查询的表达式
	 * 例如：
	 * 传入abc
	 * 返回%abc%
	 * 
	 * 传入ab_c
	 * 返回%ab\_c%
	 * @param para
	 * @return
	 */
	public static String genLikeString(String para){
		String likeString = para.replace("_", "\\_").replace("%", "\\%");
		return "%" + likeString + "%";
	}
	/**
	 * 根据传入的参数，生成sql进行like查询的表达式(左like)
	 * 例如：
	 * 传入abc
	 * 返回%abc%
	 * 
	 * 传入ab_c
	 * 返回%ab\_c%
	 * @param para
	 * @return
	 */
	public static String genLeftLikeString(String para){
		String likeString = para.replace("_", "\\_").replace("%", "\\%");
		return likeString + "%";
	}

	/**
	 * 
	 * @param para
	 * @return
	 * @throws ParseException 
	 */
	public static Object dealType(String typeString,String value) throws ParseException{
		if(QueryPara.TYPE_INT.equals(typeString)){
			return Integer.parseInt(value);
		}else if(QueryPara.TYPE_LONG.equals(typeString)){
			return Long.parseLong(value);
		}else if(QueryPara.TYPE_STRING_LIKE.equals(typeString)){
			return genLikeString(value);
		}else if(QueryPara.TYPE_STRING_LEFT_LIKE.equals(typeString)){
			return genLeftLikeString(value);
		}else if(QueryPara.TYPE_DATE.equals(typeString)){
			return DateUtil.parse(value,"yyyy-MM-dd");
		}else if(QueryPara.TYPE_TIME.equals(typeString)){
			return DateUtil.parse(value,"yyyy-MM-dd hh:mm:ss");
		}
		return value;
	}
	
}
