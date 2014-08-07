package org.hbhk.test.decorator;

//被装饰者具体类
public class HouseBlend extends Beverage{

	public HouseBlend(){
		description = "House Blend Coffee";
	}
	
	@Override
	public double cost() {
		// TODO Auto-generated method stub
		return 0.89;
	}

}

