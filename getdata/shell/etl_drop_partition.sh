#!/usr/bin/env bash
#commonpath=$(cd "$(dirname "$0")";pwd)/
#commonpath=$(dirname $(cd "$(dirname "$0")";pwd))/common/
. ${commonpath}functions.sh $1


#/************************************************************************************
# 程序名称:    etl_drop_partition.sh <必填>
# 功能描述:    数据清理程序 <必填>
# 输入参数:    '2020-10-20' <默认T-1>
# 输出参数:    OK - <OK为正常结束，其余为异常>
# 输入资源:
# 输出资源:
# 中间资源:
# 创建人员:    zoukehui@51talk.com <必填>
# 创建日期:    2020-11-28 <必填>
# 版本说明:    V1.0 <必填>
# 修改人员:    zoukehui@51talk.com <必填>
# 修改日期:    2020-11-28 <必填>
# 修改原因:    project init <必填>
# 版本说明:
# 执行说明:
# 公司名称:    51talk
#
#/************************************************************************************

v_deal_date=$1                              #入参：时间
v_user='tmp'                                #hive库名
v_prc="etl_drop_partition"        #程序名称
v_queue='data_engineering'                  #hive队列
result_code=""                              #程序返回值
eval $(echo $v_deal_date |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')
mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "

#处理流程：
# 1.获取批量执行的id
#       参数1：日期参数； 参数2：分组参数； 参数3：配置编号
# 2.根据id获取配置
# 3.根据配置生成删除数据sql
# 4.执行删除数据sql，获取执行结果
# 5.记录最新执行结果

# group_ids=$2
# config_ids=$3

function drop_partition()
{
group_id=$1
config_id=$2
mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "

if [[ "Z$config_id" != "Z" ]];then
config_id=$(echo $config_id |sed "s/,/','/g")
config_id_sql=" and config_id in ('$config_id') "
fi

if [[ "Z$group_id" != "Z" ]];then
group_id=$(echo $group_id |sed "s/,/','/g")
group_id_sql=" and group_id in ('$group_id') "
fi


v_step='1'
# 完成功能优先
v_sql="SELECT concat_ws('|',COALESCE(config_id,''),COALESCE(group_id,''),COALESCE(db_user,'')
,COALESCE(table_name,''),COALESCE(drop_partition_form,''),COALESCE(data_save_days,'')
)
from etl_drop_partition_config
where 1=1
and status='1'
$group_id_sql
$config_id_sql;
"

#获取mysql执行结果
config_results=$(mysql $mysqlConn -s   -N -e "$v_sql")

IFS_OLD=$IFS  #將当前值保存
IFS=$'\n'

for config_result in $config_results ;do
config_id=$(echo "$config_result" |awk -F "|" '{print $1}')
group_id=$(echo "$config_result" |awk -F "|" '{print $2}')
db_user=$(echo "$config_result" |awk -F "|" '{print $3}')
table_name=$(echo "$config_result" |awk -F "|" '{print $4}')
drop_partition_form=$(echo "$config_result" |awk -F "|" '{print $5}')
data_save_days=$(echo "$config_result" |awk -F "|" '{print $6}')
drop_partition_form1=$(echo "$drop_partition_form" |awk -F "'" '{print $1}')
drop_partition_form2=$(echo "$drop_partition_form" |awk -F "'" '{print $2}')
drop_partition_form3=$(echo "$drop_partition_form" |awk -F "'" '{print $3}')

drop_partition_value=$(dateFormat $(echo $v_deal_date |sed "s/-//g") $drop_partition_form2 $data_save_days)

IFS=$IFS_OLD  #恢復之前保存的值

start_time=$(date +"%Y-%m-%d %H:%M:%S")

#删除分区
v_step='2'
v_sql=" alter table $db_user.$table_name drop partition if exists ($drop_partition_form1 '$drop_partition_value' $drop_partition_form3);"
etlSparkSQLLog


#记录执行结果
v_step='end'
v_sql="
update etl_drop_partition_config set last_exec_date='${start_time}', last_exec_cmd='${v_sql}',last_exec_result='${result_code}'
where config_id='${config_id}'
and status='1';
"
mysql $mysqlConn -s   -N -e "$v_sql"
done
}


# 自动增加配置,方便其他人使用
function get_add_config_auto(){
while getopts "t:ghaf" arg #选项后面的冒号表示该选项需要参数
do
    case $arg in
         f)
            is_force='1'
            ;;
         a)
            ;;
         g) 
            ;;
         h)
            ;;
         ?)  #当有不认识的选项的时候arg为?
        echo "unkonw argument"
    exit 1
    ;;
    esac
done

#必要参数
# group_id=               #分组id,不同的组之间是并行执行的
# db_user=                #数据库用户名
# table_name=             #表名
# table_name_desc=        #表中文描述
# drop_partition_form=    #分区格式
# data_save_days=         #清理周期
# create_user=            #创建人

#检查是否已添加
#同一张表[相同的清理周期]只有一条有效记录
v_sql=" select config_id from etl_drop_partition_config 
where 1=1
and status='1'
and lower(db_user)=lower('${db_user}')
and lower(table_name)=lower(${table_name});" 
is_config=$(mysql $mysqlConn -s    -N -e "$v_sql")

# 需增加可以修改的参数
if [[ "Z$config_id" != "Z" ]];then
    if  [[ "Z$is_force" = "Z1" ]];then
        v_sql=" update etl_drop_partition_config set status='0' 
        where   status='1'
        and lower(db_user)=lower('${db_user}')
        and lower(table_name)=lower(${table_name});" 
        mysql $mysqlConn -s    -N -e "$v_sql"
    else
        echo "config exists~ " >&2
        echo "$config_id"
        return 0
        
    fi
fi


#默认参数
# 获取config_id,序列
# " select nextval('seq_common_id') "
get_config_id="select nextval('seq_common_id');"
config_id=$(mysql  $mysqlConn -s   -N -e "$get_config_id")
status='1'
create_date=$(date +"%Y-%m-%d %H:%M:%S")
remark='auto'


v_sql=" insert into  
etl_drop_partition_config(
    config_id
    ,group_id
    ,db_user
    ,table_name
    ,table_name_desc
    ,drop_partition_form
    ,data_save_days
    ,status
    ,create_user
    ,create_date
    ,remark) 
values('${config_id}'
        ,'${group_id}'
        ,'${db_user}'
        ,'${table_name}'
        ,'${table_name_desc}'
        ,'${drop_partition_form}'
        ,'${data_save_days}'
        ,'${status}'
        ,'${create_user}'
        ,'${create_date}'
        ,'${remark}'
        );"
        mysql $mysqlConn -s    -N -e "$v_sql"
        echo "$config_id"
        return 0
}

# 执行部分,单条执行,按分组执行,全部执行
#修改为组内并行,组间串行
## if [[ "Z$group_ids" = "Z" ]];then
## get_group_sql="SELECT DISTINCT group_id from etl_drop_partition_config where status='1';"
## group_ids=$(mysql $mysqlConn -s    -N -e "$get_group_sql")
## #else
## #group_ids=$(echo $group_ids |sed "s/,/','/g")
## 
## fi
## 
## for check_group_id in $group_ids ;do
## drop_partition $group_ids $config_ids &
## done
## 
## 
## wait

