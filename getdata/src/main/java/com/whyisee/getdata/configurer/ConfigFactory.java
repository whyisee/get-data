package com.whyisee.getdata.configurer;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/20 17:23
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
public class ConfigFactory {
    public static String SEQ_COMMON_ID="SELECT NEXTVAL('seq_common_id');";
    public static String DEL_GETDATA_TASK="update  tc_gd_configmain set status='0' where task_id = ";
}
