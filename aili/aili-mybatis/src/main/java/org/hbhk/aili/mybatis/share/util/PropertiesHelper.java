package org.hbhk.aili.mybatis.share.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesHelper {
	public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;

	public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;

	public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;

	Properties p;
	private int systemPropertiesMode = SYSTEM_PROPERTIES_MODE_NEVER;

	public PropertiesHelper(Properties p) {
		setProperties(p);
	}

	/**
	 * 验证systemPropertiesMode参数是否正确，此方法是默认不检查系统文件
	 */
	public PropertiesHelper(Properties p, int systemPropertiesMode) {
		setProperties(p);
		if (systemPropertiesMode != SYSTEM_PROPERTIES_MODE_NEVER
				&& systemPropertiesMode != SYSTEM_PROPERTIES_MODE_FALLBACK
				&& systemPropertiesMode != SYSTEM_PROPERTIES_MODE_OVERRIDE) {
			throw new IllegalArgumentException(
					"error systemPropertiesMode mode:" + systemPropertiesMode);
		}
		this.systemPropertiesMode = systemPropertiesMode;
	}

	/**
	 * 获取properties
	 */
	public Properties getProperties() {
		return p;
	}

	private void setProperties(Properties props) {
		if (props == null) {
			throw new IllegalArgumentException("properties must be not null");
		}
		this.p = props;
	}

	/**
	 * 验证通过key获取value，并进行非空验证
	 */
	public String getRequiredString(String key) {
		String value = getProperty(key);
		if (isBlankString(value)) {
			throw new IllegalStateException(
					"required property is blank by key=" + key);
		}
		return value;
	}

	/**
	 * 如果key为空，通过此方法获取对应的value为空
	 */
	public String getNullIfBlank(String key) {
		String value = getProperty(key);
		if (isBlankString(value)) {
			return null;
		}
		return value;
	}

	/**
	 * 如果value为空，返回空
	 */
	public String getNullIfEmpty(String key) {
		String value = getProperty(key);
		if (value == null || "".equals(value)) {
			return null;
		}
		return value;
	}

	/**
	 * 如果value为空，返回系统中默认的value
	 */
	public String getAndTryFromSystem(String key) {
		String value = getProperty(key);
		if (isBlankString(value)) {
			value = getSystemProperty(key);
		}
		return value;
	}

	/**
	 * 如果value为空，返回系统中默认的value
	 */
	private String getSystemProperty(String key) {
		String value;
		value = System.getProperty(key);
		if (isBlankString(value)) {
			value = System.getenv(key);
		}
		return value;
	}

	/**
	 * 通过此方法返回一个Integer型
	 */
	public Integer getInteger(String key) {
		String v = getProperty(key);
		if (v == null) {
			return null;
		}
		return Integer.parseInt(v);
	}

	/**
	 * 通过此方法返回一个int型
	 */
	public int getInt(String key, int defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Integer.parseInt(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个int型
	 */
	public int getRequiredInt(String key) {
		return Integer.parseInt(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Long型
	 */
	public Long getLong(String key) {
		if (getProperty(key) == null) {
			return null;
		}
		return Long.parseLong(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个long型
	 */
	public long getLong(String key, long defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Long.parseLong(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Long型
	 */
	public Long getRequiredLong(String key) {
		return Long.parseLong(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Boolean型
	 */
	public boolean getBoolean(String key) {
		if (getProperty(key) == null) {
			return false;
		}
		return Boolean.parseBoolean(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个boolean型
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Boolean.parseBoolean(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个boolean型
	 */
	public boolean getRequiredBoolean(String key) {
		return Boolean.parseBoolean(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Float型
	 */
	public Float getFloat(String key) {
		if (getProperty(key) == null) {
			return null;
		}
		return Float.parseFloat(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个float型
	 */
	public float getFloat(String key, float defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Float.parseFloat(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Float型
	 */
	public Float getRequiredFloat(String key) {
		return Float.parseFloat(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Double型
	 */
	public Double getDouble(String key) {
		if (getProperty(key) == null) {
			return null;
		}
		return Double.parseDouble(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Double型
	 */
	public double getDouble(String key, double defaultValue) {
		if (getProperty(key) == null) {
			return defaultValue;
		}
		return Double.parseDouble(getRequiredString(key));
	}

	/**
	 * 通过此方法返回一个Double型
	 */
	public Double getRequiredDouble(String key) {
		return Double.parseDouble(getRequiredString(key));
	}

	/**
	 * 给properties赋一int类型值
	 */
	public Object setProperty(String key, int value) {
		return setProperty(key, String.valueOf(value));
	}

	/**
	 * 给properties赋一long类型值
	 */
	public Object setProperty(String key, long value) {
		return setProperty(key, String.valueOf(value));
	}

	/**
	 * 给properties赋一float类型值
	 */
	public Object setProperty(String key, float value) {
		return setProperty(key, String.valueOf(value));
	}

	/**
	 * 给properties赋一double类型值
	 */
	public Object setProperty(String key, double value) {
		return setProperty(key, String.valueOf(value));
	}

	/**
	 * 给properties赋一boolean类型值
	 */
	public Object setProperty(String key, boolean value) {
		return setProperty(key, String.valueOf(value));
	}



	/**
	 * 通过key获取value，并设置默认值
	 */
	public String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 通过key获取value
	 */
	public String getProperty(String key) {
		String propVal = null;
		// 如果systemPropertiesMode = 2 首先检查系统属性
		if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
			propVal = getSystemProperty(key);
		}
		if (propVal == null) {
			propVal = p.getProperty(key);
		}
		// systemPropertiesMode = 1 如果执行属性没有，获取系统属性
		if (propVal == null
				&& systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
			propVal = getSystemProperty(key);
		}
		return propVal;
	}

	/**
	 * 设置properties值
	 */
	public Object setProperty(String key, String value) {
		return p.setProperty(key, value);
	}

	/**
	 * 清空properties
	 */
	public void clear() {
		p.clear();
	}

	/**
	 * 返回Hashtable 中所包含的键的 Set 视图
	 */
	public Set<Entry<Object, Object>> entrySet() {
		return p.entrySet();
	}

	/**
	 * 返回属性列表中所有键的枚举，如果在主属性列表中未找到同名的键，则包括默认属性列表中不同的键。
	 */
	public Enumeration<?> propertyNames() {
		return p.propertyNames();
	}

	/**
	 * 验证properties时候包含指定的value
	 */
	public boolean contains(Object value) {
		return p.contains(value);
	}

	/**
	 * 验证properties时候包含指定的key
	 */
	public boolean containsKey(Object key) {
		return p.containsKey(key);
	}

	/**
	 * 验证properties时候包含指定的value
	 * 
	 */
	public boolean containsValue(Object value) {
		return p.containsValue(value);
	}

	/**
	 * 返回此哈希表中的值的枚举。对返回的对象使用 Enumeration方法，以便按顺序获取这些元素。
	 */
	public Enumeration<Object> elements() {
		return p.elements();
	}

	/**
	 * 通过key 获取到一个object
	 */
	public Object get(Object key) {
		return p.get(key);
	}

	/**
	 * 判断properties是否为空
	 */
	public boolean isEmpty() {
		return p.isEmpty();
	}

	/**
	 * 返回此哈希表中的键的枚举。
	 */
	public Enumeration<Object> keys() {
		return p.keys();
	}

	/**
	 * 返回此 Hashtable 中所包含的键的 Set 视图。
	 */
	public Set<Object> keySet() {
		return p.keySet();
	}

	/**
	 * 将属性列表输出到指定的输出流。
	 */
	public void list(PrintStream out) {
		p.list(out);
	}

	/**
	 * 将属性列表输出到指定的输出流。
	 */
	public void list(PrintWriter out) {
		p.list(out);
	}

	/**
	 * 从输入流中读取属性列表（键和元素对）。
	 */
	public void load(InputStream inStream) throws IOException {
		p.load(inStream);
	}

	/**
	 *  将指定输入流中由 XML 文档所表示的所有属性加载到此属性表中。
	 */
	public void loadFromXML(InputStream in) throws IOException {
		p.loadFromXML(in);
	}

	/**
	 * 给 properties 赋值
	 */
	public Object put(Object key, Object value) {
		return p.put(key, value);
	}

	/**
	 * 给 properties 赋值
	 */
	public void putAll(Map<? extends Object, ? extends Object> t) {
		p.putAll(t);
	}

	/**
	 * 删除指定key的value
	 */
	public Object remove(Object key) {
		return p.remove(key);
	}

	/**
	 * 果在保存属性列表时发生 I/O 错误，则此方法不抛出 IOException。
	 * 保存属性列表的首选方法是通过 store(OutputStream out, String comments) 
	 * 方法或 storeToXML(OutputStream os, String comment) 方法来进行。
	 */
	@Deprecated
	public void save(OutputStream out, String comments) {
		p.save(out, comments);
	}

	/**
	 * 获取properties的大小
	 */
	public int size() {
		return p.size();
	}

	/**
	 * 适合使用 load 方法加载到 Properties 表中的格式，
	 * 将此 Properties 表中的属性列表（键和元素对）写入输出流。
	 */
	public void store(OutputStream out, String comments) throws IOException {
		p.store(out, comments);
	}

	/**
	 *  发出一个表示此表中包含的所有属性的 XML 文档。
	 */
	public void storeToXML(OutputStream os, String comment, String encoding)
			throws IOException {
		p.storeToXML(os, comment, encoding);
	}

	/**
	 *   使用指定的编码发出一个表示此表中包含的所有属性的 XML 文档。
	 */
	public void storeToXML(OutputStream os, String comment) throws IOException {
		p.storeToXML(os, comment);
	}

	/**
	 *   返回此 Hashtable 中所包含值的 Collection 视图。
	 *   Collection 受 Hashtable 的支持，
	 *   所以对 Hashtable 的更改反映在 Collection 中，
	 *   反之亦然。Collection 支持元素的删除（它从 Hashtable 中移除相应的条目)，
	 *   但不支持元素的添加。 
	 * 
	 */
	public Collection<Object> values() {
		return p.values();
	}

	/**
	 * 返回此 Hashtable 对象的字符串表示形式，其形式为 ASCII 字符 ", " 
	 * （逗号加空格）分隔开的、括在括号中的一组条目。
	 * 每个条目都按以下方式呈现：键，一个等号 = 和相关元素，
	 * 其中 toString 方法用于将键和元素转换为字符串。
	 * 
	 */
	public String toString() {
		return p.toString();
	}

	/**
	 *  String字符串的非空验证
	 */
	private static boolean isBlankString(String value) {
		return value == null || "".equals(value.trim());
	}
}
