package org.hbhk.aili.test.server.aop;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  
public class SpringAOPTest {  
      
    @Test  
    public void inteceptorTest(){  
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");  
        PersonServer bean = (PersonServer)ctx.getBean("personServiceBean");  
        bean.save("hbhk");  
    }  
      
  
} 
