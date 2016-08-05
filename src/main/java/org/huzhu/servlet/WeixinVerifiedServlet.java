package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class WeixinVerifiedServlet extends BaseServlet {
    @Override
    public String readme() {
        return "验证是否输入正确验证码\n" +
                "参数：callback, weixin_id, mobile, vcode\n" +
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
        String mobile = Util.getParameterStringNotEmpty(request, "mobile");
        String vcode = Util.getParameterStringNotEmpty(request, "vcode");

        String result = JSON.toJSONString(new Result(0, weixinListService.isVerified(openid, mobile, vcode)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
        //pw.print(result);
    }

}
