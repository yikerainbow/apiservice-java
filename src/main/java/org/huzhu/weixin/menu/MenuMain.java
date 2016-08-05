package org.huzhu.weixin.menu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.sina.api.commons.util.ApacheHttpClient;

/**
 * ClassName: MenuMain
 * @Description: 点击型菜单事件
 * Created by guobao on 16/7/31.
 */

public class MenuMain {
    public static void main(String[] args) {

        ApacheHttpClient httpClient = new ApacheHttpClient(150, 600, 600, 1048576, 1, 300);

        ViewButton vbtIndex = new ViewButton();
        vbtIndex.setUrl("http://www.gongjiangren.com");
        vbtIndex.setName("元子互助");
        vbtIndex.setType("view");

        ViewButton vbtUserCertain = new ViewButton();
        vbtUserCertain.setUrl("http://www.gongjiangren.com/usercenter.html");
        vbtUserCertain.setName("个人中心");
        vbtUserCertain.setType("view");

        JSONArray button=new JSONArray();
        button.add(vbtIndex);
        button.add(vbtUserCertain);

        JSONObject menujson=new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);
        //这里为请求接口的url+号后面的是token，这里就不做过多对token获取的方法解释
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+"YJ2yZ40LPZYXPK7Ze6Pg-y59FYGCjcvAOJtMUgbd_k-BmoCIM7Bbz9Hs2G_0vR7-9onQWjv3VXnK46waJb7VX1JAWGvUqrRnlxHXtly1yaWJJG_Qvq_x42FVLmaE3fF8CEJdAJAMOT";

        try{

            //String ret = httpClient.post(url, map);
            //String rs= HttpUtils.sendPostBuffer(url, menujson.toJSONString());
            //System.out.println(rs);
        }catch(Exception e){
            System.out.println("请求错误！");
        }

    }

}
