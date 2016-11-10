package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinPayService;
import org.huzhu.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 微信支付
 *
 * @author guobao
 * @date 2016-11-05
 */
public class WeixinPayServlet extends BaseServlet {
    @Override
    public String readme() {
        return "获取微信预支付id\n" +
                "参数：openid, openid, callback\n" +
                "";
    }

    @Override
    public void init() {
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String spbill_create_ip = request.getRemoteAddr();
        String openid = Util.getParameterStringNotEmpty(request, "openid");
        String fee = Util.getParameterStringNotEmpty(request, "fee");
        String callBack = Util.getParameterStringNotEmpty(request, "callback");

        String result = JSON.toJSONString(new Result(0, WeixinPayService.getWeixinPayRealFormSign(spbill_create_ip, openid, fee)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
    }

}
