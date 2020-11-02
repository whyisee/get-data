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

    @PostMapping
    public Result add(@RequestBody  Map<String,Object> params ,@CurrentUser TcAuthUser currentUser) {
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

        //TcGdConfigflow tcGdConfigflow = new TcGdConfigflow();
        //tcGdConfigflow.setFlowId(taskId);
        //tcGdConfigflow.setFlowName("数据源配置");
        //tcGdConfigflow.
        //tcGdConfigflowService.save(tcGdConfigflow);
        //String sourceFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);

        //params.getOrDefault()
        System.out.println("===test===>"+params);
        //String troopFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        String condFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        String showFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        String execFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        String dataFlowId= manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);





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
        return ResultGenerator.genSuccessResult(taskId);
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

        tcGdConfigmainService.update(tcGdConfigmain);
        return ResultGenerator.genSuccessResult();
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
        StringBuffer flowValue4 = new StringBuffer();
        StringBuffer flowValue5 = new StringBuffer();

        JSONObject jsonObject =JSONUtil.getJSONFromString((JSONObject.toJSONString(params)));

        switch (flowType){
            case "dataSource" :
                JSONArray dataSourcesSelect = jsonObject.getJSONArray("dataSourcesSelect");
                for (int i = 0; i < dataSourcesSelect.size(); i++) {
                    if(i != 0){
                        flowValue1=flowValue1.append(",");
                        flowValue2=flowValue2.append(",");
                        flowValue3=flowValue3.append(",");
                        flowValue4=flowValue4.append(",");
                    }
                    flowValue1=flowValue1.append(JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceId"));
                    flowValue2=flowValue2.append(JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceName"));
                    flowValue3=flowValue3.append(JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceNameZh"));
                    flowValue4=flowValue4.append(JSONUtil.getJSONFromString(dataSourcesSelect.get(i).toString()).get("sourceAlias"));
                }
                tcGdConfigflow.setFlowValue1(flowValue1.toString());
                tcGdConfigflow.setFlowValue2(flowValue2.toString());
                tcGdConfigflow.setFlowValue3(flowValue3.toString());
                tcGdConfigflow.setFlowValue4(flowValue4.toString());
                tcGdConfigflow.setFlowName("数据源配置");
                tcGdConfigflow.setFlowType("getdata");
                tcGdConfigflow.setFlowKey("dataSourceConfig");
                tcGdConfigflow.setFlowSort("1001");
                break;
            case "userTroop" :
                JSONArray userTroopsSelect = jsonObject.getJSONArray("userTroopsSelect");
                JSONArray userTroopsDel = jsonObject.getJSONArray("userTroopsDel");
                for (int i = 0; i < userTroopsSelect.size(); i++) {
                    if (i != 0) {
                        flowValue1=flowValue1.append(",");
                    }
                    flowValue1=flowValue1.append(JSONUtil.getJSONFromString(userTroopsSelect.get(i).toString()).get("troopId"));
                }
                for (int i = 0; i < userTroopsDel.size(); i++) {
                    if (i != 0) {
                        flowValue3=flowValue3.append(",");
                    }
                    flowValue3=flowValue3.append(JSONUtil.getJSONFromString(userTroopsDel.get(i).toString()).get("troopId"));
                }
                flowValue2.append(params.get("userTroopsSelectCP"));
                flowValue4.append(params.get("userTroopsDelCP"));

                tcGdConfigflow.setFlowValue1(flowValue1.toString());
                tcGdConfigflow.setFlowValue2(flowValue2.toString());
                tcGdConfigflow.setFlowValue3(flowValue3.toString());
                tcGdConfigflow.setFlowValue4(flowValue4.toString());
                tcGdConfigflow.setFlowName("用户群配置");
                tcGdConfigflow.setFlowType("getdata");
                tcGdConfigflow.setFlowKey("userTroopConfig");
                tcGdConfigflow.setFlowSort("1002");

                break;
            default :

        }
        // 新增/更新
        System.out.println("===test===>"+flowId);
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
}
