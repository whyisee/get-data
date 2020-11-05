package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.annotation.CurrentUser;
import com.whyisee.getdata.configurer.ConfigFactory;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.dao.ManageSqlTools;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdConfigflowService;
import com.whyisee.getdata.service.TcGdConfigmainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whyisee.utils.DateUtils;
import com.whyisee.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by zoukh on 2020/10/25.
*/
@RestController
@RequestMapping("/configmain")
public class TcGdConfigmainController {

    @Resource
    private ManageSqlTools manageSqlTools;
    @Resource
    private TcGdConfigmainService tcGdConfigmainService;
    @Resource
    private TcGdConfigflowService tcGdConfigflowService;



    private  String mainSourceKey = "";

    @PostMapping    public Result add(@RequestBody  Map<String,Object> params ,@CurrentUser TcAuthUser currentUser) {
        /**
         * add is  创建取数任务
         * 增加业务逻辑,流程开始魔幻起来
         * @author zoukh
         * Created in:  2020/11/2 10:09
         * @version 1.0
         * @Modified By:
         * @used in: TcGdConfigmainController
         */
        TcGdConfigmain tcGdConfigmain = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdConfigmain.class);

        //需增加获取多个序列id方法 SELECT NEXTVAL('seq_common_id') from tc_gd_configflow limit 5;
        String taskId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        String sourceFlowId= saveFlow(taskId,"dataSource",params ,"");
        String troopFlowId=saveFlow(taskId,"userTroop",params ,"");
        String condFlowId=saveFlow(taskId,"condTag",params ,"");
        String showFlowId=saveFlow(taskId,"showTag",params ,"");
        String execFlowId=saveFlow(taskId,"execFlow",params ,"");
        String dataFlowId=saveFlow(taskId,"dataFlow",params ,"");


        tcGdConfigmain.setSourceFlowId(sourceFlowId);
        tcGdConfigmain.setTroopFlowId(troopFlowId);
        tcGdConfigmain.setCondFlowId(condFlowId);
        tcGdConfigmain.setShowFlowId(showFlowId);
        tcGdConfigmain.setExecFlowId(execFlowId);
        tcGdConfigmain.setDataFlowId(dataFlowId);
        tcGdConfigmain.setTaskId(taskId);
        tcGdConfigmain.setCreatePersion(currentUser.getLoginName());
        tcGdConfigmain.setCreateDate(DateUtils.getDateTimeFormat(new Date()));
        tcGdConfigmainService.save(tcGdConfigmain);
        //rs=
        return ResultGenerator.genSuccessResult(tcGdConfigmain);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        // tcGdConfigmainService.deleteById(id);
        // 修改状态删除
        // 后期添加:权限验证, 删除状态验证
        //
        manageSqlTools.safeDelete(ConfigFactory.DEL_GETDATA_TASK+id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody  Map<String,Object> params ,@CurrentUser TcAuthUser currentUser) {
        /**
         * update is  修改操作
         *
         * @author zoukh
         * Created in:  2020/11/2 15:06
         * @version 1.0
         * @Modified By:
         * @used in: TcGdConfigmainController
         */
        TcGdConfigmain tcGdConfigmain = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdConfigmain.class);
        System.out.println("===test===>"+params);

        saveFlow(tcGdConfigmain.getTaskId(),"dataSource",params,tcGdConfigmain.getSourceFlowId());
        saveFlow(tcGdConfigmain.getTaskId(),"userTroop",params ,tcGdConfigmain.getTroopFlowId());
        saveFlow(tcGdConfigmain.getTaskId(),"condTag",params ,tcGdConfigmain.getCondFlowId());
        saveFlow(tcGdConfigmain.getTaskId(),"showTag",params ,tcGdConfigmain.getShowFlowId());
        saveFlow(tcGdConfigmain.getTaskId(),"execFlow",params ,tcGdConfigmain.getExecFlowId());
        saveFlow(tcGdConfigmain.getTaskId(),"dataFlow",params ,tcGdConfigmain.getDataFlowId());



