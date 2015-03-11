package org.hbhk.aili.rpc.server.dubbo;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @Description: 远程调用增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class Test implements ITest,Serializable {
	private static final long serialVersionUID = -6414716285561258148L;
	public static final Logger log = LoggerFactory.getLogger(Test.class);
	public Test(String str) {
	}
	
	@Override
	public String test1() {
		return "test1";
	}
}

