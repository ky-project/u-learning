package com.ky.ulearning.gateway.common.utils;

import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.DefaultJwtSigner;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 刷新token工具类
 *
 * @author luyuhao
 * @date 19/12/09 02:54
 */
@Component
public class JwtRefreshTokenUtil {
    private static final long serialVersionUID = -3301605591108950415L;
    private Clock clock = DefaultClock.INSTANCE;

    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;

    /**
     * 获取注册名
     */
    public String getUsernameFromRefreshToken(String refreshToken) {
        return getClaimFromRefreshToken(refreshToken, Claims::getSubject);
    }

    /**
     * 获取创建时间
     */
    public Date getIssuedAtDateFromRefreshToken(String refreshToken) {
        return getClaimFromRefreshToken(refreshToken, Claims::getIssuedAt);
    }

    /**
     * 获取有效期
     */
    public Date getExpirationDateFromRefreshToken(String refreshToken) {
        return getClaimFromRefreshToken(refreshToken, Claims::getExpiration);
    }

    /**
     * 根据需求获取基本信息
     */
    public <T> T getClaimFromRefreshToken(String refreshToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromRefreshToken(refreshToken);
        return claimsResolver.apply(claims);
    }

    /**
     * 获取拓展信息对象
     */
    private Claims getAllClaimsFromRefreshToken(String refreshToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(gatewayConfigParameters.getSecret())
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (ExpiredJwtException eje) {
            return eje.getClaims();
        }
    }

    /**
     * 判断是否在有效期
     */
    private Boolean isTokenExpired(String refreshToken) {
        final Date expiration = getExpirationDateFromRefreshToken(refreshToken);
        return expiration.before(clock.now());
    }

    /**
     * 判断token创建时间在更新前
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String refreshToken) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    /**
     * 生成token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        claims.put("id", jwtAccount.getId());
        claims.put("loginType", jwtAccount.getLoginType());
        return doGenerateRefreshToken(claims, jwtAccount.getUsername());
    }

    /**
     * token生成器
     */
    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, gatewayConfigParameters.getSecret())
                .compact();
    }

    /**
     * token是否可以更新
     */
    public Boolean canTokenBeRefreshed(String refreshToken, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromRefreshToken(refreshToken);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(refreshToken) || ignoreTokenExpiration(refreshToken));
    }

    /**
     * 刷新token
     */
    public String refreshRefreshToken(String refreshToken) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromRefreshToken(refreshToken);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, gatewayConfigParameters.getSecret())
                .compact();
    }

    /**
     * token验证
     */
    public Boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        JwtAccount jwtAccount = (JwtAccount) userDetails;
        final Date created = getIssuedAtDateFromRefreshToken(refreshToken);
//        final Date expiration = getExpirationDateFromToken(token);
//        如果token存在，且token创建日期 > 最后修改信息的日期 则代表token有效
        return (!isTokenExpired(refreshToken)
                && !isCreatedBeforeLastPasswordReset(created, jwtAccount.getPwdUpdateTime())
        );
    }

    /**
     * 防篡改校验
     */
    public Boolean tamperProof(String token) {
        //获取header.payload
        String headerAndPayload = token.substring(0, token.lastIndexOf("."));
        String signature = token.substring(token.lastIndexOf(".") + 1);
        //获取key的字节流
        byte[] keyBytes = TextCodec.BASE64.decode(gatewayConfigParameters.getSecret());
        //生成key
        SecretKeySpec key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        //定义算法和key
        JwtSigner signer = new DefaultJwtSigner(SignatureAlgorithm.HS256, key);
        //对jwt进行重新加密
        String base64UrlSignature = signer.sign(headerAndPayload);
        return signature.equals(base64UrlSignature);
    }

    /**
     * 计算过期时间
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + gatewayConfigParameters.getRefreshExpiration());
    }
}
