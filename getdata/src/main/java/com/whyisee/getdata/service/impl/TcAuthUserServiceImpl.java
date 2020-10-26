package com.whyisee.getdata.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.whyisee.getdata.core.Result;
import com.whyisee.getdata.core.ResultCode;
import com.whyisee.getdata.dao.TcAuthUserMapper;
import com.whyisee.getdata.model.TcAuthUser;
import com.whyisee.getdata.service.TcAuthUserService;
import com.whyisee.getdata.core.AbstractService;
import com.whyisee.utils.CookieUtil;
import com.whyisee.utils.JacksonUtil;
import com.whyisee.utils.MD5Utils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;


/**
 * Created by zoukh on 2020/10/24.
 */
@Service
@Transactional
public class TcAuthUserServiceImpl extends AbstractService<TcAuthUser> implements TcAuthUserService  {
    @Resource
    private TcAuthUserMapper tcAuthUserMapper;

    //public static final String LOGIN_IDENTITY_KEY = "GETDATA_LOGIN_IDENTITY";


    @Override
    public TcAuthUser findByLoginName(String loginName){
        TcAuthUser userParam = new TcAuthUser();
        userParam.setLoginName(loginName);
        TcAuthUser user = tcAuthUserMapper.selectOne(userParam);
        return user;
    }



    public String getToken(TcAuthUser user) {
        String token = "";
        try {
            token = JWT.create()
                    .withAudience(user.getLoginName().toString())          // 将 user id 保存到 token 里面
                    .sign(Algorithm.HMAC256(user.getPassword()));   // 以 password 作为 token 的密钥
        } catch (UnsupportedEncodingException ignore) {
        }
        return token;
    }
    public boolean comparePassword(TcAuthUser user, TcAuthUser userInDataBase) {
        return MD5Utils.getMD5(user.getPassword())      // 将用户提交的密码转换为 hash
                .equals(userInDataBase.getPassword()); // 数据库中的 password 已经是 hash，不用转换
    }


    public Result<JSONObject> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember){
        Result result= new Result();
        TcAuthUser userParam = new TcAuthUser();
        userParam.setLoginName(username);
        // param
        if (username==null || username.trim().length()==0 || password==null || password.trim().length()==0){
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("server error");
            return result;
        }

        // valid passowrd
        TcAuthUser user = tcAuthUserMapper.selectOne(userParam);
        if (user == null) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("login_param_unvalid");
            return result;
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(user.getPassword())) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMessage("login_param_unvalid");
            return result;
        }

        String loginToken = makeToken(user);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("Success");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",loginToken);
        result.setData(jsonObject);

        return result;
    }

    private String makeToken(TcAuthUser user){
        String tokenJson = JacksonUtil.writeValueAsString(user);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    public TcAuthUser parseToken(String tokenHex){
        TcAuthUser user = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            user = JacksonUtil.readValue(tokenJson, TcAuthUser.class);
        }
        return user;
    }

    public Result<String> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        Result result= new Result();

        result.setCode(ResultCode.SUCCESS);
        result.setMessage("OK");
        return result;
    }

    public TcAuthUser ifLogin(HttpServletRequest request, HttpServletResponse response){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        TcAuthUser userParam = new TcAuthUser();
        if (cookieToken != null) {
            TcAuthUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                userParam.setLoginName(cookieUser.getLoginName());
                TcAuthUser dbUser = tcAuthUserMapper.selectOne(userParam);
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }
        return null;
    }


}
