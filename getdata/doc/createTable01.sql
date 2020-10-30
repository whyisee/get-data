-- 创建数据库
create database if not exists shetuan;
use shetuan;

-- 创建序列
drop table if exists sequence;
create table sequence (
seq_name        VARCHAR(50) NOT NULL, -- 序列名称
current_val     INT         NOT NULL, -- 当前值
increment_val   INT         NOT NULL    DEFAULT 1, -- 步长(跨度)
PRIMARY KEY (seq_name)   );

INSERT INTO sequence VALUES ('seq_common_id', '1000000000', '1');



create function currval(v_seq_name VARCHAR(50))
returns integer(11)
begin
 declare value integer;
 set value = 0;
 select current_val into value  from sequence where seq_name = v_seq_name;
   return value;
end;

create function nextval (v_seq_name VARCHAR(50)) returns integer(11)
begin
    update sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;
	return currval(v_seq_name);
end;


-- 创建数据源表
CREATE TABLE tc_gd_datasource
 (
 source_id varchar(10) comment '数据源编码'
,source_name varchar(50) comment '数据源名称'
,source_name_zh varchar(200) comment '数据源中文名称'
,source_type varchar(50) comment '分类'
,source_key varchar(50) comment '主键'
,source_alias varchar(10) comment '别名'
,status varchar(2) comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
 ) comment=' 1.数据源表';



-- 配置流程表
CREATE TABLE tc_gd_configflow
 (
 flow_id varchar(10) comment '流程编码'
,flow_name varchar(200) comment '流程名称'
,flow_type varchar(10) comment '流程类型'
,parent_flow_id varchar(10) comment '父流程编码'
,flow_key varchar(50) comment '流程配置键'
,flow_value1 varchar(2000) comment '流程配置值1'
,flow_value2 varchar(2000) comment '流程配置值2'
,flow_value3 varchar(2000) comment '流程配置值3'
,flow_value4 varchar(2000) comment '流程配置值4'
,flow_value5 varchar(2000) comment '流程配置值5'
,flow_sort varchar(10) comment '流程序号'
,status varchar(2) comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
 ) DEFAULT CHARSET=utf8, comment=' 配置流程表';

-- 配置结果表
CREATE TABLE tc_gd_configmain
(
    task_id varchar(10) comment '任务编码'
    ,task_name varchar(200) comment '任务名称'
    ,flow_id varchar(10) comment '任务流程编码'
    ,exec_type varchar(10) comment '执行引擎'
    ,exec_sql varchar(2000) comment '执行语句'
    ,source_flow_id varchar(10) comment '数据源配置编码'
    ,troop_flow_id varchar(10) comment '用户群配置编码'
    ,cond_flow_id varchar(10) comment '筛选条件配置编码'
    ,show_flow_id varchar(10) comment '展示配置编码'
    ,exec_flow_id varchar(10) comment '执行配置编码'
    ,data_flow_id varchar(10) comment '结果配置编码'
    ,cycle_type varchar(10) comment '周期类型'
    ,cycle_value varchar(200) comment '周期执行项'
    ,cycle_end_date varchar(20) comment '周期截止时间'
    ,data_file varchar(200) comment '最新结果文件'
    ,data_num varchar(10) comment '最新结果数量'
    ,task_status varchar(10) comment '最新任务执行状态'
    ,begin_time varchar(10) comment '最新执行开始时间'
    ,end_time varchar(10) comment '最新执行结束时间'
    ,status varchar(2) default '1' comment '有效状态'
    ,remark varchar(200) comment '备注'
    ,create_persion varchar(200) comment '创建人'
    ,create_date varchar(20) comment '创建时间'
    ,update_persion varchar(200) comment '修改人'
    ,update_date varchar(20) comment '修改时间'
) DEFAULT CHARSET=utf8, comment=' 配置结果表';

-- 标签配置表
CREATE TABLE tc_gd_tagconfig
 (
 tag_id varchar(10) comment '标签编码'
,tag_name varchar(200) comment '标签名称'
,tag_name_zh varchar(200) comment '标签中文名称'
,tag_from_id varchar(10) comment '数据来源编码'
,tag_class_id varchar(10) comment '标签分类编码'
,tag_data_type varchar(10) comment '标签数据类型'
,tag_show_order varchar(10) comment '标签展示排序'
,is_show varchar(2) comment '是否可展示'
,is_cond varchar(2) comment '是否可做条件'
,status varchar(2) comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
 ) DEFAULT CHARSET=utf8, comment=' 标签配置表';

 CREATE TABLE tc_gd_tagconfig_flow
 (
 tag_id varchar(10) comment '标签编码'
,tag_name varchar(200) comment '标签名称'
,tag_name_zh varchar(200) comment '标签中文名称'
,tag_from_id varchar(10) comment '数据来源编码'
,tag_class_id varchar(10) comment '标签分类编码'
,tag_data_type varchar(10) comment '标签数据类型'
,tag_show_order varchar(10) comment '标签展示排序'
,is_show varchar(2) comment '是否可展示'
,is_cond varchar(2) comment '是否可做条件'
,status varchar(2) comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
 ) DEFAULT CHARSET=utf8, comment=' 标签配置表';

-- 系统用户表
CREATE TABLE tc_auth_user
 (
 persion_id varchar(10) comment '用户编码'
,login_name varchar(200) comment '用户登录名'
,show_name varchar(200) comment '用户昵称'
,true_name varchar(200) comment '用户真实姓名'
,sex varchar(2) comment '用户性别'
,birthday varchar(10) comment '用户生日'
,phone_number varchar(20) comment '用户联系方式'
,email varchar(200) comment '用户邮箱'
,password varchar(200) comment '用户密码'
,user_status varchar(2) comment '用户状态'
,last_login_date varchar(20) comment '用户上次登录时间'
,role_id varchar(10) comment '用户角色编码'
,depart_id varchar(10) comment '用户部门编码'
,group_id varchar(10) comment '用户分组编码'
,begin_date varchar(20) comment '账号有效开始时间'
,end_date varchar(20) comment '账号有效结束时间'
,create_type varchar(20) comment '用户创建方式'
,status varchar(2) comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
,update_persion varchar(200) comment '修改人'
,update_date varchar(20) comment '修改时间'
 ) DEFAULT CHARSET=utf8, comment=' 系统用户表';


-- 用户群表
CREATE TABLE tc_gd_usertroop
 (
 troop_id varchar(10) comment '用户群编码'
,troop_name varchar(200) comment '用户群名称'
,troop_from varchar(200) comment '用户群来源'
,troop_type varchar(20) comment '用户群类别'
,troop_num varchar(10) comment '用户群数量'
,troop_status varchar(10) comment '用户群状态'
,troop_begin_date varchar(10) comment '用户生效开始时间'
,troop_end_date varchar(10) comment '用户生效结束时间'
,source_file_name varchar(200) comment '原始文件名'
,troop_key varchar(200) comment '主键'
,is_user_tag varchar(2) comment '是否使用其中标签'
,tag_flow_id varchar(10) comment '标签配置编码'
,status varchar(2)  DEFAULT 1  comment '有效状态'
,remark varchar(200) comment '备注'
,create_persion varchar(200) comment '创建人'
,create_date varchar(20) comment '创建时间'
,update_persion varchar(200) comment '修改人'
,update_date varchar(20) comment '修改时间'
 ) DEFAULT CHARSET=utf8, comment=' 用户群表';

