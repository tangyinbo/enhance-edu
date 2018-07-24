package com.cowboy.encrypt.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 加密算法工具类
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 10:00
 * @Description:
 */
public class RSAUtils {
    public static final String KEY_ALGORITHM = "RSA";

    public static final String MD5_WITH_RSA = "MD5withRSA";
    public static final String SHA1_WITH_RSA ="SHA1WithRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";

    public static final String PRIVATE_KEY = "RSAPrivateKey";

    public static void main(String[] args) throws Exception {
       /* Map<String, Object> keyMap;
        try {
            keyMap = initKey();
            String publicKey = getPublicKey(keyMap);
            System.out.println(publicKey);
            String privateKey = getPrivateKey(keyMap);
            System.out.println(privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/


        String privKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOE6jA7kztSoRZcGG1zBFF6Sm/agSKfEgLVFK/GWoOZt7LQo0E7SUeKzeKh9xJEu69Nb6AS2269ppdIkay9zDyLFPn0eLy7Y4pjIkI8wtQT5G5ytXMTItfCOdAcrfxmHT+G11NYswRVdWhXi7LXQaeHdIU/WCkMYXdu+Yb1EZpNFAgMBAAECgYBFGBnpY8YTtP+MQiJYxR7DmIRiF2/Sj9TQR0Ug2w3HQweviSnGVH0sZ9RnFBYeV9+eun4mHBau7GEjY2rCZrDIbV9RFVCS1Q6DixFb457562+ZhD63tdPrw8amHvfrzl6tXDHJfvnOlSZNv0n0E6UOdyNJQB/wQIrakMjX174yYQJBAP2rbuCU1Vj3jo+vE1iepIf7dUREhOjjyx9v37v7CNBNYogM0Nq5ra4wx8Ud7+m3K9kWv73QpruiMfnEpIf6YqkCQQDjTDpg4Mzd4mYy9gxg2xwZCzVhejGHosI1RCBv739tCWbPKM81IjA+oJCthJKD6aCvbsFNkxTzqGJAIfwKcCk9AkB61KYtzSO5nrXDUW02virBRbu0wNDyzEqxAEUAC0BrTO1nH32KS9qTvD8fba2cJ/vtdJyH1x7FQHaekN9ykIVxAkEA0eXK4zu09BHmHploxOiSAe5/1QxqQL7kaRwIeFXKbn6IGjWtY3u24Z6U/Ce4xWgxos+8Yk64wml0kZTQkzMQYQJBALbJ4Gj0IhbSs9AcIdqkV8msfw611XGygEnvNcfuhdtS0k1ZrKVnxwli/4I1RX46EUnVt1HPoblGxAt7j4VfCxQ=";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhOowO5M7UqEWXBhtcwRRekpv2oEinxIC1RSvxlqDmbey0KNBO0lHis3iofcSRLuvTW+gEttuvaaXSJGsvcw8ixT59Hi8u2OKYyJCPMLUE+RucrVzEyLXwjnQHK38Zh0/htdTWLMEVXVoV4uy10Gnh3SFP1gpDGF3bvmG9RGaTRQIDAQAB";

        String data = "hello world";

       /* String sign = RSAUtils.signByPrivateKey(data,privKey,RSAUtils.MD5_WITH_RSA);

        System.out.println(RSAUtils.validateSignByPublicKey(data,pubKey,RSAUtils.MD5_WITH_RSA,sign))*/;
    }



    /**
     * <p>获取Ras公私钥对{@code base64}编码后字符串
     * @return
     * @throws Exception if generator key fail
     * @see #PUBLIC_KEY
     * @see #PRIVATE_KEY
     */
    public static Map<String, String> getKeyPairStr() throws Exception {
        //公私钥对容器
        Map<String, Object> keyMap;
        keyMap = initKey();
        //
        Map<String,String> keyMapStr = new HashMap<>();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);
        keyMapStr.put(PUBLIC_KEY,publicKey);
        keyMapStr.put(PRIVATE_KEY,privateKey);
        return keyMapStr;
    }

    /**
     * 获取公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    private static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        byte[] publicKey = key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    private static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        byte[] privateKey = key.getEncoded();
        return encryptBASE64(key.getEncoded());
    }

    /**
     * base64解码
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * base64编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 生成公私钥对
     *
     * @return
     * @throws Exception
     */
    private static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 私钥签名,指定算法
     * 签名算法参考:{@link #MD5_WITH_RSA},{@link #SHA1_WITH_RSA}
     * @param data
     * @param privateKey
     * @param algorithm
     * @return
     * @throws Exception
     */
  /*  public static String signByPrivateKey(String data, String privateKey,String algorithm) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateK);
        signature.update(data.getBytes("utf-8"));
        return encode(signature.sign(),true).replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
    }*/

    /**
     * 公钥验签,指定算法
     * 签名算法参考:{@link #MD5_WITH_RSA},{@link #SHA1_WITH_RSA}
     * @param paramStr
     * @param publicKey
     * @param algorithm
     * @param signedData
     * @return
     * @throws Exception
     */
   /* public static boolean validateSignByPublicKey(String paramStr, String publicKey,String algorithm, String signedData) throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(publicK);
        signature.update(paramStr.getBytes("utf-8"));
        return signature.verify(Base64.decode(signedData));
    }


    public static String encode(byte[] data, boolean splitlines) {
        byte[] bytes = org.bouncycastle.util.encoders.Base64.encode(data);
        if (!splitlines) {
            return new String(bytes);
        } else {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            for(int i = 0; i < bytes.length; i += 64) {
                if (i + 64 < bytes.length) {
                    os.write(bytes, i, 64);
                    os.write(10);
                } else {
                    os.write(bytes, i, bytes.length - i);
                }
            }

            return new String(os.toByteArray());
        }
    }*/


}
