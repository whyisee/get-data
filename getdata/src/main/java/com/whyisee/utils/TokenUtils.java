package com.whyisee.utils;

import com.whyisee.getdata.model.TcAuthUser;

import java.math.BigInteger;

public class TokenUtils {

    public static TcAuthUser parseToken(String tokenHex){
        TcAuthUser user = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            user = JacksonUtil.readValue(tokenJson, TcAuthUser.class);
        }
        return user;
    }

    public static String makeToken(TcAuthUser user){
        String tokenJson = JacksonUtil.writeValueAsString(user);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    public static boolean isExpire(String token){
        return true;
    }
}
