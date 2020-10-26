package com.whyisee.getdata.web;

import com.alibaba.fastjson.JSONObject;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.getdata.service.TcAuthUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
* Created by zoukh on 2020/10/24.
*/
@RestController
@RequestMapping("/user")
public class TcAuthUserController {
    @Resource
    private TcAuthUserService tcAuthUserService;

    @PostMapping
    public Result add(@RequestBody TcAuthUser tcAuthUser) {
        tcAuthUserService.save(tcAuthUser);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        tcAuthUserService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody TcAuthUser tcAuthUser) {
        tcAuthUserService.update(tcAuthUser);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable String id) {
        TcAuthUser tcAuthUser = tcAuthUserService.findById(id);
        return ResultGenerator.genSuccessResult(tcAuthUser);
    }
    @GetMapping("/info")
    public Result info(@RequestParam String token) {
        TcAuthUser user = tcAuthUserService.parseToken(token);
        TcAuthUser tcAuthUser = tcAuthUserService.findById(user.getPersionId());
        return ResultGenerator.genSuccessResult(tcAuthUser);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TcAuthUser> list = tcAuthUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @RequestMapping(value="/search",method = {RequestMethod.GET})
    public @ResponseBody
    Result searchForGet(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size, @RequestParam Map<String,Object> data) throws Exception {
        PageHelper.startPage(page, size);
        List<TcAuthUser> list = tcAuthUserService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/login")
    public Object login(@RequestBody TcAuthUser user) {
        TcAuthUser userInDataBase = tcAuthUserService.findByLoginName(user.getLoginName());
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("message", "用户不存在");
        } else if (!tcAuthUserService.comparePassword(user, userInDataBase)) {
            jsonObject.put("message", "密码不正确");
        } else {
            String token = tcAuthUserService.getToken(userInDataBase);
            jsonObject.put("token", token);
            jsonObject.put("user", userInDataBase);
        }
        return jsonObject;
    }
}
