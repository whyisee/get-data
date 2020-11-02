package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdTagconfigFlow;
import com.whyisee.getdata.service.TcGdTagconfigFlowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import com.whyisee.utils.JSONUtil;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* Created by zoukh on 2020/11/01.
*/
@RestController
@RequestMapping("/tagconfigflow")
public class TcGdTagconfigFlowController {
    @Resource
    private TcGdTagconfigFlowService tcGdTagconfigFlowService;

    @PostMapping
    public Result add(@RequestBody TcGdTagconfigFlow tcGdTagconfigFlow) {
        tcGdTagconfigFlowService.save(tcGdTagconfigFlow);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdTagconfigFlowService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdTagconfigFlow tcGdTagconfigFlow) {
        tcGdTagconfigFlowService.update(tcGdTagconfigFlow);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdTagconfigFlow tcGdTagconfigFlow = tcGdTagconfigFlowService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdTagconfigFlow);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdTagconfigFlow> list = tcGdTagconfigFlowService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/search")
    public Result search(@RequestBody  Map< String, Object> params) {
    TcGdTagconfigFlow tcGdTagconfigFlow = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdTagconfigFlow.class);
        PageHelper.startPage((int)params.getOrDefault("page",1), (int)params.getOrDefault("limit",20));
    List<TcGdTagconfigFlow> list = tcGdTagconfigFlowService.search(tcGdTagconfigFlow);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}