package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.commons.Constants;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.util.Util;
import org.huzhu.weixin.proj.SNSUserInfo;
import org.huzhu.weixin.proj.WeixinOauth2Token;
import org.huzhu.weixin.util.AdvancedUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权后的回调请求处理
 *
 * @author guobao
 * @date 2016-08-02
 */
public class OAuthServlet extends BaseServlet {
    @Override
    public String readme() {
        return "查询用户信息\n" +
                "参数：code, callback\n" +
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
        String code = Util.getParameterStringNotEmpty(request, "code");
        String callBack = Util.getParameterStringNotEmpty(request, "callback");

        String result = JSON.toJSONString(new Result(0, weixinListService.getSnsUserInfo(code)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
