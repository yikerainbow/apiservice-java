package org.huzhu.servlet;

import cn.sina.api.commons.util.ApiLogger;
import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.commons.Source;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jihui2 on 2015/3/12.
 */
public abstract class BaseServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        _process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        _process(request, response);
    }

    private void _process(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            response.setHeader("Content-type","text/html;charset=UTF-8");
            pw = response.getWriter();
            if (request.getParameterMap().keySet().contains("help")) {
                String str = readme();
                pw.println(str);
                return;
            }
//            String sourceStr = request.getParameter("source");
//            if (null == sourceStr) {
//                throw new Exception("parameter error");
//            }
//            if (!Source.validate(sourceStr)) {
//                throw new Exception("invalid source");
//            }
            process(request, pw);
            pw.flush();
        } catch (IOException e) {
            ApiLogger.error("Fail to write repsone", e);
        } catch (Exception e) {
            String excRep = this.getClass().getName() + ", url:" + request.getRequestURI();
            ApiLogger.error(excRep, e);
            String errorLog = e.getMessage();
            if (null == errorLog)  {
                errorLog = e.toString();
            }
            pw.println(JSON.toJSONString(new Result(-1, errorLog)));
            pw.flush();
        }
    }

    public abstract String readme();
    public abstract void process(HttpServletRequest request, PrintWriter pw) throws Exception;

}