        tcGdConfigmainService.update(tcGdConfigmain);
        return ResultGenerator.genSuccessResult(tcGdConfigmain);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdConfigmain tcGdConfigmain = tcGdConfigmainService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdConfigmain);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdConfigmain> list = tcGdConfigmainService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @RequestMapping(value="/search",method = {RequestMethod.GET})
    public @ResponseBody
    Result searchForGet(@RequestBody TcGdConfigmain tcGdConfigmain) throws Exception {
        PageHelper.startPage(0, 0);
        List<TcGdConfigmain> list = tcGdConfigmainService.search(tcGdConfigmain);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @PostMapping("/search")
    public Result search(@RequestBody  Map<String,Object> params) {
        TcGdConfigmain tcGdConfigmain = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdConfigmain.class);
        PageHelper.startPage((int)params.getOrDefault("page",1), (int)params.getOrDefault("limit",20));
        List<TcGdConfigmain> list = tcGdConfigmainService.search(tcGdConfigmain);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    private String saveFlow(String taskId,String flowType,Map<String,Object> params,String flowId){
        /**
         * saveFlow is  保存流程配置方法,使代码简洁
         *
         * @author zoukh
         * Created in:  2020/11/2 10:25
         * @version 1.0
         * @Modified By:
         * @used in: TcGdConfigmainController
         */
        TcGdConfigflow tcGdConfigflow = new TcGdConfigflow();

        tcGdConfigflow.setParentFlowId(taskId);
        tcGdConfigflow.setStatus("1");
        tcGdConfigflow.setCreatePersion("admin");
        tcGdConfigflow.setCreateDate(DateUtils.getDateTimeFormat(new Date()));

        StringBuffer flowValue1 = new StringBuffer();
        StringBuffer flowValue2 = new StringBuffer();
        StringBuffer flowValue3 = new StringBuffer();
        StringBuffer flowValue4 = new StringBuffer(); //每步的sql
        StringBuffer flowValue5 = new StringBuffer(); //原始json--先暂时使用保存时候解析成sql,后期可以单独开发解析程序
        String mainSourceKey=getMainSourceKey();
        JSONObject jsonObject =JSONUtil.getJSONFromString((JSONObject.toJSONString(params)));

        switch (flowType){
            case "dataSource" :
                JSONArray dataSourcesSelect = jsonObject.getJSONArray("dataSourcesSelect");
                flowValue5.append(" { \"dataSourcesSelect\": "+ dataSourcesSelect+"}");

                for (int i = 0; i < dataSourcesSelect.size(); i++) {
                    if(i == 0){

                        flowValue4=flowValue4.append(" from "+JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceName")
                                + " TS" +JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId"));
                        mainSourceKey=" TS" +JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
                                + "." + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceKey");
                        setMainSourceKey(mainSourceKey);
                    }else {
                        flowValue4 = flowValue4.append("\n left outer join  " + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceName")
                                + " TS" + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
                                + " on " + mainSourceKey + " = " + " TS" + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
                                + "." + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceKey"));
                    }
                }

                tcGdConfigflow.setFlowName("数据源配置");
                tcGdConfigflow.setFlowType("getdata");
                tcGdConfigflow.setFlowKey("dataSourceConfig");
                tcGdConfigflow.setFlowSort("1001");
                break;
            case "userTroop" :
                JSONArray userTroopsSelect = jsonObject.getJSONArray("userTroopsSelect");
                JSONArray userTroopsDel = jsonObject.getJSONArray("userTroopsDel");
                flowValue5.append("{ \"userTroopsSelect\": "+ userTroopsSelect);
                flowValue5.append(" ,\"userTroopsSelectCP\": \""+params.get("userTroopsSelectCP")+"\"");
                flowValue5.append(" ,\"userTroopsDelCP\": \""+params.get("userTroopsDelCP")+"\" ");
                flowValue5.append(" ,\"userTroopsDel\": "+ userTroopsDel+"}");

                StringBuffer tempSql1 = new StringBuffer("");
                StringBuffer tempSql2 = new StringBuffer("");


                for (int i = 0; i < userTroopsSelect.size(); i++) {
                    if ("".equals(mainSourceKey)&& i == 0) {
                        tempSql1.append("\n from " + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopName")
                                    + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey"));
                    }else {
                            tempSql1.append("\n left outer join " + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopName")
                                    + " TU" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey"));
                    }
                    // 交集
                    if("N".equals(params.get("userTroopsSelectCP"))&& i == 0){
                        tempSql2.append("\n and  (" + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") +" != null ");
                    }else if("N".equals(params.get("userTroopsSelectCP"))&& i != 0){
                        tempSql2.append(" and " + (" T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + " != null "));
                    }

                    // 并集
                    if("U".equals(params.get("userTroopsSelectCP"))&& i == 0){
                        tempSql2.append("\n and  (" + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + " != null ");
                    }else if("U".equals(params.get("userTroopsSelectCP"))&& i != 0){
                        tempSql2.append(" or " + (" T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + " != null "));
                    }

                }
                if (tempSql2.length()!=0 ) {
                    tempSql2.append(" ) ");
                }


                    for (int i = 0; i < userTroopsDel.size(); i++) {
                    if ("".equals(mainSourceKey)&& i == 0 &&tempSql2.length()==0 ) {
                        tempSql1.append("\n from " + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopName")
                                + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey"));
                    }else {
                        tempSql1.append("\n left outer join " + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopName")
                                + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey"));
                    }
                    // 交集
                    if("N".equals(params.get("userTroopsSelectCP"))&& i == 0){
                        tempSql2.append(" and  not (" + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") +" != null ");
                    }else if("N".equals(params.get("userTroopsSelectCP"))&& i != 0){
                        tempSql2.append(" and " + (" T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + " != null "));
                    }

                    // 并集
                    if("U".equals(params.get("userTroopsDelCP"))&& i == 0){
                        tempSql2.append(" \n and  not (" + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + " != null ");
                    }else if("U".equals(params.get("userTroopsDelCP"))&& i != 0){
                        tempSql2.append(" or " + (" T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + " != null "));
                    }
                }

                if (tempSql1.length()!=0 ){
                    flowValue4.append(tempSql1+" \n where 1=1  "+tempSql2 + ")");
                }
                tcGdConfigflow.setFlowName("用户群配置");
                tcGdConfigflow.setFlowKey("userTroopConfig");
                tcGdConfigflow.setFlowSort("1002");
                break;

            case "condTag":
                JSONArray condTagsSelect = jsonObject.getJSONArray("condTagsSelect");
                JSONArray condTagsDel = jsonObject.getJSONArray("condTagsDel");
                flowValue5.append("{ \"condTagsSelect\": "+ condTagsSelect);
                flowValue5.append(" ,\"condTagsDel\": "+ condTagsDel+"}");


                for (int i = 0; i < condTagsSelect.size(); i++) {
                    flowValue4.append(JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("unionType")
                         + " "   + JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("leftBracket")
                            + ""
                    );
                }

                tcGdConfigflow.setFlowName("数据源条件配置");
                tcGdConfigflow.setFlowKey("condTagConfig");
                tcGdConfigflow.setFlowSort("1003");
                break;
            case"showTag":
                JSONArray dataSourceTag = jsonObject.getJSONArray("dataSourceTag");
                JSONArray userTroopTag = jsonObject.getJSONArray("userTroopTag");
                JSONArray userOtherTag = jsonObject.getJSONArray("userOtherTag");
                flowValue5.append("{ \"dataSourceTag\": "+ dataSourceTag);
                flowValue5.append(" ,\"userTroopTag\": "+ userTroopTag);
                flowValue5.append(" ,\"userOtherTag\": "+ userOtherTag+"}");

                //sql生成
                 tempSql1 = new StringBuffer(" select ");
                 tempSql2 = new StringBuffer(" select ");

                for (int i = 0; i < dataSourceTag.size(); i++) {
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" T"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagFromId")+"."
                            +JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagName")+" ,");
                }
                for (int i = 0; i < userTroopTag.size(); i++) {
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" T"+JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagFromId")+"."
                            +JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagName")+" ,");
                }
                for (int i = 0; i < userOtherTag.size(); i++) {
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(userOtherTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" '"+JSONUtil.getJSONFromString(userOtherTag.get(i).toString()).get("tagValue")+"' ,");
                }


                tempSql1.deleteCharAt(tempSql1.length() - 1);
                tempSql2.deleteCharAt(tempSql2.length() - 1);

                flowValue4=tempSql1.append(" from dual \n union all \n "+tempSql2);


                tcGdConfigflow.setFlowName("展示指标配置");
                tcGdConfigflow.setFlowKey("showTagConfig");
                tcGdConfigflow.setFlowSort("1004");
                break;
            case "execFlow":
                flowValue5.append("{ \"execConfig\": "+ jsonObject.get("execConfig")+"}");
                tcGdConfigflow.setFlowName("执行配置");
                tcGdConfigflow.setFlowKey("execFlowConfig");
                tcGdConfigflow.setFlowSort("1005");
                break;

            case "dataFlow":
                flowValue5.append("{ \"dataConfig\": "+ jsonObject.get("dataConfig")+"}");
                tcGdConfigflow.setFlowName("结果配置");
                tcGdConfigflow.setFlowKey("dataFlowConfig");
                tcGdConfigflow.setFlowSort("1006");
                break;
            default :

        }

        tcGdConfigflow.setFlowType("getdata");
        tcGdConfigflow.setFlowValue1(flowValue1.toString());
        tcGdConfigflow.setFlowValue2(flowValue2.toString());
        tcGdConfigflow.setFlowValue3(flowValue3.toString());
        tcGdConfigflow.setFlowValue4(flowValue4.toString());
        tcGdConfigflow.setFlowValue5(flowValue5.toString());

        // 新增/更新
        if(null==flowId||"".equals(flowId)) {
            flowId = manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
            tcGdConfigflow.setFlowId(flowId);
            tcGdConfigflowService.save(tcGdConfigflow);
        }else {
            tcGdConfigflow.setFlowId(flowId);
            tcGdConfigflowService.update(tcGdConfigflow);

        }

        return flowId;
    }

    private String getExecSql(){


        return null;
    }

    public String getMainSourceKey() {
        return mainSourceKey;
    }

    public void setMainSourceKey(String mainSourceKey) {
        this.mainSourceKey = mainSourceKey;
    }
}
