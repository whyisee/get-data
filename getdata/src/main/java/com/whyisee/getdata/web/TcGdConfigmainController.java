package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdConfigmain;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdConfigmainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whyisee.utils.JSONUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* Created by zoukh on 2020/10/25.
*/
@RestController
@RequestMapping("/configmain")
public class TcGdConfigmainController {
    @Resource
    private TcGdConfigmainService tcGdConfigmainService;

    @PostMapping
    public Result add(@RequestBody TcGdConfigmain tcGdConfigmain) {
        tcGdConfigmainService.save(tcGdConfigmain);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdConfigmainService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdConfigmain tcGdConfigmain) {
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
        PageHelper.startPage((int)params.get("page"), (int)params.get("limit"));
        List<TcGdConfigmain> list = tcGdConfigmainService.search(tcGdConfigmain);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
