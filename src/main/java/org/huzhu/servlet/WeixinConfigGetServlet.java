package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.util.Util;
import org.huzhu.weixin.thread.TokenThread;
import org.huzhu.weixin.util.WeixinUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class WeixinConfigGetServlet extends BaseServlet {
    @Override
    public String readme() {
        return "获取微信配置信息\n" +
                "参数：callback, url\n" +
                "";
    }


    @Override
    public void init() {
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String callBack = Util.getParameterStringNotEmpty(request, "callback");
        String url = Util.getParameterStringNotEmpty(request, "url");
        String result = JSON.toJSONString(new Result(0, WeixinUtil.getWxConfig(url)));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
        //pw.print(result);
    }

}
