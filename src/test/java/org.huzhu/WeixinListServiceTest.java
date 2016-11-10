package org.huzhu;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinListService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WeixinListServiceTest extends TestCase {

    private boolean open = false;

    public void test() { //把测试代码放在testAdd中
        if(open) {
            String[] paths = {"classpath:spring/processor*.xml"};
            ApplicationContext context = new ClassPathXmlApplicationContext(paths);
            WeixinListService weixinListService = (WeixinListService) context.getBean("weixinListService");
            //String result = JSON.toJSONString(new Result(weixinListService.getHuzhuMembers()));
            //String openid = "2324235";
            //String nickname = "weixin_name";
            //String mobile = "15007110889";
            //String vcode = "110213";
            String code = "011zX5P800j6it1HOZN80WT4P80zX5PR";
            //String result = JSON.toJSONString(new Result(weixinListService.addVcodeList(openid, nickname, mobile)));
            //String result = JSON.toJSONString(new Result(weixinListService.isVerified(openid, mobile, vcode)));
            //String result = JSON.toJSONString(new Result(weixinListService.isRegistered(openid)));
            String result = JSON.toJSONString(weixinListService.getSnsUserInfo(code));


            print("result: " + result);
        }
    }

    public void print(String var) {
        if (true) {
            System.out.println(var);
        }
    }

}
