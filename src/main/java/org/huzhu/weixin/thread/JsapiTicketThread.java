package org.huzhu.weixin.thread;

import org.huzhu.weixin.proj.JsapiTicket;
import org.huzhu.weixin.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取调用微信JS接口的临时票据的线程
 *
 * @author guobao
 * @date 2016-08-07
 */
public class JsapiTicketThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(JsapiTicketThread.class);

    public static JsapiTicket jsapiTicket = null;

    public void run() {
        while (true) {
            try {
                jsapiTicket = CommonUtil.getJsapiTicket();
                if (null != jsapiTicket) {
                    log.info("获取jsapi_ticket成功，有效时长{}秒 token:{}", jsapiTicket.getExpiresIn(), jsapiTicket.getTicket());
                    // 休眠7000秒
                    Thread.sleep((jsapiTicket.getExpiresIn() - 200) * 1000);
                } else {
                    // 如果jsapi_ticket为null，60秒后再获取
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }
        }
    }
}
