package com.whyisee.getdata.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/19 15:22
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value="/get",method = {RequestMethod.POST})
    public
    String get(ModelMap modelMap, HttpServletRequest request, @RequestBody Map<String, Object> map) throws IOException {

        return "index";
    }

    @RequestMapping(value="/get",method = {RequestMethod.GET})
    public  String getForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> map) throws IOException {
        return "index";
    }
    @RequestMapping(value="/test",method = {RequestMethod.GET})
    public  String testForGet(ModelMap modelMap, HttpServletRequest request, @RequestParam Map<String, Object> map) throws IOException {
        return "index";
    }

}
