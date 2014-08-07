package org.hbhk.aili.test.server.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface IGetInfoService {
	@WebMethod(operationName = "add")
	@WebResult(name = "result")
	public int add(@WebParam(name = "num1") int num1,
			@WebParam(name = "num2") int num2);

	@WebMethod(operationName = "getRetInfo")
	@WebResult(name = "result")
	public String getRetInfo(@WebParam(name = "name") String name,
			@WebParam(name = "age") int age);
}
