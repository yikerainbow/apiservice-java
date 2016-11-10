package org.huzhu.servlet;

import org.huzhu.weixin.thread.JsapiTicketThread;
import org.huzhu.weixin.thread.TokenThread;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 保证jsapi_ticket长期有效
 *
 * @author guobao
 * @date 2016-08-07
 */

public class JsapiTicketInitServlet extends BaseServlet {
    @Override
    public String readme() {
        return "保证jsapi_ticket长期有效\n" +
                "";
    }

    @Override
    public void init() {
        // 启动定时获取jsapi_ticket的线程
        new Thread(new JsapiTicketThread()).start();
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
    }

}
