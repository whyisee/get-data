#!/usr/bin/env bash

#v_thisyyyymmdd=$1                                                #当日年月日变量，如当前2016年3月3日，则为20160302(一般当前日期的前一天)
v_thisyyyymmdd='2020-12-18'                                                #当日年月日变量，如当前2016年3月3日，则为20160302(一般当前日期的前一天)
if [ ! $v_thisyyyymmdd ]; then
    v_thisyyyymmdd=`date -d "yesterday" +%Y-%m-%d`
fi
v_lastyyyymmdd=`date -d"yesterday $v_thisyyyymmdd" +%Y-%m-%d`      #昨日年月日变量，如当前2016年3月3日，则为20160302
v_llyyyymmdd=`date -d"2 day ago $v_thisyyyymmdd" +%Y-%m-%d`
v_nextyyyymmdd=`date -d"-1 day ago $v_thisyyyymmdd" +%Y-%m-%d`     #后一日年月日变量,如当前2016年3月3日，则为20160303(一般是当前系统日期时间)
v_ltmnyyyymmdd=`date -d"1 month ago $v_thisyyyymmdd" +%Y-%m-%d`    #上月同日年月日变量，如当前2016年3月3日，则为20160203
v_thisyyyymm=`date -d"0 month ago $v_thisyyyymmdd" +%Y-%m`        #本月，取事务日期的YYYYMM，主要用于月处理
v_thisyyyy=`date -d"0 month ago $v_thisyyyymmdd" +%Y`            #本年，取事务日期的YYYY，主要用于年处理
v_dealyyyymm=`date -d"1 month ago $v_thisyyyymmdd" +%Y-%m`        #处理月，如当前2016年3月3日，则为201602
v_lastyyyymm=`date -d"2 month ago $v_thisyyyymmdd" +%Y-%m`        #处理前1个月，如当前2016年3月3日，则为201601
v_last2yyyymm=`date -d"3 month ago $v_thisyyyymmdd" +%Y-%m`       #处理前2个月，如当前2016年3月3日，则为201512
v_last3yyyymm=`date -d"4 month ago $v_thisyyyymmdd" +%Y-%m`       #处理前3个月，如当前2016年3月3日，则为201511
v_last4yyyymm=`date -d"5 month ago $v_thisyyyymmdd" +%Y-%m`       #处理前2个月，如当前2016年3月3日，则为201512
v_last5yyyymm=`date -d"6 month ago $v_thisyyyymmdd" +%Y-%m`       #处理前3个月，如当前2016年3月3日，则为201511
v_dlldyyyymmdd=`date -d"last day $v_thisyyyymmdd" +%Y-%m-%d`       #处理月的最后一天，如当前2016年3月3日，则为20160229
v_dlldyyyymmdd1=`date -d"0 month ago $v_thisyyyymmdd" +%Y-%m-01`
v_dlldyyyymmdd=`date -d"yesterday $v_dlldyyyymmdd1" +%Y-%m-%d`     #处理前1个月，如当前2016年3月3日，则为201601
v_thisyymmdd=${v_thisyyyymmdd:2:6}                               #当日年月日变量
v_thismmdd=`date -d"$v_thisyyyymmdd" +%m-%d`                      #当日月日变量
v_thisdd=`date -d"$v_thisyyyymmdd" +%d`                          #当日变量
v_thismyyyyymmdd=`date +%Y-%m-01`
v_lmyyyyymmdd=`date -d"$v_thismyyyyymmdd last day" +%Y-%m-%d `         #系统月最后一天

#beeline_hive="beeline -u 'jdbc:hive2://emr-header-1:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2'"
beeline_hive="beeline -u 'jdbc:hive2://10.0.7.228:10000/' -n data_engineering -w /opt/workpass/data_engineering.pass"
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

echo
etlHiveLog()
{
#增加判断是否可执行
if execWorkCheck ;then

echo "$v_sql"
start_time=$(date +"%Y-%m-%d %H:%M:%S")

$beeline_hive -e "$v_sql;"


if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time
echo $result_code

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval}

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi

fi
}


echo
etlSparkSQLLog()
{
  if execWorkCheck ;then
echo "$v_sql"
start_time=$(date +"%Y-%m-%d %H:%M:%S")

#beeline="beeline -u 'jdbc:hive2://10.0.7.205:10001/' -n data_engineering -w /opt/workpass/data_engineering.pass"
#beeline="spark-sql --proxy-user hadoop --name $v_user-$v_prc-$v_step"
beeline="spark-sql --queue root.users.azkaban --name $v_user-$v_prc-$v_step"
$beeline -e "$v_sql;"


if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time
echo $result_code

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval}

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi
fi
}

etlHiveLog_stable()
{
#增加判断是否可执行
if execWorkCheck ;then

echo "$v_sql"
start_time=$(date +"%Y-%m-%d %H:%M:%S")
beeline="beeline -u 'jdbc:hive2://10.0.7.205:10001/' -n data_engineering -w /opt/workpass/data_engineering.pass"
$beeline -e "$v_sql;"

#$beeline_hive -e "$v_sql;"


if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time
echo $result_code

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval}

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi

fi
}


#/************************************************************************************
# 功能描述: 获取hive执行结果
# 使用示例: 同etlSparkSQLLog完全一致,只修改了日志信息重定向
# 输入参数: 
# 输出结果: sql执行结果
# 创建人员: zoukehui
# 创建日期: 2020-11-19 13:53:35
#/************************************************************************************
#
etlSparkSQLLogResult()
{
if execWorkCheck ;then
#修改为错误重定向,下同
echo "$v_sql"   >&2
start_time=$(date +"%Y-%m-%d %H:%M:%S")

beeline="beeline -u 'jdbc:hive2://10.0.7.205:10001/'  -n data_engineering -w /opt/workpass/data_engineering.pass --showHeader=false --outputformat=tsv2 "
#$beeline -e "$v_sql"
hive -e "$v_sql"

if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time >&2
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time >&2
echo $result_code >&2

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval} >&2

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi
fi
}

