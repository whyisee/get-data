package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdDatasourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whyisee.utils.JSONUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* Created by zoukh on 2020/11/01.
*/
@RestController
@RequestMapping("/datasource")
public class TcGdDatasourceController {
    @Resource
    private TcGdDatasourceService tcGdDatasourceService;

    @PostMapping
    public Result add(@RequestBody TcGdDatasource tcGdDatasource) {
        tcGdDatasourceService.save(tcGdDatasource);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdDatasourceService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdDatasource tcGdDatasource) {
        tcGdDatasourceService.update(tcGdDatasource);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdDatasource tcGdDatasource = tcGdDatasourceService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdDatasource);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdDatasource> list = tcGdDatasourceService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/search")
    public Result search(@RequestBody  Map<String,Object> params) {
        TcGdDatasource tcGdDatasource = JSONUtil.toBean((JSONObject.toJSONString(params)), TcGdDatasource.class);
        PageHelper.startPage((int)(null == params.get("page")?1:params.get("page")), (int)(null == params.get("limit")?20:params.get("limit")));
        List<TcGdDatasource> list = tcGdDatasourceService.search(tcGdDatasource);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
