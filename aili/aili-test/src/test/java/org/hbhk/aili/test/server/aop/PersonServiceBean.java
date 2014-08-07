package org.hbhk.aili.test.server.aop;


public class PersonServiceBean implements PersonServer{  
      
    @Override  
    public void save(String name) {  
          
        System.out.println("我是save方法");  
    //  throw new RuntimeException();  
    }  
  
    @Override  
    public void update(String name, Integer id) {  
          
        System.out.println("我是update()方法");  
    }  
  
    @Override  
    public String getPersonName(Integer id) {  
          
        System.out.println("我是getPersonName()方法");  
        return "xxx";  
    }  
  
}  
