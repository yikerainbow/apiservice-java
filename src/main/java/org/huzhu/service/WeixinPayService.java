package org.huzhu.service;

import cn.sina.api.commons.util.ApiLogger;
import org.huzhu.weixin.util.WeixinUtil;
import org.huzhu.weixin.weixinpay.common.Configure;
import org.huzhu.weixin.weixinpay.common.Signature;
import org.huzhu.weixin.weixinpay.common.XMLParser;
import org.huzhu.weixin.weixinpay.notify.PayNotifyData;
import org.huzhu.weixin.weixinpay.protocol.UnifiedOrderReqData;
import org.huzhu.weixin.weixinpay.service.WxPayApi;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信支付
 *
 * @author guobao
 * @date 2016-11-08
 */

public class WeixinPayService {
    /**
     * 处理微信支付请求
     *
     * @param spbill_create_ip
     * @param openid
     * @param feeStr
     * @return
     */

    public static Map<String, Object> getWeixinPayRealFormSign(String spbill_create_ip, String openid, String feeStr) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        String appid = Configure.getAppid();
        String mch_id = Configure.getMchid();
        String body = "惠众互助";  //商品描述

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String out_trade_no = df.format(date);  //商家订单号

        String notify_url = "http://huzhonghz.s1.natapp.cc/weixin/notify_url"; //接收财付通通知的URL
        int total_fee = Integer.parseInt(feeStr);  //以分为单位
        String tradeType = "JSAPI";

        // 设置package参数
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        try {
            UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(appid, mch_id, body,
                    out_trade_no, total_fee, spbill_create_ip, notify_url, tradeType).setOpenid(openid).build();
            Map<String, Object> unifiedOrderMap = WxPayApi.UnifiedOrder(reqData);

            String prepayId = null;
            if (unifiedOrderMap.containsKey("prepay_id")) {
                prepayId = unifiedOrderMap.get("prepay_id").toString();
            } else {
                throw new Exception("获取prepay_id失败!");
            }

            String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
            String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
            packageParams.put("appId", appid);
            packageParams.put("timeStamp", timestamp);
            packageParams.put("nonceStr", nonceStr);
            packageParams.put("package", "prepay_id=" + prepayId);
            packageParams.put("signType", "MD5");
            String paySign = Signature.getSign(packageParams);  //支付订单签名

            //支付订单签名Map
            retMap.put("timeStamp", timestamp);
            retMap.put("nonceStr", nonceStr);
            retMap.put("package", "prepay_id=" + prepayId);
            retMap.put("paySign", paySign);
            retMap.put("signType", "MD5");
        } catch (Exception e) {
            ApiLogger.error("获取支付订单签名异常: ", e);
        }

        return retMap;
    }

    /**
     * 微信回调
     *
     * @param is
     * @return
     */

    public static String weixinPayCallback(InputStream is) {
        String resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

        String notifyXml = WeixinUtil.inputStream2String(is, "UTF-8");

        PayNotifyData payNotifyData = XMLParser.getObjectFromXML(notifyXml, PayNotifyData.class);
        ApiLogger.debug("从微信获取回调数据 ================\n" + payNotifyData.toString());
        if(payNotifyData != null) {
            if ("SUCCESS".equals(payNotifyData.getReturn_code().toUpperCase()) && "SUCCESS".equals(payNotifyData.getResult_code().toUpperCase())) {
                try {
                /*
                 ** TODO, 业务逻辑处理
                 */
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    ApiLogger.debug("微信支付处理成功================\n" + resXml);
                } catch (Exception e) {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[" + e.getMessage() + "]]></return_msg>" + "</xml> ";
                    ApiLogger.debug("微信支付处理失败================\n" + resXml);
                }

            } else {
                ApiLogger.debug("微信支付处理失败================\n" + resXml);
            }
        } else {
            ApiLogger.debug("微信支付处理失败================\n" + resXml);
        }

        return resXml;
    }
}