#/************************************************************************************
# 功能描述: 获取mysql执行结果
# 使用示例: 同etlSparkSQLLog完全一致,只修改了日志信息重定向
# 输入参数: 
# 输出结果: sql执行结果
# 创建人员: zoukehui
# 创建日期: 2020-11-19 13:53:35
#/************************************************************************************
#
etlMysqlLogResult()
{
#执行控制
if execWorkCheck ;then
#修改为错误重定向,下同
echo "$v_sql"   >&2
start_time=$(date +"%Y-%m-%d %H:%M:%S")

mysql="mysql -s -u${user} -p$(hdfs dfs -cat ${pass_file} ) -h${host} -P${port} -D${db} "
$mysql -e "$v_sql;"


if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time >&2
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time >&2
echo $result_code >&2

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval} >&2

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi
fi

}




etlSqoopLog()
{
  start_time=$(date +"%Y-%m-%d %H:%M:%S")

  echo ${v_sqoop_cmd}

  eval ${v_sqoop_cmd}

  if [ $? -eq 0 ]; then
    result_code="OK"
  else
    result_code="ERROR"
  fi

  end_time=$(date +"%Y-%m-%d %H:%M:%S")
  echo $start_time
  v_sqoop_cmd=${v_sqoop_cmd//"\`"/"\\\`"}
  v_sqoop_cmd=${v_sqoop_cmd//"\""/"\\\""}
  v_sql_log=`echo "$v_sqoop_cmd" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
  echo $end_time
  echo $result_code

  sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
  eval ${sqoop_eval}

  if [ "$result_code" = "ERROR" ]; then
   echo $result_code
   exit 1
  fi

}

#/************************************************************************************
# 功能描述: 数据入ck
# 输出结果: 
# 创建人员: zoukehui
# 创建日期: 2020-12-09 13:53:35
#/************************************************************************************
#
function etlImportCK(){
# 1. 先从配置表中获取字段信息
# 2. 后续增加自动获取


### 1. 获取建表语句
### 2. 生成建表sql
### 3. 导入数据


mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

table_name=${v_prc}
db_user=${v_user}


while getopts "c:p:t:" arg #选项后面的冒号表示该选项需要参数
do
    case $arg in
         c)
            local is_create="$OPTARG"
            ;;
         p)
            local partition_key="$OPTARG"
            ;;
         t)
            local partition_value="$OPTARG"
            ;;
         ?)  #当有不认识的选项的时候arg为?
        echo "unkonw argument"
    exit 1
    ;;
    esac
done
#重置
OPTIND=
: ${partition_key:="partition_id"}
: ${partition_value:="$v_thisyyyymmdd"}


table_id=$1
v_sql="
select * from (
select concat(' create table  if not exists ' ,db_user,'.',table_name ,' (' ) 
from etl_index_target where 1=1 and status='1' and table_name='${table_name}' limit 1
) a
union
select * from (
SELECT  concat(case when order_num='1' then 'deal_date String COMMENT \'数据日期\' ,' else ',' end ,index_name,'   ',REPLACE(REPLACE(index_data_type,'int','Int'),'string','String') ,' COMMENT \'',index_name_desc,'\'')
from etl_index_target
where 1=1
and status='1'
and table_name='${table_name}'
order by(order_num+0)
) b
union 
select * from (
select concat(' ) ENGINE = MergeTree()  PARTITION BY deal_date ORDER BY ',index_name ,' SETTINGS index_granularity = 8192')
from  etl_index_target 
where 1=1
and status='1'
and table_name='${table_name}'
and order_num='1'
) c
"
create_table_sql=$(mysql $mysqlConn -s   -N -e "$v_sql")

#配置表建表
if [[ "Z$is_create" = "Z1" ]];then
v_step=''
v_sql="$create_table_sql"
etlCKLog
fi
v_step=''
#etlSparkSQLLog
v_sql="
ALTER TABLE ${db_user}.${table_name} DROP partition '$partition_value';
"
etlCKLog

rm -rf /tmp/${db_user}_${table_name}/*
python3 /mnt/disk1/workspace/emr-workflow/utils/hive_table_2ck.py -hp /user/hive/warehouse/${db_user}.db/${table_name}/${partition_key}=$partition_value -d $db_user -t ${table_name}


}

etlCKLog()
{
#增加判断是否可执行
if execWorkCheck ;then

echo "$v_sql"
start_time=$(date +"%Y-%m-%d %H:%M:%S")

clickhouse='clickhouse-client -h cc-2ze79ing76muz352g.ads.aliyuncs.com  --port 3306 --config-file /mnt/disk1/workspace/emr-workflow/conf/ck-data_clickhouse.xml  --multiquery '


$clickhouse -q "$v_sql;"


if [ $? -eq 0 ]; then
  result_code="OK"
else
  result_code="ERROR"
fi
end_time=$(date +"%Y-%m-%d %H:%M:%S")
echo $start_time
v_sql=${v_sql//"\`"/"\\\`"}
v_sql_log=`echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
echo $end_time
echo $result_code

sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
eval ${sqoop_eval}

if [ "$result_code" = "ERROR" ]; then
 echo $result_code
 exit 1
fi

fi
}


#/************************************************************************************
# 功能描述: exec 能否执行校验,if 条件使用
# 使用示例: step=1,2,4-6 ;v_step=5 ;execWorkCheck ; echo $?   #0 ^排除慎用,除非单个步骤
# 输入参数: 全局变量 step:输入的步骤  ,v_step:当前的步骤
# 输出结果: 0-可执行,1-不可执行
# 创建人员: zoukehui
# 创建日期: 2020-11-11 13:53:35
#/************************************************************************************
#
function execWorkCheck(){
# step 包含情况:
# 1. ALL
# 2. ,1,
# 3. ,1,2,
# 4. ,2-5,
# 5. ,-5,
# 6. ,2-,
# 7. ,2,5-,
# 8. ,2,5-7,9-12,14,
# 9 ,^1,
# 10 ,^1-3,

# 如果包含- 则取出所有-两端的数字,和当前v_step比较,再加上取反
# 按逗号分隔处理

#旧程序跳过
if [[ "Z$step" = "Z" ]];then
return 0
fi

for check_step in  $(echo $step|sed "s/,/ /g");do
if [[ $check_step = "ALL" ]];then
return 0
fi
# 先判断是否排除,去掉^
is_exclude=$(echo $check_step |grep "^^" |wc -l)
check_step=$(echo $check_step |sed "s/\^//g")
#echo $check_step >&2
if [[ $(echo $check_step |grep "-" |wc -l) -ge 1 ]];then
    eval $(echo $check_step |awk -F "-" '{print "check_step_v1="$1";check_step_v2="$2}')
    check_step_v1=${check_step_v1:=0}
    check_step_v2=${check_step_v2:=v_step}

    [[ $v_step -ge $check_step_v1 ]] && [[ $v_step -le $check_step_v2 ]] 
else
    [[ $(echo $step |grep ",$v_step," |wc -l) -ge 1 ]] 
fi
result=$?

#多次判断,满足一次就退出,有矛盾的条件不做处理
if [[ $((is_exclude^result)) = 0 ]];then 
return $((is_exclude^result))
fi

done
return 1
# [[ $step = "ALL" ]]||[[ `echo $step |grep ",$v_step," |wc -l` -ge 1 ]]

}


function main()
{
if ([ Z"$2" = "Z" ]||[ Z"$2" = "Z0" ]);then
    step="ALL"
    work $@
else
    for  step in $(echo $2|sed "s/,/ /g");do
    step=","$step","
    work $@
    done
fi
}

dropPartition()
{
  drop_day_before=7
  partition_id=`date -d"$drop_day_before day ago" +%Y-%m-%d`
  drop_sql="alter table \`${hive_db}\`.\`${hive_table}\` drop if exists partition (partition_id<='${partition_id}');"
  echo ${drop_sql}
  $beeline_hive -e "$drop_sql"
}

dropDwPartition()
{
  #drop_day_before=3
  #partition_id=`date -d"$drop_day_before day ago" +%Y-%m-%d`
  #drop_sql="alter table \`${v_user}\`.\`${v_prc}\` drop if exists partition (partition_id<='${partition_id}');"
  #echo ${drop_sql}
  #$beeline_hive -e "$drop_sql"

  #修改为使用配置表
  drop_partition "" $(get_add_dpconfig_auto )
}




#/************************************************************************************
# 功能描述: 多张表合并成一张表
# 使用示例: gatherTables  tmp.test_1119001 tmp.talk_user_order_text,tmp.dwd_user_dim_user_base_pay_type stu_id
# 输入参数: $1-结果表 $2-参加合并的小表 $3-关联使用的key
# 输出结果: $target_table
# 创建人员: zoukehui
# 创建日期: 2020-11-19 11:53:35
#/************************************************************************************
#
gatherTables()
{
#多表合并为一表,参照java方法GatherToOneCdr(),简单处理
#要求:需合并的表无分区,可带上分区名
#join_key full join
#多表,隔开
#相同字段 COALESCE() 函数按顺序获取第一个不为null的值

#思路
#先把所有表的字段 describe 出来重定向到临时文件,加上表别名再全部重定向到一个文件中,取出重复字段,循环重复字段先删除合并文件中的重复字段,再加上COALESCE(重复字段).
v_user=${v_user:='tmp'}               #hive库名
v_prc=${v_prc:="gatherTables"}       #程序名称
v_queue=${v_queue:='data_engineering'}       #hive队列

target_table=$1
source_tables=$2
join_key=$3
tmp_file=/tmp/gatherTables_${target_table}_$$

if  [ -z $v_user ];then
v_user="tmp"
fi
i=1
from_sql=" from "

for table_i in `echo "$source_tables"| sed "s/,/\n/g"` ;do
v_step="1"
v_sql="
use $v_user ;describe $table_i;"
etlSparkSQLLogResult  > ${tmp_file}_${table_i} &

done 
wait

for table_i in `echo "$source_tables"| sed "s/,/\n/g"` ;do
v_step="1"
v_sql="
use $v_user ;describe $table_i;"
#etlSparkSQLLogResult  > ${tmp_file}_${table_i}
cat ${tmp_file}_${table_i} |awk -F "\t" -v i=$i '{print "T"i"."$1}' >> ${tmp_file}_all
if [ $i -eq 1 ];then
from_sql=${from_sql}" $table_i T$i "
else 
from_sql=${from_sql}" FULL OUTER JOIN $table_i T$i on T$i.$join_key=T1.$join_key  "
fi
i=$((i+1))
done

uniq_cols=`cat ${tmp_file}_all |awk -F "." '{print $2}' |sort |uniq -d `
#echo "$uniq_cols"
for uniq_col in `echo "$uniq_cols"`;do
#a=`cat gatherTables_all| grep $uniq_col |sed "s/\n/,/g"`
a=$(cat ${tmp_file}_all |grep -w $uniq_col |awk '{if (NR>1){print ","$1}else {print $1}}' )
a=$(echo $a)
sed -i "/\<$uniq_col\>/d" ${tmp_file}_all
echo "COALESCE( $a )  as $uniq_col " >> ${tmp_file}_all
done
#cat gatherTables_all
select_sql="drop table  IF EXISTS $target_table ;create table  $target_table as select distinct "`cat ${tmp_file}_all |awk '{if (NR>1){print ","$0}else {print $0}}' `

v_step="end"
v_sql=${select_sql}"  "${from_sql}" ;"
#echo "$v_sql"
etlHiveLog_stable
rm ${tmp_file}*


}

analyzeTableOds()
{
  v_sql="analyze table \`$hive_db.$hive_table\` compute statistics;"
  echo $v_sql
  $beeline_hive -e "$v_sql"

}
analyzeTableOdsDt()
{
  v_sql="analyze table \`$hive_db.$hive_table\` PARTITION(dt='$v_thisyyyymmdd') compute statistics;"
  echo $v_sql
  $beeline_hive -e "$v_sql"

}

analyzeTableOther()
{
  v_sql="analyze table \`$v_user.$v_prc\` compute statistics;"
  echo $v_sql
  $beeline_hive -e "$v_sql"

}

analyzeTableOtherPartitionDt()
{
  v_sql="analyze table \`$v_user.$v_prc\` PARTITION(dt='$v_thisyyyymmdd') compute statistics;"
  echo $v_sql
  $beeline_hive -e "$v_sql"

}


#/************************************************************************************
# 功能描述: 日期处理函数
# 使用示例: dateFormat 20180408  YYYY-MM-DD  D-1,m-1,y-1
# 输入参数: $1-基础日期字符串格式YYYYMMDD $2-输出日期的格式 $3-日期计算
# 说明1: 输出日期格式支持YYYYMMDD,YYYY-MM-DD,YYYY-MM,YYYY/MM/DD,MMDD,YYMMDD等
# 说明2: 日期计算支持日D,周W,月M的加减,如D+1,M-1,W+1,Y+1,支持多个处理操作逗号分隔
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#
function dateFormat()
{
# 去掉特殊符号
local deal_date=$(echo $1 |sed "s/-//g;s'/''g;s'\\\\''g")
rs_format=$2
deal_ops=$3


if [ Z"$deal_ops" = "Z" ];then
  rs_format=`echo $rs_format |tr 'a-z' 'A-Z' |sed  "s/YYYY/%Y/;s/MM/%m/;s/DD/%d/"`
  rs_date=`date -d "$deal_date" +$rs_format`
else 
  rs_format=$(echo $rs_format |tr 'a-z' 'A-Z' |sed  "s/YYYY/%Y/;s/MM/%m/;s/DD/%d/;s/YY/%y/")
  deal_ops=$(echo $deal_ops |sed "s/,/ /g")
  for deal_op in $deal_ops ;do
    #echo $deal_op >&2
    eval $(echo $deal_op | awk -F '[-+]' '{print "op_type="$1";op_value="$2;}')
    if [ Z"`echo $deal_op |grep "+" |wc -l `" = "Z1" ];then
      op_value=`echo -1*$op_value |bc`
    fi

    op_type=`echo $op_type |tr 'a-z' 'A-Z'`
    op_type_1=days
    if [ $op_type = "D" ];then
      op_type_1="days"
    elif [ $op_type = "W" ];then
      op_type_1="days"
      op_value=`echo 7*$op_value |bc`
    elif [ $op_type = "M" ];then
      op_type_1="months"
    elif [ $op_type = "Y" ];then
      op_type_1="months"
      deal_date=$((deal_date-10000*op_value))
      op_value=0
    else
      echo "op_type in:d/w/m/y" >&2
      return 1
    fi

    rs_date=$(date -d "$op_value  $op_type_1  ago $deal_date" +$rs_format)
    deal_date=$(date -d "$op_value  $op_type_1  ago $deal_date" +%Y%m%d )
    #echo $deal_date >&2

  done
fi
echo $rs_date
}

#/************************************************************************************
# 功能描述: 日期循环函数
# 使用示例: dateRange 20180408 20180508  YYYY-MM-DD  
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#
function dateRange()
{
# 第一个参数是开始日期, 第二个参数是截止日期 , 第三个参数是 输出格式 ,默认yyyy-mm-dd
local begin_date=$(echo $1 |sed "s/-//g")
local end_date=$(echo $2 |sed "s/-//g")
local date_form=$3
local date_form=${date_form:="yyyy-mm-dd"}
local this_date=$(dateFormat $begin_date $date_form )
echo "$this_date"
this_date=$(echo "$this_date" |sed "s/-//g")

while [ $this_date -lt $end_date ];do
this_date=$(dateFormat $this_date $date_form d+1)
echo "$this_date"
this_date=$(echo "$this_date" |sed "s/-//g" )
done
}


#/************************************************************************************
# 功能描述: 删分区函数
# 使用示例: drop_partition ""  10000001
# 使用示例2: 可以和获取配置id同时使用  drop_partition "" $(get_add_dpconfig_auto)
# 输入参数: $1-分组id(不清楚group_id时,可使用空),$2-配置id
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 
# 创建人员: zoukehui
# 创建日期: 2020-11-30 11:53:35
#/************************************************************************************
#
function drop_partition()
{
local group_id=$1
local config_id=$2

# local mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

if [[ "Z$config_id" != "Z" ]];then
local config_id=$(echo $config_id |sed "s/,/','/g")
local config_id_sql=" and config_id in ('$config_id') "
fi

if [[ "Z$group_id" != "Z" ]];then
local group_id=$(echo $group_id |sed "s/,/','/g")
local group_id_sql=" and group_id in ('$group_id') "
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

drop_partition_value=$(dateFormat $(echo $v_thisyyyymmdd |sed "s/-//g") $drop_partition_form2 $data_save_days)

IFS=$IFS_OLD  #恢復之前保存的值

start_time=$(date +"%Y-%m-%d %H:%M:%S")

#删除分区
v_step='2'
v_sql=" alter table $db_user.$table_name drop  if exists partition ($drop_partition_form1 '$drop_partition_value' $drop_partition_form3);"
etlSparkSQLLog

v_sql_log=$(echo "$v_sql" | awk -v v="'" -v r="''" '{gsub(v,r)}1')

#记录执行结果
v_step='end'
v_sql="
update etl_drop_partition_config set last_exec_date='${start_time}', last_exec_cmd='${v_sql_log}',last_exec_result='${result_code}'
where config_id='${config_id}'
and status='1';
"
mysql $mysqlConn -s   -N -e "$v_sql"
done
}



#/************************************************************************************
# 功能描述: 获取/增加删除分区配置id
# 使用示例: config_id=$(get_add_dpconfig_auto)
# 使用示例2: 可以和删除分区同时使用  drop_partition "" $(get_add_dpconfig_auto)
# 输入参数: -f 强制更新配置,勿轻易使用,需要使用的全局变量见函数内部使用示例
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 配置id
# 创建人员: zoukehui
# 创建日期: 2020-11-30 11:53:35
#/************************************************************************************
#
function get_add_dpconfig_auto(){

while getopts "t:T:D:af" arg #选项后面的冒号表示该选项需要参数
do
    case $arg in
         f)
            local is_force='1'
            ;;
         a)
            ;;
         T) 
            local table_name="$OPTARG"
            ;;
         D)
            local db_user="$OPTARG"
            ;;
         ?)  #当有不认识的选项的时候arg为?
        echo "unkonw argument"
    exit 1
    ;;
    esac
