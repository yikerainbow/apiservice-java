package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.commons.Constants;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class WeixinMemberChargeServlet extends BaseServlet {
    @Override
    public String readme() {
        return "添加会员信息接口\n" +
                "参数：openid, personid, charge, callback\n" +
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
        String personid = Util.getParameterStringNotEmpty(request, "personid");
        String charge = Util.getParameterStringNotEmpty(request, "charge");
        String callBack = Util.getParameterStringNotEmpty(request, "callback");
        String result = JSON.toJSONString(new Result(0, weixinListService.updataMemberBalance(openid, personid, charge)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
