package org.huzhu.commons;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import cn.sina.api.commons.util.ApiLogger;
import org.huzhu.util.Util;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;


/**
 * 基于http client 4.2.3叮咚云相关接口
 *
 * @author guobao
 * @version 1.0
 * @date 2016年7月17日 下午00:29:45
 */

public class MessageVerifyApis {
    // 查账户信息的http地址
    private static String URL_GET_USER_INFO = "https://api.dingdongcloud.com/v1/sms/userinfo";

    // 查询账户余额的http地址
    private static String URL_GET_BALANCE = "https://api.dingdongcloud.com/v1/sms/querybalance";

    // 验证码短信发送接口的http地址
    private static String URL_SEND_YZM = "https://api.dingdongcloud.com/v1/sms/sendyzm";

    private static String URL_SEND_YYYZM = "https://api.dingdongcloud.com/v1/sms/sendyyyzm";

    // 通知短信发送接口的http地址
    private static String URL_SEND_TZ = "https://api.dingdongcloud.com/v1/sms/sendtz";

    // 营销短信发送接口的http地址
    private static String URL_SEND_YX = "https://api.dingdongcloud.com/v1/sms/sendyx";

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    public static boolean sendVcodeMsg(String mobile, String vcode) {
        boolean ret = false;

        String apikey = "ac0efb77d16a7fcf578d35f9d83541f2";

        String yzmContent = "【元子互助】尊敬的用户，你的验证码是：" + vcode + "，请在10分钟内输入。请勿告诉其他人。";

        String retSend = MessageVerifyApis.sendYzm(apikey, mobile, yzmContent);
        try {
            JSONObject jsonObject = JSON.parseObject(retSend);
            int code = jsonObject.getInteger("code");
            if(code == 1) {
                ret = true;
            }
        } catch (Exception e) {
            ApiLogger.error("send msg error: ", e);
        }

        return ret;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

        // 修改为您的apikey. apikey可在官网（https://www.dingdongcloud.com)登录后获取
        String apikey = "ac0efb77d16a7fcf578d35f9d83541f2";

        // 修改为您要发送的手机号
        // String mobile = "15007110889";
        String mobile = "13697355863";
        //String mobile = "15026777126";

        /**************** 查账户信息调用示例 *****************/
        // System.out.println(DingdongCloudApis.getUserInfo(apikey));

        /**************** 查账户余额调用示例 *****************/
        // System.out.println(DingdongCloudApis.getUserInfo(apikey));

        /**************** 发送验证码短信 *****************/
        // 设置您要发送的内容(内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
        String yzmContent = "【蜜蜂互助】尊敬的用户，你的验证码是：" + Util.getRandomString(6) + "，请在10分钟内输入。请勿告诉其他人。";
        System.out.println(yzmContent);

        // 发验证码短信调用示例
        System.out.println(MessageVerifyApis.sendYzm(apikey, mobile, yzmContent));

//        /**************** 发送语音验证码短信 *****************/
//        // 必须纯数字4-6位
//        String yyContent = "1234";
//
//        // 发短信调用示例
//        System.out.println(MessageVerifyApis.sendYyYzm(apikey, mobile, yyContent));
//
//        /**************** 发送通知短信 *****************/
//        // 设置您要发送的内容
//        String tzContent = "【叮咚云】您已成功注册叮咚云，请联系支持人员安排对接测试。";
//
//        // 发短信调用示例
//        System.out.println(MessageVerifyApis.sendTz(apikey, mobile, tzContent));
//
//        /**************** 发送营销短信 *****************/
//        // 设置您要发送的内容，短信末尾必须带有“退订回T”
//        String yxContent = "【叮咚云】您已成功注册叮咚云，请联系支持人员安排对接测试。退订回t";
//
//        // 发短信调用示例
//        System.out.println(MessageVerifyApis.sendYx(apikey, mobile, yxContent));
    }

    /**
     * 查询账户信息接口
     *
     * @param apikey
     * @return
     */
    public static String getUserInfo(String apikey) {

        NameValuePair[] data = {new NameValuePair("apikey", apikey)};
        return doPost(URL_GET_USER_INFO, data);
    }

    /**
     * 查询账户余额接口
     *
     * @param apikey
     * @return
     */
    public static String getBalance(String apikey) {

        NameValuePair[] data = {new NameValuePair("apikey", apikey)};
        return doPost(URL_GET_BALANCE, data);
    }

    /**
     * 发送验证码短信
     *
     * @param apikey  apikey
     * @param mobile  手机号码(唯一，不许多个)
     * @param content 短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static String sendYzm(String apikey, String mobile, String content) {

        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        NameValuePair[] data = {new NameValuePair("apikey", apikey),

                new NameValuePair("mobile", mobile),

                new NameValuePair("content", content)};

        return doPost(URL_SEND_YZM, data);
    }

    /**
     * 发送语音验证码
     *
     * @param apikey
     * @param mobile  手机号码(唯一，不许多个)
     * @param content 短信发送内容(必须纯数字4-6位)
     * @return
     */
    public static String sendYyYzm(String apikey, String mobile, String content) {

        NameValuePair[] data = {new NameValuePair("apikey", apikey),

                new NameValuePair("mobile", mobile),

                new NameValuePair("content", content)};

        return doPost(URL_SEND_YYYZM, data);
    }

    /**
     * 发送通知短信
     *
     * @param apikey  apikey
     * @param mobile  手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
     * @param content 短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static String sendTz(String apikey, String mobile, String content) {

        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        NameValuePair[] data = {new NameValuePair("apikey", apikey),

                new NameValuePair("mobile", mobile),

                new NameValuePair("content", content)};

        return doPost(URL_SEND_TZ, data);
    }

    /**
     * 发送营销短信
     *
     * @param apikey  apikey
     * @param mobile  手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
     * @param content 短信发送内容（必须经过utf-8格式编码，短信末尾必须带有“退订回T”）
     * @return json格式字符串
     */
    public static String sendYx(String apikey, String mobile, String content) {

        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        NameValuePair[] data = {new NameValuePair("apikey", apikey),

                new NameValuePair("mobile", mobile),

                new NameValuePair("content", content)};

        return doPost(URL_SEND_YX, data);
    }

    /**
     * 基于HttpClient的post函数
     *
     * @param url  提交的URL
     * @param data 提交NameValuePair参数
     * @return 提交响应
     */
    private static String doPost(String url, NameValuePair[] data) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // method.setRequestHeader("ContentType",
        // "application/x-www-form-urlencoded;charset=UTF-8");
        method.setRequestBody(data);
        // client.getParams().setContentCharset("UTF-8");
        client.getParams().setConnectionManagerTimeout(10000);
        try {
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
