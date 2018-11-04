package com.wen.mall.tools;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WxHelper {

    private static final String APPID = "wx76d1172b0b1b153d";
    private static final String SECRET = "e2f5645902900cb8c0a327cda63b8b0b";
    private static final String GET_OPEN_ID_URL = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String GET_UNION_ID_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    public static JSONObject getOpenId(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("appid", APPID);
        data.put("secret", SECRET);
        data.put("grant_type", "authorization_code");
        data.put("js_code", code);
        String resp = HttpRequest.get(GET_OPEN_ID_URL).form(data).body();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        return jsonObject;
    }

    public static JSONObject getAccessToken() {
        String resp = HttpRequest.get(GET_ACCESS_TOKEN_URL + "?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET).body();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        return jsonObject;
    }


    public static JSONObject getUnionId(String code) {
        JSONObject openid = getOpenId(code);
        JSONObject token = getAccessToken();
        Map<String, Object> data = new HashMap<>();
        data.put("access_token", token.get("access_token"));
        data.put("openid", openid.get("openid"));
        data.put("lang", "zh_CN");
        String resp = HttpRequest.get(GET_UNION_ID_URL + "?access_token=" + token.get("access_token") + "&openid=" + openid.get("openid") + "&lang=zh_CN").body();
        JSONObject jsonObject = JSONObject.parseObject(resp);
        return jsonObject;
    }

    public static String getUnionIdByMin(String encryptedData,  String iv,String code ){
        JSONObject openid = getOpenId(code);
        JSONObject jsonObject =  getUserInfo(encryptedData, openid.getString("session_key"), iv);
        return jsonObject.getString("unionId");
    }

    public static JSONObject getUserInfo(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
