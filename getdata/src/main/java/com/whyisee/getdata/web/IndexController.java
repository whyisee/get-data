package com.whyisee.getdata.web;

import com.whyisee.getdata.annotation.PermissionLimit;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.getdata.model.TcGdDatasource;
import com.whyisee.getdata.service.TcAuthUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/24 18:25
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */

@Controller
public class IndexController {
    @Resource
    private TcAuthUserService tcAuthUserService;

    @RequestMapping("/")
    public String index(Model model){
        //Map<String, Object> dashboardMap = xxlJobService.dashboardInfo();
        Map<String, Object> dashboardMap = new HashMap<>();
        model.addAllAttributes(dashboardMap);
        return "index";
    }
    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit=false)
    public Result loginDo(HttpServletRequest request, HttpServletResponse response, @RequestBody TcAuthUser user, String ifRemember){
        boolean ifRem = (ifRemember!=null && ifRemember.trim().length()>0 && "on".equals(ifRemember))?true:false;

        return tcAuthUserService.login(request, response, user.getLoginName(), user.getPassword(), ifRem);
    }
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    @ResponseBody
    @PermissionLimit(limit=false)
    public Result<String> logout(HttpServletRequest request, HttpServletResponse response){
        return tcAuthUserService.logout(request, response);
    }
}
