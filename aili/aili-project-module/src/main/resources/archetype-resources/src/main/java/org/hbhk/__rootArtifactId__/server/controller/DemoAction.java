/**
 * Action开发规范
 * 1.必须继承com.deppon.foss.framework.server.web.action.AbstractAction
 * 2.必须生成serialVersionUID
 * 3.类名必须以Action结尾
 * 4.前台传参必须封装到Vo中,不能直接使用Entity,Vo必须添加getter、setter方法
 * 5.方法上必须添加com.deppon.foss.framework.server.web.result.json.annotation.JSON @JSON 注解
 * 6.方法中必须try catch异常,成功调用returnSuccess系列重载函数,异常调用returnError系列重载函数
 * 7.禁止添加Service的getter方法
 * 8.禁止注入Dao、只允许注入Service
 */