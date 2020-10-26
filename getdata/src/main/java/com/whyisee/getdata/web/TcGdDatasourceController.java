package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcGdDatasourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/10/20.
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
    public Result search(@RequestBody TcGdDatasource tcGdDatasource) {
        PageHelper.startPage(0, 0);
        List<TcGdDatasource> list = tcGdDatasourceService.search(tcGdDatasource);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
