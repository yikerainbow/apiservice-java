package org.huzhu.weixin.weixinpay.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.huzhu.weixin.weixinpay.common.Configure;
import org.huzhu.weixin.weixinpay.common.HttpService;
import org.huzhu.weixin.weixinpay.common.XMLParser;
import org.huzhu.weixin.weixinpay.protocol.UnifiedOrderReqData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by hupeng on 2015/7/28.
 */
public class WxPayApi {

    private static Log logger = LogFactory.getLog(WxPayApi.class);

    public static Map<String, Object> UnifiedOrder(UnifiedOrderReqData reqData) throws IOException, SAXException, ParserConfigurationException {
        String res = HttpService.doPost(Configure.UNIFIED_ORDER_API, reqData);
        logger.debug("UnifiedOrder get response:" + res);
        return XMLParser.getMapFromXML(res);
    }

    public static void main(String[] args) throws Exception {
        String appid = Configure.getAppid();
        String appSecret = Configure.getAppSecret();
        String mch_id = Configure.getMchid();
        String body = "惠众互助";  //商品描述
        String out_trade_no = "1234";  //商家订单号
        String spbill_create_ip = "127.0.0.1";
        String notify_url = "http://127.0.0.1:8180/tenpay_api_b2c/payNotifyUrl.jsp"; //接收财付通通知的URL
        String openId = "o7GHswaI1107K2Ez2QblsQyztH5M";
        String tradeType = "JSAPI";

        int total_fee = 1;

        UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(appid, mch_id, body, out_trade_no, total_fee, spbill_create_ip, notify_url, tradeType).setOpenid(openId).build();
        System.out.println(UnifiedOrder(reqData));


    }
}
