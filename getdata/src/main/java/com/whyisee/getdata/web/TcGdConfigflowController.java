package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdConfigflow;
import com.whyisee.getdata.service.TcGdConfigflowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/10/20.
*/
@RestController
@RequestMapping("/configflow")
public class TcGdConfigflowController {
    @Resource
    private TcGdConfigflowService tcGdConfigflowService;

    @PostMapping
    public Result add(@RequestBody TcGdConfigflow tcGdConfigflow) {
        tcGdConfigflowService.save(tcGdConfigflow);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcGdConfigflowService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcGdConfigflow tcGdConfigflow) {
        tcGdConfigflowService.update(tcGdConfigflow);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcGdConfigflow tcGdConfigflow = tcGdConfigflowService.findById(id);
        return ResultGenerator.genSuccessResult(tcGdConfigflow);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcGdConfigflow> list = tcGdConfigflowService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
