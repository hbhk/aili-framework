package org.hbhk.aili.mybatis.server.support;

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
