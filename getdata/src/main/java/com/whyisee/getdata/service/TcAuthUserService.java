package com.whyisee.getdata.service;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.getdata.core.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by zoukh on 2020/10/24.
 */
public interface TcAuthUserService extends Service<TcAuthUser> {
    public static final String LOGIN_IDENTITY_KEY = "GETDATA_LOGIN_IDENTITY";

    boolean comparePassword(TcAuthUser user, TcAuthUser userInDataBase);
    String getToken(TcAuthUser user);
    TcAuthUser findByLoginName(String loginName);
    Result login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember);
    Result<String> logout(HttpServletRequest request, HttpServletResponse response);
    TcAuthUser ifLogin(HttpServletRequest request, HttpServletResponse response);
    TcAuthUser parseToken(String tokenHex);

}
