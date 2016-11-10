package org.huzhu.weixin.weixinpay.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.huzhu.weixin.weixinpay.common.XMLParser;


/**
 * Created by Administrator on 2015/7/29.
 */
public class PayNotifyTemplate {

    private static Log logger = LogFactory.getLog(PayNotifyTemplate.class);

    private PayNotifyData payNotifyData;

    public PayNotifyTemplate(String notifyXml) {
//        boolean isValid = Signature.checkIsSignValidFromResponseString(notifyXml);
//        if (isValid) {
            payNotifyData = XMLParser.getObjectFromXML(notifyXml, PayNotifyData.class);
//        }

    }

    public String execute(PaySuccessCallBack successCallBack) {
        ResponseData responseData = new ResponseData();
//        if (payNotifyData == null) {
//            responseData.setReturn_code("FAIL");
//            responseData.setReturn_msg("签名错误");
//
//            return XMLParser.toXML(responseData);
//        }
        logger.debug("receive data from wechat:" + payNotifyData );
        if("SUCCESS".equals(payNotifyData.getReturn_code()) && "SUCCESS".equals(payNotifyData.getResult_code())) {

            if (successCallBack != null) {
                try {
                    successCallBack.onSuccess(payNotifyData);
                    responseData.setReturn_code("SUCCESS");
                    responseData.setReturn_msg("OK");
                } catch (Exception e) {
                    responseData.setReturn_code("FAIL");
                    responseData.setReturn_msg(e.getMessage());
                }

            }
        }

        return XMLParser.toXML(responseData);

    }


}
