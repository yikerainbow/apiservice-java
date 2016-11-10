package org.huzhu;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.huzhu.weixin.util.WeixinUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WeixinConfigTest extends TestCase {

    private boolean open = false;

    public void test() { //把测试代码放在testAdd中
        if(open) {
            String[] paths = {"classpath:spring/processor*.xml"};

            String url = "http://www.gongjiangren.com";
            String result = JSON.toJSONString(new Result(0, WeixinUtil.getWxConfig(url)));

            print("result: " + result);
        }
    }

    public void print(String var) {
        if (true) {
            System.out.println(var);
        }
    }

}
