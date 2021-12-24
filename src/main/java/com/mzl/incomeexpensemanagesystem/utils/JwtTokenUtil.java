package com.mzl.incomeexpensemanagesystem.utils;

import com.mzl.incomeexpensemanagesystem.enums.RetCodeEnum;
import com.mzl.incomeexpensemanagesystem.exception.CustomException;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @ClassName :   JwtTokenUtil
 * @Description: JWT工具类
 * @Author: v_ktlema
 * @CreateDate: 2021/12/24 17:25
 * @Version: 1.0
 */
public class JwtTokenUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtSecret";
    private static final String ISS = "incomeExpense";

    // 过期时间是3600秒，既是1个小时
    public static final long EXPIRATION = 60 * 60L;

    // 选择了记住我之后的过期时间为1天
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 1L;

    /**
     * 创建token
     * 注：如果是根据可变的唯一值来生成，唯一值变化时，需重新生成token
     * @param username
     * @param isRememberMe
     * @return
     */
    public static String createToken(String id, String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        //可以将基本不重要的对象信息放到claims中，此处信息不多,见简单直接放到配置内
//        HashMap<String,Object> claims = new HashMap<String,Object>();
//        claims.put("id", id);
//        claims.put("username",username);
        //id是重要信息，进行加密下
//        String encryId = RCUtils.encry_string(id);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                // 这里要早set一点，放到后面会覆盖别的字段
//                .setClaims(claims)
                .setIssuer(ISS)
                .setId(id)
                .setSubject(username)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取ID，同时做解密处理
     * @param token
     * @return
     */
    public static String getObjectId(String token){
        return getTokenBody(token).getId();
    }


    /**
     * 是否已过期
     * @param token
     * @throws
     * @return
     */
    @Deprecated
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 获取token信息，同时也做校验处理
     * @param token
     * @throw
     * @return
     */
    public static Claims getTokenBody(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(ExpiredJwtException expired){
            //过期
            throw new CustomException(RetCodeEnum.TOKEN_EXPIRED);
        }catch (SignatureException e){
            //无效
            throw new CustomException(RetCodeEnum.REQUEST_INVALID);
        }catch(MalformedJwtException malformedJwt){
            //无效
            throw new CustomException(RetCodeEnum.REQUEST_INVALID);
        }
    }

}
