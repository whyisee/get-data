#!/usr/bin/env bash
#commonpath=$(cd "$(dirname "$0")";pwd)/
commonpath=$(dirname $(cd "$(dirname "$0")";pwd))/common/
. ${commonpath}functions.sh $1


#/************************************************************************************
# 程序名称:    dm_etl_index_complex.sh <必填>
# 功能描述:    宽表合并s表 <必填>
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
v_prc="dm_etl_index_complex"        #程序名称
v_queue='data_engineering'                  #hive队列
result_code=""                              #程序返回值
check_begin_date=$(date "+%Y-%m-%d %H:%M:%S")
eval $(echo $v_deal_date |awk -F "-" '{print "v_year="$1";v_month="$2";v_day="$3}')

# 合并类型参数
# 合并指定s表
# 合并指定g表

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


# 生成t表流程
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

complexTableGenerator  -c a -t $2 