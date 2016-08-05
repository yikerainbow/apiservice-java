package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class QueryMemberInfoServlet extends BaseServlet {
    @Override
    public String readme() {
        return "查询保障人信息\n" +
                "参数：person_id, callBack\n" +
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
        String personId = Util.getParameterStringNotEmpty(request, "person_id");
        String callBack = Util.getParameterStringNotEmpty(request, "callback");

        String result = JSON.toJSONString(new Result(weixinListService.getMemberInfo(personId)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
