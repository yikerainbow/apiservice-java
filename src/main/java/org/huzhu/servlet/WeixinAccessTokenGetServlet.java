package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.huzhu.weixin.proj.Token;
import org.huzhu.weixin.thread.TokenThread;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class WeixinAccessTokenGetServlet extends BaseServlet {
    @Override
    public String readme() {
        return "获取access_token\n" +
                "参数：callback\n" +
                "";
    }


    @Override
    public void init() {
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String callBack = Util.getParameterStringNotEmpty(request, "callback");
        String result = JSON.toJSONString(new Result(0, TokenThread.token.getAccessToken()));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
        //pw.print(result);
    }

}
