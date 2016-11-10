package org.huzhu;


import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.huzhu.result.Result;
import org.huzhu.service.WeixinPayService;

public class WeixinPayTest extends TestCase {

    private boolean open = false;

    public void test() { //把测试代码放在testAdd中
        if(open) {
            String spbill_create_ip = "127.0.0.1";
            String openId = "o7GHswaI1107K2Ez2QblsQyztH5M";
            String fee = "100";
            String result = JSON.toJSONString(new Result(0, WeixinPayService.getWeixinPayRealFormSign(spbill_create_ip, openId, fee)));

            print("result: " + result);
        }
    }

    public void print(String var) {
        if (true) {
            System.out.println(var);
        }
    }

}
