#!/usr/bin/env bash
#commonpath=$(cd "$(dirname "$0")";pwd)/
commonpath=$(dirname $(cd "$(dirname "$0")";pwd))/common/
. ${commonpath}functions.sh $1


#/************************************************************************************
# 程序名称:    etl_check.sh <必填>
# 功能描述:    数据核查 <必填>
# 输入参数:    '2020-10-20' <默认T-1>
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


v_deal_date=$1                              #入参：时间
v_user='dwd'                                #hive库名
v_prc="dwd_teacher_dim_teacher_base"        #程序名称
v_queue='data_engineering'                  #hive队列
result_code=""                              #程序返回值
check_begin_date=$(date "+%Y-%m-%d %H:%M:%S")
eval $(echo $v_deal_date |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')
#mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

#处理流程：
# 1.获取批量执行的id
#       参数1：日期参数； 参数2：分组参数； 参数3：配置编号
# 2.根据id获取配置
# 3.根据配置生成核查sql
# 4.执行核查sql，生成核查结果
# 5.保存核查结果

group_ids=$2
config_ids=$3



function check_data()
{

group_id=$1
config_id=$2

if [[ "Z$config_id" != "Z" ]];then
config_id=$(echo $config_id |sed "s/,/','/g")
config_id_sql=" and id in ('$config_id') "
fi

if [[ "Z$group_id" != "Z" ]];then
group_id=$(echo $group_id |sed "s/,/','/g")
group_id_sql=" and group_id in ('$group_id') "
fi

#config_id=1
#年月日



# 临时使用,简单完成功能优先
v_sql="SELECT concat_ws('|',COALESCE(cluster_type,''),COALESCE(db_user,''),COALESCE(table_name,'')
,COALESCE(check_type,''),COALESCE(check_col,''),COALESCE(group_col,''),COALESCE(par_cond,''),COALESCE(result_refer,'')
,COALESCE(check_id,''),COALESCE(check_desc,''),COALESCE(id,'')
)
from etl_check_config
where 1=1
and status='1'
$group_id_sql
$config_id_sql;
"

#获取mysql执行结果


#config_result="cdh|dm|dm_finance_fact_cfo_1vn_revenue_detail_day|count|0||dt={YYYY-MM-DD}|"
config_results=$(mysql $mysqlConn -s   -N -e "$v_sql")
IFS_OLD=$IFS  #將当前值保存
IFS=$'\n'

for config_result in $config_results ;do
#echo $config_result
cluster_type=$(echo "$config_result" |awk -F "|" '{print $1}')
db_user=$(echo "$config_result" |awk -F "|" '{print $2}')
table_name=$(echo "$config_result" |awk -F "|" '{print $3}')
check_col=$(echo "$config_result" |awk -F "|" '{print $5}')
check_type=$(echo "$config_result" |awk -F "|" '{print $4}' |sed "s/)/ $check_col )/")  # count(),sum(),count(distinct),sum(distinct )
group_col=$(echo "$config_result" |awk -F "|" '{print $6}')
par_cond=$(echo "$config_result" |awk -F "|" '{print $7}' | sed "s/YYYY/$v_year/;s/MM/$v_month/;s/DD/$v_day/")   # 简单处理,只有一个分区
result_refer=$(echo "$config_result" |awk -F "|" '{print $8}')
check_id=$(echo "$config_result" |awk -F "|" '{print $9}')
check_desc=$(echo "$config_result" |awk -F "|" '{print $10}')
config_id=$(echo "$config_result" |awk -F "|" '{print $11}')
if [[ "$cluster_type" = "emr" ]];then
# 新旧集群配置
#beeline="beeline -u 'jdbc:hive2://10.0.7.205:10001/' --showHeader=false --outputformat=tsv2  "
beeline="hive "
elif [[ "$cluster_type" = "cdh" ]];then
#beeline='beeline -u jdbc:hive2://10.0.3.18:10000/ -n data_engineering -w /opt/workpass/data_engineering.pass'
beeline='hive '
fi



delete_sql="delete from etl_check_result where check_date='$v_deal_date' and config_id='$config_id' ;"
check_log_sql_file=/tmp/check_log_${config_id}_${v_deal_date}_$$.sql
echo "$delete_sql" > $check_log_sql_file
null_group_sql="insert into etl_check_result values (null,'$config_id','$check_id','$check_desc','table_name','$check_col','$group_col','-','0','$v_deal_date','$check_begin_date','');"

# 生成核查sql
if [[ "Z$group_col" != "Z" ]];then

# 需加上null的情况         
v_sql="select concat_ws(',','insert into etl_check_result values (null','\'$config_id\'','\'$check_id\'','\'$check_desc\''
,'\'$db_user\'','\'$table_name\'','\'$check_col\'','\'$group_col\'',concat('\'',cast( COALESCE($group_col ,'null')as string),'\''),concat('\'',cast($check_type as string),'\'')
,'0','\'$v_deal_date\'','\'$check_begin_date\'',concat('\'$check_time\'',')\;'))  
from \`${db_user}.${table_name}\`
where 1=1
$par_cond
group by $group_col ;
"
else
v_sql="select concat_ws(',','insert into etl_check_result values (null','\'$config_id\'','\'$check_id\'','\'$check_desc\''
,'\'$db_user\'','\'$table_name\'','\'$check_col\'','\'$group_col\'','\'-\'',concat('\'',cast( $check_type as string),'\''),'0','\'$v_deal_date\'','\'$check_begin_date\'',concat('\'$check_time\'',')\;'))  
from \`${db_user}.${table_name}\`
where 1=1
$par_cond ;
"
fi

echo "check_sql:$v_sql"
IFS=$IFS_OLD  #恢復之前保存的值

# 执行
#echo $beeline
echo "$beeline"
$beeline  -e "$v_sql" >> $check_log_sql_file

if [[ $(wc -l $check_log_sql_file) = 1 ]];then
echo "$null_group_sql" >>  $check_log_sql_file
fi
#v_sql=" source $check_log_sql_file ;"
mysql   $mysqlConn  -s  -N < "$check_log_sql_file"

rm "$check_log_sql_file"

done
IFS=$IFS_OLD  #恢復之前保存的值

}


# 执行部分,单条执行,按分组执行,全部执行
#修改为组内并行,组间串行
if [[ "Z$group_ids" = "Z" ]];then
get_group_sql="SELECT DISTINCT group_id from etl_check_config where status='1';"
group_ids=$(mysql $mysqlConn -s    -N -e "$get_group_sql")
#else
#group_ids=$(echo $group_ids |sed "s/,/','/g")

fi

for check_group_id in $group_ids ;do
check_data $check_group_id $config_ids &
done


wait