done
#重置
OPTIND=
#必要参数
# group_id=               #分组id,不同的组之间是并行执行的
# db_user=                #数据库用户名
# table_name=             #表名
# table_name_desc=        #表中文描述
# drop_partition_form=    #分区格式
# data_save_days=         #清理周期
# create_user=            #创建人

: ${db_user:="$v_user"}
: ${table_name:="$v_prc"}
: ${drop_partition_form:="partition_id=\'yyyy-mm-dd\'"}
: ${create_user:="admin"}
: ${group_id:="default"}
: ${data_save_days:="d-10"}


#\`${v_user}\`.\`${v_prc}\`


# mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

#检查是否已添加
#同一张表[相同的清理周期]只有一条有效记录
v_sql=" select config_id from etl_drop_partition_config 
where 1=1
and status='1'
and lower(db_user)=lower('${db_user}')
and lower(table_name)=lower('${table_name}');" 
is_config=$(mysql $mysqlConn -s    -N -e "$v_sql")

# 需增加可以修改的参数
if [[ "Z$is_config" != "Z" ]];then
    if  [[ "Z$is_force" = "Z1" ]];then
        v_sql=" update etl_drop_partition_config set status='0' 
        where   status='1'
        and lower(db_user)=lower('${db_user}')
        and lower(table_name)=lower('${table_name}');" 
        mysql $mysqlConn -s    -N -e "$v_sql"
    else
        echo "config exists~ " >&2
        echo "$is_config"
        return 0
        
    fi
