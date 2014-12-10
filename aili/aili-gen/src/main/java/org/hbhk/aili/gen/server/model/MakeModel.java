package org.hbhk.aili.gen.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin
 * 
 */
public class MakeModel {

	private String projectName;

	private String moduleName;

	private String entityPackagName;
	
	private  String fname;
	/**
	 * 生命周期的字段名
	 */
	private String lifecycle;

	/**
	 * 序列(主键自增)
	 */
	private String sequeneName;

	/**
	 * 主键名
	 */
	private String pkName;

	/**
	 * 实体类名
	 */
	private String entityName;

	/**
	 * 实体类包名(用于dao生成时的路径)
	 */
	private String packagName;
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 作者名(需要用户显示定义)
	 */
	private String authName;

	/**
	 * version字段(version参与修改,但会使用now()代替字段值)
	 */
	private String versionField;

	/**
	 * 是否有删除状态
	 */
	private Boolean hasDeleteLifecycle = true;

	/**
	 * 字段描述
	 */
	private List<PropertyDesc> propertyList = new ArrayList<PropertyDesc>();

	public Boolean getHasDeleteLifecycle() {
		return hasDeleteLifecycle;
	}

	public void setHasDeleteLifecycle(Boolean hasDeleteLifecycle) {
		this.hasDeleteLifecycle = hasDeleteLifecycle;
	}

	public String getPackagName() {
		return packagName;
	}

	public void setPackagName(String packagName) {
		this.packagName = packagName;
	}

	public List<PropertyDesc> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<PropertyDesc> propertyList) {
		this.propertyList = propertyList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getVersionField() {
		return versionField;
	}

	public void setVersionField(String versionField) {
		this.versionField = versionField;
	}

	public String getSequeneName() {
		return sequeneName;
	}

	public void setSequeneName(String sequeneName) {
		this.sequeneName = sequeneName;
	}

	public String getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getEntityPackagName() {
		return entityPackagName;
	}

	public void setEntityPackagName(String entityPackagName) {
		this.entityPackagName = entityPackagName;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	

}
