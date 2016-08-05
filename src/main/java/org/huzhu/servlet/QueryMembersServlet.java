package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.InsuranceListService;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class QueryMembersServlet extends BaseServlet {
    @Override
    public String readme() {
        return "查询保障人列表\n" +
                "参数：openid, callBack\n" +
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
        String openid = Util.getParameterStringNotEmpty(request, "openid");
        String callBack = Util.getParameterStringNotEmpty(request, "callback");

        String result = JSON.toJSONString(new Result(weixinListService.getMemberByOpenid(openid)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
