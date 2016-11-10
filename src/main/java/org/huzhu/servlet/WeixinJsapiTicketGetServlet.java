package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.util.Util;
import org.huzhu.weixin.proj.JsapiTicket;
import org.huzhu.weixin.thread.JsapiTicketThread;
import org.huzhu.weixin.thread.TokenThread;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class WeixinJsapiTicketGetServlet extends BaseServlet {
    @Override
    public String readme() {
        return "获取jsapi_ticket\n" +
                "参数：callback\n" +
                "";
    }


    @Override
    public void init() {
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String callBack = Util.getParameterStringNotEmpty(request, "callback");
        String result = JSON.toJSONString(new Result(0, JsapiTicketThread.jsapiTicket.getTicket()));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
        //pw.print(result);
    }

}
