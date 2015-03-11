package org.hbhk.aili.mybatis.server.support;

/**
 * 
 * @Description: mybatis增强处理
 * @author 何波
 * @date 2015年3月11日 上午10:05:24 
 *
 */
public class GnericInterfaceTypeContext {
	private static ThreadLocal<GnericInterfaceTypeContext> context = new ThreadLocal<GnericInterfaceTypeContext>() {
		@Override
		protected GnericInterfaceTypeContext initialValue() {
			return new GnericInterfaceTypeContext();
		}
	};

	private Class<?> gnericInterfaceType;

	public static void setType(Class<?> type) {
		context.get().gnericInterfaceType = type;
	}

	public static Class<?> getType() {
		return context.get().gnericInterfaceType;
	}

	public static void remove() {
		context.remove();
	}
}
