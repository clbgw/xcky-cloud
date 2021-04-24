package com.xcky.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * JWT(Json Web Token)工具类
 *
 * @author lbchen
 */
@Slf4j
public class JwtUtil {
    /**
     * 解析jwt
     */
    public static Claims parseJWT(String jsonWebToken) {
        if (StringUtils.isEmpty(jsonWebToken)) {
            return null;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Constants.TOKEN_KEY))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            log.warn("token过期了");
            return e.getClaims();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 构建jwt,默认900秒
     */
    public static String createJWT(String username) {
        return createJWT(username, Constants.DEFAULT_TOKEN_TIMEOUT);
    }

    /**
     * 构建jwt
     *
     * @param username       用户名
     * @param timeoutSeconds 超时秒数
     * @return token字符串
     */
    private static String createJWT(String username, Integer timeoutSeconds) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.TOKEN_KEY);
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").claim(Constants.TOKEN_FIELD, username).signWith(signatureAlgorithm, signingKey);
        // 添加Token过期时间(15分钟过期)
        long expMillis = nowMillis + 1000 * timeoutSeconds;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
        // 生成JWT
        return builder.compact();
    }
}
