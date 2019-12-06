package com.leyou.auth.test;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
 
    private static final String pubKeyPath = "D:\\java\\rsa\\rsa.pub";
 
    private static final String priKeyPath = "D:\\java\\rsa\\rsa.pri";
 
    private PublicKey publicKey;
 
    private PrivateKey privateKey;
 
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }
 
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
 
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }
 
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU2OTY1MjExM30.VYHf7EzJgf7jprshC2Z3DkaMO9Lms8-8tH2VNlT5HEUZ6NJ_3NQhPZEzhj-uFN6xYSkYy5oqsJdleBEJZhAVRlmOE6PinVHcGSG1G5DqggLbcj4axV7e3E5I6FbnvOQBoou67r1gWRPyirygbJWH9-dDFkIS-zKbzhCJzECdvFg";
 
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}