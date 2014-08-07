package org.hbhk.aili.test.server.ant;

import org.hbhk.aili.test.server.ant.spring.AntPathMatcher;
import org.hbhk.aili.test.server.ant.spring.PathMatcher;
import org.junit.Assert;
import org.junit.Test;



public class AntTest {
	
	@Test
	public void test(){
		 PathMatcher matcher = new AntPathMatcher() ;
			
		boolean g = matcher.match("asd/**/hbhk", "asd/asd/asd/hbhk");
		Assert.assertTrue(g);
	}

}
