package org.huzhu.servlet;

import cn.sina.api.commons.util.ApiLogger;
import org.huzhu.service.WeixinPayService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.PrintWriter;


public class WeixinNotifyUrlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String ret = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        InputStream is = null;
        PrintWriter pw = null;
        try {
            is = req.getInputStream();
        } catch (Exception e) {
            ApiLogger.error("获取微信回调数据异常: ", e);
            pw.print(ret);
            pw.flush();
            return;
        }
        ret = WeixinPayService.weixinPayCallback(is);

        pw.print(ret);
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doGet(req, resp);
    }

}