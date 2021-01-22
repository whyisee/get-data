package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.annotation.CurrentUser;
import com.whyisee.getdata.configurer.ConfigFactory;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.dao.ManageSqlTools;
import com.whyisee.getdata.model.*;
import com.whyisee.getdata.service.TcGdConfigflowService;
import com.whyisee.getdata.service.TcGdConfigmainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whyisee.getdata.service.TcGdTagconfigFlowService;
import com.whyisee.getdata.service.TcGdUsertroopService;
import com.whyisee.utils.DateUtils;
import com.whyisee.utils.JSONUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Resource
    private TcGdTagconfigFlowService tcGdTagconfigFlowService;

    @Resource
    private TcGdUsertroopService tcGdUsertroopService;

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
        String sourceFlowId= saveFlow(taskId,"dataSource",params ,"",currentUser);
        String troopFlowId=saveFlow(taskId,"userTroop",params ,"",currentUser);
        String condFlowId=saveFlow(taskId,"condTag",params ,"",currentUser);
        String showFlowId=saveFlow(taskId,"showTag",params ,"",currentUser);
        String execFlowId=saveFlow(taskId,"execFlow",params ,"",currentUser);
        String dataFlowId=saveFlow(taskId,"dataFlow",params ,"",currentUser);
        String execSql="";

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
        if(null==tcGdConfigmain.getExecSql()||"".equals(tcGdConfigmain.getExecSql())) {
            execSql = getExecSql(taskId);
        }
        if(!"".equals(execSql)){
            tcGdConfigmain.setExecSql(execSql);
        }
        tcGdConfigmainService.update(tcGdConfigmain);
        tcGdConfigmain.setExecSql("");
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

        saveFlow(tcGdConfigmain.getTaskId(),"dataSource",params,tcGdConfigmain.getSourceFlowId(),currentUser);
        saveFlow(tcGdConfigmain.getTaskId(),"userTroop",params ,tcGdConfigmain.getTroopFlowId(),currentUser);
        saveFlow(tcGdConfigmain.getTaskId(),"condTag",params ,tcGdConfigmain.getCondFlowId(),currentUser);
        saveFlow(tcGdConfigmain.getTaskId(),"showTag",params ,tcGdConfigmain.getShowFlowId(),currentUser);
        saveFlow(tcGdConfigmain.getTaskId(),"execFlow",params ,tcGdConfigmain.getExecFlowId(),currentUser);
        saveFlow(tcGdConfigmain.getTaskId(),"dataFlow",params ,tcGdConfigmain.getDataFlowId(),currentUser);
        String execSql="";

        tcGdConfigmainService.update(tcGdConfigmain);

        if(null==tcGdConfigmain.getExecSql()||"".equals(tcGdConfigmain.getExecSql())) {
            execSql = getExecSql(tcGdConfigmain.getTaskId());
        }
        if(!"".equals(execSql)){
            tcGdConfigmain.setExecSql(execSql);
        }
        tcGdConfigmainService.update(tcGdConfigmain);
        tcGdConfigmain.setExecSql("");

        return ResultGenerator.genSuccessResult(tcGdConfigmain);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdConfigmain tcGdConfigmain = tcGdConfigmainService.findById(id);
        tcGdConfigmain.setExecSql("");

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


    private String saveFlow(String taskId,String flowType,Map<String,Object> params,String flowId,TcAuthUser currentUser)  {
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
        tcGdConfigflow.setCreatePersion(currentUser.getLoginName());
        tcGdConfigflow.setCreateDate(DateUtils.getDateTimeFormat(new Date()));


        StringBuffer flowValue1 = new StringBuffer();
        StringBuffer flowValue2 = new StringBuffer();
        StringBuffer flowValue3 = new StringBuffer();
        StringBuffer flowValue4 = new StringBuffer(); //每步的sql
        StringBuffer flowValue5 = new StringBuffer(); //原始json--先暂时使用保存时候解析成sql,后期可以单独开发解析程序
        String mainSourceKey=getMainSourceKey();
        JSONObject jsonObject =JSONUtil.getJSONFromString((JSONObject.toJSONString(params)));
        String createPerson = currentUser.getLoginName();

        switch (flowType){
            case "dataSource" :
                JSONArray dataSourcesSelect = jsonObject.getJSONArray("dataSourcesSelect");
                flowValue5.append(" { \"dataSourcesSelect\": "+ dataSourcesSelect+"}");

                for (int i = 0; i < dataSourcesSelect.size(); i++) {
                    if(i == 0){

                        flowValue4=flowValue4.append("\n from "+JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceName")
                                + " T" +JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId"));
                        mainSourceKey=" T" +JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
                                + "." + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceKey");
                        setMainSourceKey(mainSourceKey);
                    }else {
                        flowValue4 = flowValue4.append("\n left outer join  " + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceName")
                                + " T" + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
                                + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId")
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
                jsonObject.getJSONObject("result");


                for (int i = 0; i < userTroopsSelect.size(); i++) {
                    if ("".equals(mainSourceKey)&& i == 0) {
                        tempSql1.append("\n from " + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopName")
                                    + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey"));
                    }else {
                            tempSql1.append("\n left outer join " + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopName")
                                    + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + " on " + mainSourceKey + " = " + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                    + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey"));
                    }
                    // 交集
                    if("N".equals(params.get("userTroopsSelectCP"))&& i == 0){
                        tempSql2.append("\n and  (" + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") +"  <> '' ");
                    }else if("N".equals(params.get("userTroopsSelectCP"))&& i != 0){
                        tempSql2.append(" and " + (" T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + "  <> '' "));
                    }

                    // 并集
                    if("U".equals(params.get("userTroopsSelectCP"))&& i == 0){
                        tempSql2.append("\n and  (" + " T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + "  <> '' ");
                    }else if("U".equals(params.get("userTroopsSelectCP"))&& i != 0){
                        tempSql2.append(" or " + (" T" + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopKey") + "  <> '' "));
                    }

                }
                if (userTroopsSelect.size()!=0 ) {
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
                    if("N".equals(params.get("userTroopsDelCP"))&& i == 0){
                        tempSql2.append("\n and  not (" + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") +"  <> '' ");
                    }else if("N".equals(params.get("userTroopsDelCP"))&& i != 0){
                        tempSql2.append(" and " + (" T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + "  <> '' "));
                    }

                    // 并集
                    if("U".equals(params.get("userTroopsDelCP"))&& i == 0){
                        tempSql2.append(" \n and  not (" + " T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + " <> '' ");
                    }else if("U".equals(params.get("userTroopsDelCP"))&& i != 0){
                        tempSql2.append(" or " + (" T" + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId")
                                + "." + JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopKey") + "  <> '' "));
                    }
                }
                if (userTroopsSelect.size()!=0 && userTroopsDel.size()>0) {
                    tempSql2.append(" ) ");
                }


                // if (tempSql1.length()!=0 ){
                    flowValue4.append(tempSql1+" \n where 1=1  "+tempSql2 );
               // }
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
                    flowValue4.append("\n" +JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("unionType")
                            + " "   + JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).getOrDefault("leftBracket","")
                            + "T"+JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("tagFromId")
                            + "."+JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("tagName")
                            + " "+JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).get("operateType")
                            + " "+JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).getOrDefault("value","")
                            + " "+JSONUtil.getJSONFromString(condTagsSelect.get(i).toString()).getOrDefault("rightBracket","")
                    );
                }

                // 排除条件,
                for (int i = 0; i < condTagsDel.size(); i++) {
                    flowValue4.append("\n" + (i == 0 ? " and not ( ":JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).get("unionType"))
                            + " "   + JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).getOrDefault("leftBracket","")
                            + "T"+JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).get("tagFromId")
                            + "."+JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).get("tagName")
                            + " "+JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).get("operateType")
                            + " "+JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).getOrDefault("value","")
                            + " "+JSONUtil.getJSONFromString(condTagsDel.get(i).toString()).getOrDefault("rightBracket","")
                    );
                }
                if (condTagsDel.size()!=0 ) {
                    flowValue4.append(" ) ");
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
                 tempSql1 = new StringBuffer("select ");
                 tempSql2 = new StringBuffer("select ");

                //此处增加是否生成客户群处理 dataConfig.saveTypes
                //
                boolean isAddTroop= params.get("taskStatus").equals("1")&& jsonObject.getJSONObject("dataConfig").getString("saveTypes").contains("U");
                if(isAddTroop){
                    // 先删除
                    tcGdUsertroopService.deleteById(taskId);
                    TcGdTagconfigFlow tcGdTagconfigFlow = new TcGdTagconfigFlow();
                    tcGdTagconfigFlow.setTagFromId(taskId);
                    tcGdTagconfigFlowService.deleteAllCon(tcGdTagconfigFlow);
                    // System.out.println(jsonObject.getJSONObject("dataConfig").getString("troopEndDate"));
                    TcGdUsertroop tcGdUsertroop = new TcGdUsertroop();
                    tcGdUsertroop.setTroopId(taskId);
                    //客户群名称规则暂时 user_troop_${troop_id}
                    //先配置基本信息 后续扩展
                    tcGdUsertroop.setTroopName("user_troop_"+taskId);
                    tcGdUsertroop.setTroopNameZh(params.get("taskName").toString());
                    tcGdUsertroop.setTroopFrom("getdata");
                    tcGdUsertroop.setTroopStatus("1");
                    tcGdUsertroop.setTroopKey("T"+taskId+"_"+getMainSourceKey().split("\\.")[1]);
                    tcGdUsertroop.setTroopBeginDate(DateUtils.getDateTimeFormat(new Date()));

                    tcGdUsertroop.setTroopEndDate( jsonObject.getJSONObject("dataConfig").getString("troopEndDate"));
                    //后续修改
                    tcGdUsertroop.setCreatePersion(createPerson);
                    tcGdUsertroop.setCreateDate(DateUtils.getDateTimeFormat(new Date()));
                    tcGdUsertroop.setStatus("1");
                    tcGdUsertroopService.save(tcGdUsertroop);

                }




                for (int i = 0; i < dataSourceTag.size(); i++) {
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" T"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagFromId")+"."
                            +JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagName")+" as "
                            +"T"+taskId+"_"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagName")+" ,");
                    flowValue3.append(JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagNameZh")+" ,");

                    //增加用户群标签
                    if(isAddTroop){
                        TcGdTagconfigFlow tcGdTagconfigFlow = new TcGdTagconfigFlow();
                        tcGdTagconfigFlow.setTagId( manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID));
                        tcGdTagconfigFlow.setTagName("T"+taskId+"_"+JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagName").toString());
                        tcGdTagconfigFlow.setTagNameZh(JSONUtil.getJSONFromString(dataSourceTag.get(i).toString()).get("tagNameZh").toString());
                        tcGdTagconfigFlow.setTagFromId(taskId);
                        tcGdTagconfigFlow.setCreateDate(DateUtils.getDateTimeFormat(new Date()));
                        tcGdTagconfigFlow.setCreatePersion(createPerson);
                        tcGdTagconfigFlow.setStatus("1");
                        tcGdTagconfigFlowService.save(tcGdTagconfigFlow);
                    }


                }
                for (int i = 0; i < userTroopTag.size(); i++) {
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" T"+JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagFromId")+"."
                            +JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagName")+" as "
                            +"T"+taskId+"_" + i + " ,");
                    flowValue3.append(JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagNameZh")+" ,");

                    if(isAddTroop){
                        TcGdTagconfigFlow tcGdTagconfigFlow = new TcGdTagconfigFlow();
                        tcGdTagconfigFlow.setTagId( manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID));
                        tcGdTagconfigFlow.setTagName("T"+taskId+"_" + i );
                        tcGdTagconfigFlow.setTagNameZh(JSONUtil.getJSONFromString(userTroopTag.get(i).toString()).get("tagNameZh").toString());
                        tcGdTagconfigFlow.setTagFromId(taskId);
                        tcGdTagconfigFlow.setCreateDate(DateUtils.getDateTimeFormat(new Date()));
                        tcGdTagconfigFlow.setCreatePersion(createPerson);
                        tcGdTagconfigFlow.setStatus("1");
                        tcGdTagconfigFlowService.save(tcGdTagconfigFlow);
                    }

                }
                for (int i = 0; i < userOtherTag.size(); i++) {
                    // 自定义标签未实现
                    tempSql1=tempSql1.append(" '"+JSONUtil.getJSONFromString(userOtherTag.get(i).toString()).get("tagNameZh")+"' ,");
                    tempSql2=tempSql2.append(" '"+JSONUtil.getJSONFromString(userOtherTag.get(i).toString()).get("tagValue")+"' ,");
                    flowValue3.append(JSONUtil.getJSONFromString(userOtherTag.get(i).toString()).get("tagNameZh")+" ,");

                }


                tempSql1.deleteCharAt(tempSql1.length() - 1);
                tempSql2.deleteCharAt(tempSql2.length() - 1);
                // 字段类型问题,去掉表头
                //flowValue4=tempSql1.append(" from dual \n union all \n "+tempSql2);
                // 增加表头单独字段
                flowValue4=tempSql2;
                flowValue3.deleteCharAt(flowValue3.length() - 1);

                tcGdConfigflow.setFlowName("展示指标配置");
                tcGdConfigflow.setFlowKey("showTagConfig");
                tcGdConfigflow.setFlowSort("1004");
                break;
            case "execFlow":
                flowValue5.append("{ \"execConfig\": "+ jsonObject.get("execConfig")+"}");
                tcGdConfigflow.setFlowName("执行配置");
                tcGdConfigflow.setFlowKey("execFlowConfig");
                tcGdConfigflow.setFlowSort("1005");
                JSONObject execJson= jsonObject.getJSONObject("execConfig");

                flowValue1.append(execJson.getString("cycleBeginDate"));
                flowValue2.append(execJson.getString("cycleEndDate"));
                flowValue3.append(execJson.getString("cycleValue"));

                break;

            case "dataFlow":
                //增加用户群生成处理逻辑
                //
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

    private String getExecSql(String taskId){
        TcGdConfigmain tcGdConfigmain = tcGdConfigmainService.findById(taskId);
        String showSql= tcGdConfigflowService.findById(tcGdConfigmain.getShowFlowId()).getFlowValue4();
        String fromSql= tcGdConfigflowService.findById(tcGdConfigmain.getSourceFlowId()).getFlowValue4();
        String joinSql= tcGdConfigflowService.findById(tcGdConfigmain.getTroopFlowId()).getFlowValue4();
        String condSql= tcGdConfigflowService.findById(tcGdConfigmain.getCondFlowId()).getFlowValue4();
        String execSql="";

        if(null==fromSql||"".equals(fromSql)||null==showSql||"".equals(showSql)){
            execSql="";
        }else {
            execSql=showSql+fromSql+joinSql+condSql;
        }

        return execSql;
    }

    public String getMainSourceKey() {
        return mainSourceKey;
    }

    public void setMainSourceKey(String mainSourceKey) {
        this.mainSourceKey = mainSourceKey;
    }
}
