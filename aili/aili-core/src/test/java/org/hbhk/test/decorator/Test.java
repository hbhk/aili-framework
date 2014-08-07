package org.hbhk.test.decorator;


public class Test {
	public static void main(String[] args) {
		//不需要调料，即没有装饰者
		Beverage beverage1 = new HouseBlend();
		System.out.println(beverage1.getDescription() + " $" + beverage1.cost());
		
		//使用了三个装饰者来装饰DarkRoast
		Beverage beverage2 = new HouseBlend();
		beverage2 = new Mocha(beverage2);
		System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
		
		//使用了两个装饰者来装饰HouseBlend
		Beverage beverage3 = new HouseBlend();
		beverage3 = new Mocha(beverage3);
		System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
		
	}
}
