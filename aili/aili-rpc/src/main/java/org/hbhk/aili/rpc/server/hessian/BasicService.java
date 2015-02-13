package org.hbhk.aili.rpc.server.hessian;

public class BasicService  implements Basic {
  public String hello(String str)
  {
    return "Hello, world";
  }
}
