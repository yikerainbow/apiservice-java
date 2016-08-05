package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class QueryIsregisteredServlet extends BaseServlet {
    @Override
    public String readme() {
        return "查询是否注册\n" +
                "参数：callBack, openid\n" +
                "";
    }

    private WeixinListService weixinListService;

    @Override
    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        weixinListService = (WeixinListService) context.getBean("weixinListService");
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String callBack = Util.getParameterStringNotEmpty(request, "callback");
        String openid = Util.getParameterStringNotEmpty(request, "openid");

        String result = JSON.toJSONString(new Result(0, weixinListService.isRegistered(openid)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
