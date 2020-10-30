package com.whyisee.getdata.interceptor;


import com.whyisee.getdata.core.ResultGenerator;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * push cookies to model as cookieMap
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class CookieInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

		// cookie
		if (modelAndView!=null && request.getCookies()!=null && request.getCookies().length>0) {
			HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();
			for (Cookie ck : request.getCookies()) {
				cookieMap.put(ck.getName(), ck);
			}
			modelAndView.addObject("cookieMap", cookieMap);
		}

		// static method
/*		if (modelAndView != null) {
			modelAndView.addObject("I18nUtil", FtlUtil.generateStaticModel(I18nUtil.class.getName()));
		}*/
		
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token=request.getHeader("token");
		//判断路径需要拦截
		//....code
		if(null==token||"".equals(token)){
			ResultGenerator.genFailResult("未登陆!");
			response.sendRedirect("/index");
			return false;

		}

		//如果token有效
		if(!TokenUtils.isExpire(token)){
			TcAuthUser user = TokenUtils.parseToken(token);
			//我们将解析的用户结果先放入session中
			request.getSession().setAttribute("currentUser",user);
		}

		return true;
	}

	
}
