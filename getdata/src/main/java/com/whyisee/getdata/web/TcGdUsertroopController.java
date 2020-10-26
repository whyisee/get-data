package com.whyisee.getdata.web;

import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcGdUsertroop;
import com.whyisee.getdata.service.TcGdUsertroopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2020/10/20.
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
}
