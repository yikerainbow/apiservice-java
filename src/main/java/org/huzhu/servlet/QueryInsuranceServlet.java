package org.huzhu.servlet;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import org.huzhu.service.InsuranceListService;
import org.huzhu.util.Util;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

public class QueryInsuranceServlet extends BaseServlet {
    @Override
    public String readme() {
        return "查询保险的详细信息\n" +
                "参数：callBack\n" +
                "";
    }

    private InsuranceListService insuranceListService;

    @Override
    public void init() {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        insuranceListService = (InsuranceListService) context.getBean("insuranceListService");
    }

    @Override
    public void process(HttpServletRequest request, PrintWriter pw) throws Exception{
        String callBack = Util.getParameterStringNotEmpty(request, "callback");

        String result = JSON.toJSONString(new Result(insuranceListService.getAllFromDb()));

        String ret = callBack + "(" + result + ")";
        pw.print(ret);
        //pw.print(result);
    }

}