fi


#默认参数
# 获取config_id,序列
# " select nextval('seq_common_id') "
get_config_id="select nextval('seq_common_id');"
config_id=$(mysql $mysqlConn -s    -N -e "$get_config_id")
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


#/************************************************************************************
# 功能描述: 数据核查函数
# 使用示例: check_data "" 1000001
# 使用示例2: 可以和获取配置id同时使用  check_data "" $(get_add_ckconfig_auto)
# 输入参数: $1-分组id,$2-配置id
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#


function check_data()
{

local group_id=$1
local config_id=$2
eval $(echo $v_thisyyyymmdd |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')

# 先测试
#local mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "


if [[ "Z$config_id" != "Z" ]];then
local config_id=$(echo $config_id |sed "s/,/','/g")
local config_id_sql=" and id in ('$config_id') "
fi

if [[ "Z$group_id" != "Z" ]];then
local group_id=$(echo $group_id |sed "s/,/','/g")
local group_id_sql=" and group_id in ('$group_id') "
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
echo $config_results >&2
IFS_OLD=$IFS  #將当前值保存
IFS=$'\n'

for config_result in $config_results ;do
#echo $config_result
local cluster_type=$(echo "$config_result" |awk -F "|" '{print $1}')
local db_user=$(echo "$config_result" |awk -F "|" '{print $2}')
local table_name=$(echo "$config_result" |awk -F "|" '{print $3}')
local check_col=$(echo "$config_result" |awk -F "|" '{print $5}')
local check_type=$(echo "$config_result" |awk -F "|" '{print $4}' |sed "s/)/ $check_col )/")  # count(),sum(),count(distinct),sum(distinct )
local group_col=$(echo "$config_result" |awk -F "|" '{print $6}')
local par_cond=$(echo "$config_result" |awk -F "|" '{print $7}' | sed "s/YYYY/$v_year/;s/MM/$v_month/;s/DD/$v_day/;s/yyyy/$v_year/;s/mm/$v_month/;s/dd/$v_day/")   # 简单处理,只有一个分区
local result_refer=$(echo "$config_result" |awk -F "|" '{print $8}')
local check_id=$(echo "$config_result" |awk -F "|" '{print $9}')
local check_desc=$(echo "$config_result" |awk -F "|" '{print $10}')
local config_id=$(echo "$config_result" |awk -F "|" '{print $11}')
if [[ "$cluster_type" = "emr" ]];then
# 新旧集群配置
#beeline="beeline -u 'jdbc:hive2://10.0.7.205:10001/' --showHeader=false --outputformat=tsv2  "
beeline="hive "
elif [[ "$cluster_type" = "cdh" ]];then
#beeline='beeline -u jdbc:hive2://10.0.3.18:10000/ -n data_engineering -w /opt/workpass/data_engineering.pass'
beeline='hive '
fi


check_begin_date=$(date +"%Y-%m-%d %H:%M:%S")
delete_sql="delete from etl_check_result where check_date='$v_thisyyyymmdd' and config_id='$config_id' ;"
check_log_sql_file=/tmp/check_log_${config_id}_${v_thisyyyymmdd}_$$.sql
echo "$delete_sql" > $check_log_sql_file
null_group_sql="insert into etl_check_result values (null,'$config_id','$check_id','$check_desc','table_name','$check_col','$group_col','-','0','$v_thisyyyymmdd','$check_begin_date','');"

# 生成核查sql
if [[ "Z$group_col" != "Z" ]];then

# 需加上null的情况         
v_sql="select concat_ws(',','insert into etl_check_result values (null','\'$config_id\'','\'$check_id\'','\'$check_desc\''
,'\'$db_user\'','\'$table_name\'','\'$check_col\'','\'$group_col\'',concat('\'',cast( COALESCE($group_col ,'null')as string),'\''),concat('\'',cast($check_type as string),'\'')
,'0','\'$v_thisyyyymmdd\'','\'$check_begin_date\'',concat('\'$check_time\'',')\;'))  
from \`${db_user}.${table_name}\`
where 1=1
$par_cond
group by $group_col ;
"
else
v_sql="select concat_ws(',','insert into etl_check_result values (null','\'$config_id\'','\'$check_id\'','\'$check_desc\''
,'\'$db_user\'','\'$table_name\'','\'$check_col\'','\'$group_col\'','\'-\'',concat('\'',cast( $check_type as string),'\''),'0','\'$v_thisyyyymmdd\'','\'$check_begin_date\'',concat('\'$check_time\'',')\;'))  
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
mysql  -s  $mysqlConn  -N < "$check_log_sql_file"

rm "$check_log_sql_file"

done
IFS=$IFS_OLD  #恢復之前保存的值

}


#/************************************************************************************
# 功能描述: 获取/增加数据核查配置
# 使用示例: check_data "" 1000001
# 使用示例2: 可以和数据核查函数同时使用  
# check_data "" $(get_add_ckconfig_auto -t "count()" -c "cc_call_num" -g "" -p "and mt=\'yyyy-mm\'"  -i "" -n ""  -a "zoukehui" -D"dw"  -T"dw_sale_fact_cc_call_num" )
# 输入参数: -f 更新一条配置(勿轻易使用) ,详细变量配置见函数内使用说明
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#

function get_add_ckconfig_auto(){

while getopts "t:c:g:p:a:D:T:G:i:n:fh" arg #选项后面的冒号表示该选项需要参数
do
    case $arg in
         f)
            local is_force='1'
            ;;
         t)
            local check_type="$OPTARG"
            ;;
         c) 
            local check_col="$OPTARG"
            ;;
         g)
            local group_col="$OPTARG"
            ;;
         p)
            local par_cond="$OPTARG"
            ;;
         a)
            local create_user="$OPTARG"
            ;;
         i)
            local check_id="$OPTARG"
            ;;
         n)
            local check_desc="$OPTARG"
            ;;
         G)
            local group_id="$OPTARG"
            ;;
         D)
            local db_user="$OPTARG"
            ;;
         T)
            local table_name="$OPTARG"
            ;;
         ?)  #当有不认识的选项的时候arg为?
        echo "unkonw argument"
    exit 1
    ;;
    esac
done
#重置
OPTIND=
#赋值/默认
: ${check_type:="count()"}
: ${check_col:="0"}
: ${group_col:=""}
: ${par_cond:="and partition_id=\'yyyy-mm-dd\'"}
: ${create_user:="admin"}
: ${group_id:="default"}
: ${db_user:=$v_user}
: ${table_name:=$v_prc}
: ${check_id:=""}
: ${check_desc:=""}
: ${cluster_type:="emr"}




#必要参数
# check_id=                 # 核查关联id,可不配置
# check_desc=               # 核查内容说明,可不配置
# cluster_type=             # 集群名称-新集群emr,旧集群cdh
# group_id=                 # 分组id,不同的组之间是并行执行的
# db_user=                  # 数据库用户名
# table_name=               # 表名
# table_remark=             # 表中文描述
# check_type=               # 核查类型
# check_col=                # 核查字段
# group_col=                # group by 字段
# par_cond=                 # 分区条件
# create_user=              # 创建人



#mysqlConn=" -urd_user -pNTHXDF7czYwi -P3306 -h172.16.70.20 -Dengineering "
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

#检查是否已添加
#同一张表,同一个集群,同一个核查类型,同一个字段,同一个group字段,同一个分区条件 只有一条有效记录
v_sql=" select id from etl_check_config 
where 1=1
and status='1'
and lower(cluster_type)=lower('${cluster_type}')
and lower(db_user)=lower('${db_user}')
and lower(table_name)=lower('${table_name}')
and lower(check_type)=lower('${check_type}')
and lower(check_col)=lower('${check_col}')
and lower(group_col)=lower('${group_col}')
and lower(par_cond)=lower('${par_cond}');" 
echo "$v_sql" >&2 
is_config=$(mysql $mysqlConn -s    -N -e "$v_sql")

# 需增加可以修改的参数
if [[ "Z$is_config" != "Z" ]];then
    if  [[ "Z$is_force" = "Z1" ]];then
        v_sql=" update etl_check_config set status='0' 
        where 1=1
        and status='1'
        and lower(cluster_type)=lower('${cluster_type}')
        and lower(db_user)=lower('${db_user}')
        and lower(table_name)=lower('${table_name}')
        and lower(check_type)=lower('${check_type}')
        and lower(check_col)=lower('${check_col}')
        and lower(group_col)=lower('${group_col}')
        and lower(par_cond)=lower('${par_cond}');" 
        mysql $mysqlConn -s    -N -e "$v_sql"
    else
        echo "config exists~ " >&2
        echo "$is_config"
        return 0
        
    fi
fi


#默认参数
# 获取config_id,序列
# " select nextval('seq_common_id') "
get_config_id="select nextval('seq_common_id');"
config_id=$(mysql $mysqlConn -s    -N -e "$get_config_id")
status='1'
create_date=$(date +"%Y-%m-%d %H:%M:%S")

result_refer=             # 结果参考值,预留
correct_range=            # 正常值范围,预留
cycle_type=               # 核查周期,预留
cycle_value=              # 执行日期,预留

v_sql=" insert into  
etl_check_config(
    id
    ,group_id
    ,check_id
    ,check_desc
    ,cluster_type
    ,db_user
    ,table_name
    ,table_remark
    ,check_type
    ,check_col
    ,group_col
    ,par_cond
    ,status
    ,create_user) 
values('${config_id}'
        ,'${group_id}'
        ,'${check_id}'
        ,'${check_desc}'
        ,'${cluster_type}'
        ,'${db_user}'
        ,'${table_name}'
        ,'${table_remark}'
        ,'${check_type}'
        ,'${check_col}'
        ,'${group_col}'
        ,'${par_cond}'
        ,'${status}'
        ,'${create_user}'
        );"
        mysql $mysqlConn -s    -N -e "$v_sql"
        echo "$config_id"
        return 0
}

etlMongoExportCSVLog()
{
  start_time=$(date +"%Y-%m-%d %H:%M:%S")

  echo ${v_mongo_export_csv_cmd}

  eval ${v_mongo_export_csv_cmd}

  if [ $? -eq 0 ]; then
    result_code="OK"
  else
    result_code="ERROR"
  fi

  end_time=$(date +"%Y-%m-%d %H:%M:%S")
  echo $start_time
  v_mongo_export_csv_cmd=${v_mongo_export_csv_cmd//"\`"/"\\\`"}
  v_mongo_export_csv_cmd=${v_mongo_export_csv_cmd//"\""/"\\\""}
  v_sql_log=`echo "v_mongo_export_csv_cmd" | awk -v v="'" -v r="''" '{gsub(v,r)}1'`
  echo $end_time
  echo $result_code

  sqoop_eval="sqoop eval --connect 'jdbc:mysql://10.0.2.63:5318/engineering?characterEncoding=utf8' --username data_engineering --password-file '/user/hive/.passdir/engineering.pass' --query \"insert into etl_hive_log (deal_date,db_user,prc_name,job_step,sql_str,start_time,end_time,result_code) values ('$v_thisyyyymmdd',UPPER('$v_user'),UPPER('$v_prc'),'$v_step','$v_sql_log','$start_time','$end_time','$result_code')\""
  eval ${sqoop_eval}

  if [ "$result_code" = "ERROR" ]; then
   echo $result_code
   exit 1
  fi

}


#/************************************************************************************
# 功能描述: s表生成
# 使用示例: 
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#


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


#/************************************************************************************
# 功能描述: 合并表生成
# 使用示例: 
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#
function complexTableGenerator(){

while getopts "c:t:S:G:g:h" arg #选项后面的冒号表示该选项需要参数
do
    case $arg in
         c)
             complex_type="$OPTARG"
            ;;
         t)
             table_id="$OPTARG"
            if [[ "Z$table_id" = "Z" ]];then
                echo "必须指定目标表!!!" >&2
                return
            fi
            ;;
         S)
             complex_use_sources="$OPTARG"
            ;;
         G)
             complex_use_groups="$OPTARG"
            ;;
         g) 
             complex_groups="$OPTARG"
            ;;
         h)
            echo "-t :指定合并目标t表编号
                  -g :指定合并目标g表编号
                  -c :指定执行步骤
                  -S :指定使用的s表, 勿随意使用,-g和-S 同时存在时-S生效
                  -G :指定使用的g表, 勿随意使用,-t和-S 同时存在时-G生效 " >&2
            exit 
            ;;

         ?)  #当有不认识的选项的时候arg为?
            echo "-t :指定合并目标t表编号
                  -g :指定合并目标g表编号
                  -c :指定执行步骤
                  -S :指定使用的s表
                  -G :指定使用的g表" >&2
            exit 1
            ;;
    esac
done
#重置
OPTIND=
# 生成g表流程
if [[ "Z$complex_type" = "Za" ]] || [[ "Z$complex_type" = "Z" ]] || [[ "Z$complex_type" = "Zg" ]] ;then
    if [[ "Z$complex_groups" = "Z" ]];then
    v_sql="
        SELECT distinct group_id       
        from etl_index_source
        where 1=1
        and status='1'
        and table_id='$table_id';
    "
        complex_groups=$(mysql $mysqlConn -s   -N -e "$v_sql")
    fi

    for complex_group in $(echo $complex_groups |sed "s/,/ /g");do
            #查询出所有s表合并
            v_sql="
            SELECT concat_ws('|'
                ,COALESCE(source_id,'')
                ,COALESCE(source_type,'')
                ,COALESCE(group_id,'')
                ,COALESCE(db_user,'')
                ,COALESCE(table_name,'')
                ,COALESCE(join_key,'')
                )
                from etl_index_source
                where 1=1
                and status='1'
                and group_id='$complex_group';
            "

            source_results=$(mysql $mysqlConn -s   -N -e "$v_sql")
            IFS_OLD=$IFS  #將当前值保存
            IFS=$'\n'

            group_source_ids=
            for source_result in $source_results ;do
                source_id=$(echo "$source_result" |awk -F "|" '{print $1}')
                group_id=$(echo "$source_result" |awk -F "|" '{print $3}')
                db_user=$(echo "$source_result" |awk -F "|" '{print $4}')
                table_name=$(echo "$source_result" |awk -F "|" '{print $5}')
                join_key=$(echo "$source_result" |awk -F "|" '{print $6}')
                group_source_ids=${group_source_ids}","$db_user.${table_name}_source_$source_id
            done
            IFS=$IFS_OLD  #恢復之前保存的值

            group_source_ids=${group_source_ids#,}
            group_table_name="$db_user.${table_name}_group_$group_id"

            gatherTables "$group_table_name"  "$group_source_ids"  "$join_key"  &

        done
    

    if [[ "Z$complex_use_sources" != "Z" ]];then
        echo "后续开发..."
    fi


    wait
fi


# 生成all表流程
if [[ "Z$complex_type" = "Za" ]] || [[ "Z$complex_type" = "Z" ]] || [[ "Z$complex_type" = "Zt" ]] ;then
            v_sql="
            SELECT distinct concat_ws('|'
,COALESCE(group_id,'')
,COALESCE(db_user,'')
,COALESCE(table_id,'')
,COALESCE(table_name,'')
,COALESCE(join_key,'')
)
from etl_index_source
where 1=1
and status='1'
and table_id='$table_id';
            "
            group_results=$(mysql $mysqlConn -s   -N -e "$v_sql")
            IFS_OLD=$IFS  #將当前值保存
            IFS=$'\n'

            table_group_ids=
            for group_result in $group_results ;do
            group_id=$(echo "$group_result" |awk -F "|" '{print $1}')
            db_user=$(echo "$group_result" |awk -F "|" '{print $2}')
            table_id=$(echo "$group_result" |awk -F "|" '{print $3}')
            table_name=$(echo "$group_result" |awk -F "|" '{print $4}')
            join_key=$(echo "$group_result" |awk -F "|" '{print $5}')
            table_group_ids="${table_group_ids}","$db_user.${table_name}_group_$group_id"

            done
            IFS=$IFS_OLD  #恢復之前保存的值

            table_group_ids=${table_group_ids#,}
            target_table_name="$db_user.${table_name}_t${table_id}"

            gatherTables "$target_table_name"  "$table_group_ids"  "$join_key"  

fi
}

#/************************************************************************************
# 功能描述: g表生成
# 使用示例: 
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#

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
etlHiveLog_stable


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
etlHiveLog_stable

}

#/************************************************************************************
# 功能描述: 统一视图建表
# 使用示例: 
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#

function targetTableCreate()
{
table_id=$1
mysqlConn=" --defaults-file=/opt/workpass/mysql_data_engineering.conf "

v_sql="
select * from (
SELECT  concat(case when order_num='1' then 'deal_date string ,' else ',' end ,index_name,'   ',index_data_type) 
from etl_index_target
where 1=1
and status='1'
and table_id='${table_id}'
order by(order_num+0)
) b ;
"
create_table_sql=$(mysql $mysqlConn -s   -N -e "$v_sql")

v_step="1"
v_sql="
create table if not exists $v_user.$v_prc (
$create_table_sql
)partitioned by(partition_id string) stored as parquet
"

etlSparkSQLLog

}


#/************************************************************************************
# 功能描述: 统一视图数据插入
# 使用示例: 
# 函数使用日期: v_thisyyyymmdd
# 输出结果: 日期
# 创建人员: zoukehui
# 创建日期: 2020-11-28 11:53:35
#/************************************************************************************
#

function targetTableInsert()
{

table_id=$1
v_step='2'
v_sql="
select insert_sql from (
select * from (
select concat(' insert overwrite table  ',db_user,'.',table_name,' partition(partition_id=\'$v_thisyyyymmdd\')',' select \'$v_thisyyyymmdd\',' )  insert_sql ,0 as order_num
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
						
				else 	 concat(', ',index_name) end insert_sql				,order_num
from etl_index_target
where 1=1
and status='1'
and table_id='${table_id}'
order by(order_num+0)
) b
union
select * from (
select concat(' from  ',db_user,'.',table_name, '_t',table_id,' ;') insert_sql  ,99999999999 as order_num
from etl_index_target where 1=1 and status='1' and table_id='${table_id}' limit 1
) c 
) d
order by(order_num+0)
;
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