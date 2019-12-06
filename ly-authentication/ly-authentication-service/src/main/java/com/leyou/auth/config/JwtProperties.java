package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * JwtProperties
 *
 * @author Billy
 * @date 2019/9/23 14:59
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {
    /**
     * 密钥
     */
    private String secret;

    /**
     * 公钥地址
     */
    private String pubKeyPath;

    /**
     * 私钥地址
     */
    private String priKeyPath;

    /**
     * token过期时间
     */
    private int expire;

    /**
     * cookie名称
     */
    private String cookieName;

    /**
     * 公钥
     */
    private PublicKey publicKey;

    /**
     * 私钥
     */
    private PrivateKey privateKey;

    // 对象一旦实例化后，就应该读取公钥和私钥
    // @PostConstruct在对象初始化完成、实例化后执行
    @PostConstruct
    public void init(){
        try {
            // 公钥私钥如果不存在，先生成
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }

}
