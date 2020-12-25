#!/usr/bin/env bash
#commonpath=$(cd "$(dirname "$0")";pwd)/
commonpath=$(dirname $(cd "$(dirname "$0")";pwd))/common/
. ${commonpath}functions.sh $1


#/************************************************************************************
# 程序名称:    dm_etl_index_target.sh <必填>
# 功能描述:    处理衍生指标,生成目标表 <必填>
# 输入参数:    '2020-12-02' <默认T-1>
# 输出参数:    OK - <OK为正常结束，其余为异常>
# 输入资源:
# 输出资源:
# 中间资源:
# 创建人员:    zoukehui@51talk.com <必填>
# 创建日期:    2020-11-10 <必填>
# 版本说明:    V1.0 <必填>
# 修改人员:    zoukehui@51talk.com <必填>
# 修改日期:    2020-11-10 <必填>
# 修改原因:    project init <必填>
# 版本说明:
# 执行说明:
# 公司名称:    51talk
#
#/************************************************************************************


v_deal_date=$v_thisyyyymmdd                 #入参：时间

v_user='dm'                                #hive库名
v_prc="dm_etl_index_target"        #程序名称
v_queue='data_engineering'                  #hive队列
result_code=""                              #程序返回值
check_begin_date=$(date "+%Y-%m-%d %H:%M:%S")
eval $(echo $v_deal_date |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "



function targetTableGenerator()
{
# 先建表,删除,再创建,可改为先改名再创建
# 不带分区

table_id=$1
v_sql="
select * from (
select concat('drop table if exists ',db_user,'.',table_name,' ;create table ' ,db_user,'.',table_name ,' (' ) 
from etl_index_target where 1=1 and status='1' and table_id='${table_id}' limit 1
) a
union
select * from (
SELECT  concat(case when order_num='1' then '' else ',' end ,index_name,'   ',index_data_type) 
from etl_index_target
where 1=1
and status='1'
and table_id='${table_id}'
order by(order_num+0)
) b
union 
select * from (
select concat(' ) ;')
) c

"
create_table_sql=$(mysql $mysqlConn -s   -N -e "$v_sql")

v_step='1'
v_sql="$create_table_sql"
etlSparkSQLLog


# 再插入

# 默认值规则,null和空,统一处理,级别比target_sql高
# target_sql规则,直接获取 最后加上 as index_name
# 变量替换问题

# 配置情况
# 1. 无默认值,无target_sql

# 2. 无默认值,有target_sql

# 3. 有默认值,无target_sql
# 4. 有默认值,有target_sql



v_step='2'
v_sql="
select * from (
select concat(' insert overwrite table  ',db_user,'.',table_name,' select ' ) 
from etl_index_target where 1=1 and status='1' and table_id='${table_id}' limit 1
) a
union 
select * from (
SELECT case when (order_num='1') then index_name
						when (default_value is null or default_value='') and (target_sql is null or target_sql='')  
													then  concat(', ',index_name)
				    when (default_value is null or default_value='') and (target_sql is not  null and  target_sql !='')
													then   concat(', ',target_sql,' as ' ,index_name)
				    when (default_value is not  null and  default_value !='') and (target_sql is   null or   target_sql ='')
													then   concat(',case when ', index_name,' is null or ',index_name,'=\'\' then ',default_value										,	' else ',index_name ,' end as ',index_name)
						when (default_value is not  null and  default_value !='') and (target_sql is not  null and target_sql !='')
													then concat(',case when (', target_sql,') is null or (',target_sql,')=\'\' then ',default_value										,	' else (',target_sql ,') end as ',index_name)
						
				else 	 concat(', ',index_name) end insert_sql				
from etl_index_target
where 1=1
and status='1'
and table_id='${table_id}'
order by(order_num+0)
) b
union
select * from (
select concat(' from  ',db_user,'.',table_name, '_t',table_id,' ;') 
from etl_index_target where 1=1 and status='1' and table_id='${table_id}' limit 1
) c ;
"

insert_sql=$(mysql $mysqlConn -s    -N  -B -r   -e  "$v_sql")

params=$(echo "$insert_sql" |grep   -o "\${[^{]*}")
IFS_OLD=$IFS  #將当前值保存
IFS=$'\n'
for param in $params;do
date_form=
date_deal=
date_form=$(echo $param |awk -F"{|}| " '{print $2}')
date_deal=$(echo $param |awk -F"{|}| " '{print $3}')
param_value=$(dateFormat $(echo $v_thisyyyymmdd |sed "s/-//g") $date_form $date_deal)
insert_sql=$(echo "$insert_sql" |sed "s/$param/$param_value/")


done
IFS=$IFS_OLD  #恢復之前保存的值

v_step='3'
v_sql="$insert_sql"
etlSparkSQLLog

}

targetTableGenerator $2