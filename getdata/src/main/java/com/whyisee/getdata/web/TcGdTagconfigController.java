package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdTagconfig;
import com.whyisee.getdata.service.TcGdTagconfigService;
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
@RequestMapping("/tagconfig")
public class TcGdTagconfigController {
    @Resource
    private TcGdTagconfigService tcGdTagconfigService;

    @PostMapping
    public Result add(@RequestBody TcGdTagconfig tcGdTagconfig) {
        tcGdTagconfigService.save(tcGdTagconfig);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdTagconfigService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdTagconfig tcGdTagconfig) {
        tcGdTagconfigService.update(tcGdTagconfig);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdTagconfig tcGdTagconfig = tcGdTagconfigService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdTagconfig);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdTagconfig> list = tcGdTagconfigService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/search")
    public Result search(@RequestBody  Map< String, Object> params) {
    TcGdTagconfig tcGdTagconfig = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdTagconfig.class);
        PageHelper.startPage((int)params.getOrDefault("page",1), (int)params.getOrDefault("limit",20));
    List<TcGdTagconfig> list = tcGdTagconfigService.search(tcGdTagconfig);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}