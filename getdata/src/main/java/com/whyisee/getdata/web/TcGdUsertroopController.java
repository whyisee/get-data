package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdUsertroop;
import com.whyisee.getdata.service.TcGdUsertroopService;
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
@RequestMapping("/usertroop")
public class TcGdUsertroopController {
    @Resource
    private TcGdUsertroopService tcGdUsertroopService;

    @PostMapping
    public Result add(@RequestBody TcGdUsertroop tcGdUsertroop) {
        tcGdUsertroopService.save(tcGdUsertroop);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdUsertroopService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdUsertroop tcGdUsertroop) {
        tcGdUsertroopService.update(tcGdUsertroop);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdUsertroop tcGdUsertroop = tcGdUsertroopService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdUsertroop);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdUsertroop> list = tcGdUsertroopService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping("/search")
    public Result search(@RequestBody  Map< String, Object> params) {
    TcGdUsertroop tcGdUsertroop = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdUsertroop.class);
        PageHelper.startPage((int)params.getOrDefault("page",1), (int)params.getOrDefault("limit",20));
    List<TcGdUsertroop> list = tcGdUsertroopService.search(tcGdUsertroop);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}