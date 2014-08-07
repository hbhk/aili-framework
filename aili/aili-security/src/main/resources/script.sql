
   -- 用户表Users
    CREATE TABLE t_aili_user (
       id varchar(255) PRIMARY KEY,
       -- 账号是否有限 1. 是 0.否
       enable char(1) ,
       password varchar(255),
       username varchar(255),
       mail varchar(255),
       name varchar(255),
       gender char(1)
     
    );
 
   -- 角色表Roles
   CREATE TABLE t_aili_role (
     id varchar(255) PRIMARY KEY ,
     enable char(1),
     name varchar(255),
     code varchar(255)
    
   );
 
   -- 用户_角色表users_roles
   CREATE TABLE t_aili_user_role (
     id varchar(255) PRIMARY KEY ,
     -- 用户表的外键
     ucode varchar(255),
     -- 角色表的外键
     rcode varchar(255)
   );
 
   -- 资源表resources
   CREATE TABLE t_aili_resource (
     id varchar(255) PRIMARY KEY ,
      -- 权限所对应的编码
     code varchar(255) ,
     -- 权限所对应的url地址
     url varchar(255),
     -- 优先权
     priority int(2),
      -- 上级菜单
     parent_code varchar(255) ,
     -- 是否有 子节点
     hasChildren char(1),
     -- css 样式
     classes  varchar(100) ,
     -- 是否展开
     expanded  char(1),
     --是否启用 YN
     active char(1),
     -- 类型  
     type varchar(255) ,
     text varchar(255) ,
      -- 备注
     memo varchar(255)
   );
   

   -- 角色_资源表roles_resources
    CREATE TABLE t_aili_role_resource(
      id varchar(255) PRIMARY KEY ,
      rocode varchar(255),
      recode varchar(255)
    
    );
insert into t_aili_user(id,username,password)  VALUES ('1','hbhk','135246');

insert into t_aili_role(id,code,name)  VALUES ('1','r001','admin');
insert into t_aili_role(id,code,name)  VALUES ('2','r002','user');
insert into t_aili_role(id,code,name)  VALUES ('3','r003','anyone');

insert into t_aili_user_role(id,ucode,rcode)  VALUES ('1','hbhk','r001');
insert into t_aili_user_role(id,ucode,rcode)  VALUES ('2','hbhk','r002');
insert into t_aili_user_role(id,ucode,rcode)  VALUES ('3','hbhk','r003');
 delete from  t_aili_resource;
 insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('root','root','root','root','root',NULL,'Y','menu','N','Y');
   insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('1','re001','中国','user/add','分类','root','Y','menu','N','Y');
  insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('2','re002','贵州省','user/add','菜单','re001','Y','menu','N','Y');
   insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('3','re003','四川省','user/add','菜单','re001','N','menu','N','Y');   
insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('4','re004','云南省','user/add','菜单','re001','N','menu','N','Y');

insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('5','re005','贵阳市','user/add','菜单','re002','Y','menu','N','Y');
   insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('6','re006','遵义市','user/add','菜单','re002','N','menu','N','Y');   
insert into t_aili_resource(id,code,text,url,type,parent_code,hasChildren,classes,expanded,active)  
VALUES ('7','re007','习水县','user/add','菜单','re002','N','menu','N','Y');

insert into t_aili_role_resource(id,rocode,recode)  VALUES ('1','r001','re001');
insert into t_aili_role_resource(id,rocode,recode)  VALUES ('2','r001','re001');
insert into t_aili_role_resource(id,rocode,recode)  VALUES ('3','r001','re002');
insert into t_aili_role_resource(id,rocode,recode)  VALUES ('4','r001','re004');
