package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.configurer.ConfigFactory;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.dao.ManageSqlTools;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdConfigmainService;
import com.whyisee.utils.ParamUtils;
import com.whyisee.utils.SQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/20 20:10
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private ManageSqlTools manageSqlTools;
    @Resource
    private TcGdConfigmainService tcGdConfigmainService;

/*    @GetMapping("/seq")
    public Result getCommonSeq(String seqName) {
        String tcGdDatasource = manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        return ResultGenerator.genSuccessResult(tcGdDatasource);
    }*/

    @RequestMapping(value="/seq",method = {RequestMethod.GET})
    public  @ResponseBody Result getCommonSeq(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> map) throws IOException {
        String tcGdDatasource = manageSqlTools.getSeqId(ConfigFactory.SEQ_COMMON_ID);
        return ResultGenerator.genSuccessResult(tcGdDatasource);
    }

    @RequestMapping(value="/index",method = {RequestMethod.GET})
    public  String index(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> map) throws IOException {
        return "index";
    }

/*    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        TcGdConfigflow tcGdConfigflow = tcGdConfigflowService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdConfigflow);
    }*/

    @RequestMapping(value="/getsqldev/{id}",method = {RequestMethod.GET})
    public  @ResponseBody Result getsqldev(@PathVariable String id) throws Exception {
        Map<String,Object> param = new HashMap<>();
        SQLParser parser =  new SQLParser(param);

        TcGdConfigmain tcGdConfigmain = tcGdConfigmainService.findById(id);
        String execSql=tcGdConfigmain.getExecSql();
        parser.addSQL(execSql);
        List tcGdDatasource = manageSqlTools.queryList(parser,param);
        return ResultGenerator.genSuccessResult(tcGdDatasource);
    }

    @RequestMapping(value="/getresultdev/{id}",method = {RequestMethod.GET})
    public  @ResponseBody Result getresult(@PathVariable String id) throws Exception {
        Map<String,Object> param = new HashMap<>();
        SQLParser parser =  new SQLParser(param);

        TcGdConfigmain tcGdConfigmain = tcGdConfigmainService.findById(id);
        String execSql=tcGdConfigmain.getExecSql();
        parser.addSQL(execSql);
        List tcGdDatasource = manageSqlTools.queryList(parser,param);
        return ResultGenerator.genSuccessResult(tcGdDatasource);
    }
}
