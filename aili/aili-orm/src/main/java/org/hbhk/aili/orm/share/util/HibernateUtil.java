package org.hbhk.aili.orm.share.util;

import java.lang.reflect.Type;

public class HibernateUtil {
	public static Type translateClass(Class<?> clazz) {
		return clazz;
		// if(clazz == null)
		// throw new IllegalArgumentException();
		// if(Integer.class.equals(clazz) || int.class.equals(clazz)){
		// return new IntegerType();
		// }else if(Long.class.equals(clazz) || long.class.equals(clazz)){
		// return new LongType();
		// }else if(Short.class.equals(clazz) || short.class.equals(clazz)){
		// return new ShortType();
		// }else if(Character.class.equals(clazz) || char.class.equals(clazz)){
		// return new CharacterType();
		// }else if(BigDecimal.class.equals(clazz)){
		// return new BigDecimalType();
		// }else if(Float.class.equals(clazz) || float.class.equals(clazz)){
		// return new FloatType();
		// }else if(Double.class.equals(clazz) || double.class.equals(clazz)){
		// return new DoubleType();
		// }else if(Boolean.class.equals(clazz) || boolean.class.equals(clazz)){
		// return new BooleanType();
		// }else if(String.class.equals(clazz)){
		// return new StringType();
		// }else if(Date.class.equals(clazz)){
		// return new TimestampType();
		// }else if(Calendar.class.equals(clazz)){
		// return new CalendarType();
		// }else if(Locale.class.equals(clazz)){
		// return new LocaleType();
		// }else if(TimeZone.class.equals(clazz)){
		// return new TimeZoneType();
		// }else if(Currency.class.equals(clazz)){
		// return new CurrencyType();
		// }else{
		// return new SerializableType(clazz);
		// }
	}
}
