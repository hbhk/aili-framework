package org.hbhk.aili.core.share.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * WEB异常的工具类
 */
public class WebErrorUtils {

	 /** 错误信息 */
	public static final String ERROR = "ERROR";
	/** 异常堆栈信息 */
	public static final String STACKTRACE = "stackTrace";
	
	/**
	 * 将异常堆栈信息存入给定的StringBuilder
	 * @param s
	 * @param iex
	 * @since
	 */
	public static void printStackTraceAsCause(StringBuilder s, Throwable iex) {
		s.append(iex.toString());
        StackTraceElement[] trace = iex.getStackTrace();
        for (int i=0; i < trace.length; i++) {
            s.append("\tat ").append(trace[i]);
        }
        
		Throwable ourCause = iex.getCause();
		if (ourCause != null) {
			printStackTraceAsCause(s, ourCause);
		}
	}

	public static String getStackTrace(Throwable e) {
		return getStackTrace(500, e);
    }
	
	public static String getStackTrace(int bufferSize, Throwable e) {
		StringWriter sw = new StringWriter(bufferSize);
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
    }
}
