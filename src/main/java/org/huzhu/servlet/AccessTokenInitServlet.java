package org.huzhu.servlet;

import org.huzhu.weixin.thread.TokenThread;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 保证access_token长期有效
 *
 * @author guobao
 * @date 2016-08-05
 */

public class AccessTokenInitServlet extends BaseServlet {
    @Override
    public String readme() {
        return "保证access_token长期有效\n" +
                "";
    }

    @Override
    public void init() {
        // 启动定时获取access_token的线程
        new Thread(new TokenThread()).start();
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
    }

}
