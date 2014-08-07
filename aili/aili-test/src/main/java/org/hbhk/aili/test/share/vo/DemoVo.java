/**
 * Vo开发规范
 * 1.必须实现java.io.Serializable接口
 * 2.必须生成serialVersionUID
 * 3.类名必须以Vo结尾
 * 4.建议不要直接继承Entity,可以使用Entity作为Vo的一个属性通过getter、setter访问
 *   Vo的生命周期只能到Action层,禁止将Vo传入Service层以下,可以通过Vo.getXXEntity(XXEntity代表某个Entity的名称)获取Entity传入Service层以下
 *   Vo主要负责接受前台给Action传参、封装后台返回结果给前台
 */