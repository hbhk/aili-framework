package org.hbhk.aili.orm.server.surpport;



public class SimpleModelClassSupport implements ModelClassSupport {

	private Class<?> modelClass;
	
	public Class<?> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<?> modelClass){
		this.modelClass = modelClass;
	}
}
