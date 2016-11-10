package org.huzhu;

import com.alibaba.fastjson.JSON;
import org.huzhu.result.Result;
import junit.framework.TestCase;
import org.huzhu.service.InsuranceListService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InsuranceServiceTest extends TestCase {

    private boolean open = false;

    public void test() { //把测试代码放在testAdd中
        if(open) {
            String[] paths = {"classpath:spring/processor*.xml"};
            ApplicationContext context = new ClassPathXmlApplicationContext(paths);
            InsuranceListService insuranceListService = (InsuranceListService) context.getBean("insuranceListService");
            String result = JSON.toJSONString(new Result(insuranceListService.getAllFromDb()));

            print("result: " + result);
        }
    }

    public void print(String var) {
        if (true) {
            System.out.println(var);
        }
    }

}
