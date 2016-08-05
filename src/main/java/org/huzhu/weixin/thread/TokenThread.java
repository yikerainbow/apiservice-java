package org.huzhu.weixin.thread;

import org.huzhu.commons.Constants;
import org.huzhu.weixin.proj.Token;
import org.huzhu.weixin.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取微信access_token的线程
 *
 * @author liuyq
 * @date 2013-05-02
 */
public class TokenThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);

    public static Token token = null;

    public void run() {
        while (true) {
            try {
                token = CommonUtil.getToken(Constants.appId, Constants.appSecret);
                if (null != token) {
                    log.info("获取access_token成功，有效时长{}秒 token:{}", token.getExpiresIn(), token.getAccessToken());
                    // 休眠7000秒
                    Thread.sleep((token.getExpiresIn() - 200) * 1000);
                } else {
                    // 如果access_token为null，60秒后再获取
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
