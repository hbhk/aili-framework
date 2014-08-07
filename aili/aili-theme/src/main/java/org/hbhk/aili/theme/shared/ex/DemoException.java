/**
 * Exception开发规范
 * 1.必须继承com.deppon.foss.framework.exception.BusinessException
 * 2.类名必须以Exception结尾
 * 3.必须生成serialVersionUID
 * 4.类中的ERROR_CODE必须声明为static final禁止使用enum枚举
 *   命名规则：模块名_具体功能描述_ERROR_CODE全部使用大写字母
 *   例如：LINEINFO_ID_NULL_ERROR_CODE
 *   类中ERROR_CODE的值必须使用国际化中的Key
 */