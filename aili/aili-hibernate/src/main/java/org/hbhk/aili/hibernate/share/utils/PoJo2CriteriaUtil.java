package org.hbhk.aili.hibernate.share.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javassist.Modifier;

import org.hbhk.aili.hibernate.share.model.Page;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/** 将POJO对象转化为HQL标准查询条件 */
public class PoJo2CriteriaUtil {

	/**
	 * 获取分页数据
	 * 
	 * @param criteria
	 *            标准查询条件
	 * @param pojo
	 *            pojo对象
	 * @param isLike
	 *            是否使用Like模式
	 * @param pagesize
	 *            每页显示条目数
	 * @param currpage
	 *            页标
	 * @return 分页数据
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> findByPage(Criteria criteria, T pojo, Boolean isLike,
			int pagesize, int currpage) throws IllegalArgumentException,
			IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Page<T> page = new Page<T>();
		criteria = this.converse(criteria, pojo, isLike);
		// 统计页数
		Long count = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		criteria.setProjection(null);
		page.setPageSize(pagesize);
		page.setTotalCount(count.intValue());
		if (currpage > page.getPageNo()) {
			currpage = page.getPageNo();
		}
		page.setPageNo(currpage);
		// 查询
		final int fristResult = (currpage - 1) * page.getPageSize();
		List<T> list = criteria.setFirstResult(fristResult)
				.setMaxResults(page.getPageSize()).list();
		page.setData(list);

		return page;
	}

	/**
	 * 将POJO对象转化为HQL标准查询条件
	 * 
	 * @param criteria
	 *            标准查询条件
	 * @param pojo
	 *            pojo对象
	 * @param isLike
	 *            是否使用Like模式
	 * @return 标准查询条件
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public <T> Criteria converse(Criteria criteria, T pojo, Boolean isLike)
			throws IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (pojo == null)
			return criteria;
		String propertyName = null;
		PropertyDescriptor pd = null;
		Method getMethod = null;
		String timeStr = null;
		Date timeDate = null;
		Class<? extends Object> clazz = pojo.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 遍历所有属性
		for (Field field : fields) {
			// 过滤final与 static 变量
			int mod = field.getModifiers();
			if (Modifier.isFinal(mod) || Modifier.isStatic(mod)
					|| field.getType().isInterface()) {
				continue;
			}
			propertyName = field.getName();
			pd = new PropertyDescriptor(propertyName, clazz);
			// 获得所有属性的读取方法
			getMethod = pd.getReadMethod();
			// 执行读取方法，获得属性值
			Object value = getMethod.invoke(pojo);
			// 如果属性值为null，就略过
			if (value == null || "".equals(value.toString().trim())) {
				continue;
			}
			// 如果不为空.判断是否开启模糊查询，添加查询条件,并且加上%%符号。
			String typeSimpleName = field.getType().getSimpleName();
			if (typeSimpleName.equals("Date")) {
				if (propertyName.toLowerCase().contains("start")) {
					timeStr = DateUtil.formatDate(value, "yyyy-MM-dd")
							+ " 00:00:00";
					timeDate = DateUtil.parseDate(timeStr, null);
					criteria.add(Restrictions.ge(propertyName, timeDate));
				}
				if (propertyName.toLowerCase().contains("end")) {
					timeStr = DateUtil.formatDate(value, "yyyy-MM-dd")
							+ " 23:59:59";
					timeDate = DateUtil.parseDate(timeStr, null);
					criteria.add(Restrictions.le(propertyName, timeDate));
				}
			} else if (typeSimpleName.equals("String")) {

				if (isLike) {
					criteria.add(Restrictions.like(propertyName, "%" + value
							+ "%"));
				} else {
					criteria.add(Restrictions.eq(propertyName, value));
				}
			} else {
				criteria.add(Restrictions.eq(propertyName, value));
			}
		}

		return criteria;
	}

}
