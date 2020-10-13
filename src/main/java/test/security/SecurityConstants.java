package test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityConstants {
    public static String SECRET;
    public static String EXPIRATION_TIME; // 10 days
    public static String TOKEN_PREFIX;
    public static String HEADER_STRING;
    public static String SIGN_UP_URL;
    public static String LOGIN_URL;

    @Autowired
    public SecurityConstants(
            @Value("${auth.jwt.secret}") String secret,
            @Value("${auth.jwt.expiration}") String expTime,
            @Value("${auth.jwt.token.prefix}") String tokenPrefix,
            @Value("${auth.jwt.header}") String header,
            @Value("${auth.jwt.signup.url}") String signupUrl,
            @Value("${auth.jwt.login.url}") String loginUrl){
        this.SECRET = secret;
        this.EXPIRATION_TIME = expTime;
        this.TOKEN_PREFIX = tokenPrefix;
        this.HEADER_STRING = header;
        this.SIGN_UP_URL = signupUrl;
        this.LOGIN_URL = loginUrl;
    }
}
