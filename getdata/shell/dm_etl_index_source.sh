#!/usr/bin/env bash
#commonpath=$(cd "$(dirname "$0")";pwd)/
commonpath=$(dirname $(cd "$(dirname "$0")";pwd))/common/
. ${commonpath}functions.sh $1


#/************************************************************************************
# 程序名称:    dm_etl_index_source.sh <必填>
# 功能描述:    宽表生成s表 <必填>
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
v_prc="dm_etl_index_source"        #程序名称
v_queue='data_engineering'                  #hive队列
result_code=""                              #程序返回值
check_begin_date=$(date "+%Y-%m-%d %H:%M:%S")
eval $(echo $v_deal_date |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "



function sourceTableGenerator()
{
table_id=$1
group_id=$2
source_id=$3

if [[ "Z$table_id" != "Z" ]];then
table_id=$(echo $table_id |sed "s/,/','/g")
table_id_sql=" and table_id in ('$table_id') "
fi

if [[ "Z$source_id" != "Z" ]];then
source_id=$(echo $source_id |sed "s/,/','/g")
source_id_sql=" and source_id in ('$source_id') "
fi

if [[ "Z$group_id" != "Z" ]];then
group_id=$(echo $group_id |sed "s/,/','/g")
group_id_sql=" and group_id in ('$group_id') "
fi

v_sql="SELECT concat_ws('|'
,COALESCE(source_id,'')
,COALESCE(source_type,'')
,COALESCE(group_id,'')
,COALESCE(db_user,'')
,COALESCE(table_name,'')
)
from etl_index_source
where 1=1
and status='1'
$table_id_sql
$group_id_sql
$source_id_sql
order by(source_level+0)
;
"
source_results=$(mysql $mysqlConn -s   -N -e "$v_sql")
IFS_OLD=$IFS  #將当前值保存
IFS=$'\n'

for source_result in $source_results ;do
source_id=$(echo "$source_result" |awk -F "|" '{print $1}')
db_user=$(echo "$source_result" |awk -F "|" '{print $4}')
table_name=$(echo "$source_result" |awk -F "|" '{print $5}')


# 获取 sql
v_sql="select  cast(source_sql as CHAR(100000)) 
 from etl_index_source
where 1=1
and status='1'
and source_id='$source_id' ;
"
#echo $v_sql
IFS=$IFS_OLD  #恢復之前保存的值

source_sql=$(mysql $mysqlConn -s    -N  -B -r   -e  "$v_sql")

params=$(echo "$source_sql" |grep   -o "\${[^{]*}")
IFS=$'\n'
for param in $params;do
date_form=
date_deal=
date_form=$(echo $param |awk -F"{|}| " '{print $2}')
date_deal=$(echo $param |awk -F"{|}| " '{print $3}')
param_value=$(dateFormat $(echo $v_thisyyyymmdd |sed "s/-//g") $date_form $date_deal)
source_sql=$(echo "$source_sql" |sed "s/$param/$param_value/")


done
IFS=$IFS_OLD  #恢復之前保存的值

v_step='$source_id'
v_sql="drop table if exists $db_user.${table_name}_source_$source_id;
create table $db_user.${table_name}_source_$source_id
as
${source_sql};
"

etlHiveLog_stable
#echo "create_sql:$v_sql"
done
IFS=$IFS_OLD  #恢復之前保存的值

}




sourceTableGenerator $2 $3 $4

## group_ids=$2
## source_ids=$3
## 
## if [[ "Z$group_ids" = "Z" ]];then
## get_group_sql="SELECT DISTINCT group_id from etl_check_config where status='1';"
## group_ids=$(mysql  $mysqlConn -s   -N -e "$get_group_sql")
## #else
## #group_ids=$(echo $group_ids |sed "s/,/','/g")
## 
## fi
## 
## for check_group_id in $group_ids ;do
## check_data $check_group_id $config_ids &
## done
## 
## 
## wait
