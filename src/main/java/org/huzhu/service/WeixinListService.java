package org.huzhu.service;

import cn.sina.api.commons.util.ApiLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.DoubleArraySerializer;
import org.huzhu.commons.Constants;
import org.huzhu.commons.MessageVerifyApis;
import org.huzhu.dao.WeixinListDao;
import org.huzhu.util.Util;
import org.huzhu.weixin.proj.SNSUserInfo;
import org.huzhu.weixin.proj.WeixinOauth2Token;
import org.huzhu.weixin.util.AdvancedUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by guobao on 2016/7/26.
 */
public class WeixinListService {

    private WeixinListDao dao;
    public void setDao(WeixinListDao dao) {
        this.dao = dao;
    }

    public Boolean addVcodeList(String openid, String nickname, String mobile) {
        boolean ret = false;

        String vcode = Util.getRandomString(6);
        boolean retMsg = MessageVerifyApis.sendVcodeMsg(mobile, vcode);
        if(retMsg) {
            WeixinListDao.VcodeRow vcodeRow = dao.queryMsgInfo(openid, mobile);
            if(vcodeRow != null ) {
                boolean rmRet = dao.rmRegiter(openid, mobile);
                if(rmRet) {  //成功删除已注册信息
                    int state = 0;
                    boolean dbRet = dao.insertRegisterCode(openid, nickname, mobile, vcode, state);
                    if (!Util.isTure(dbRet)) {
                        ApiLogger.error("insert into register table failed !");
                    }
                    ret = dbRet;
                } else {
                    ApiLogger.error("insert into register table failed !");
                    return ret;
                }
            } else {
                int state = 0;
                boolean dbRet = dao.insertRegisterCode(openid, nickname, mobile, vcode, state);
                if (!Util.isTure(dbRet)) {
                    ApiLogger.error("insert into register table failed !");
                }
                ret = dbRet;
            }
        }

        return ret;
    }

    public WeixinListDao.TotalNum getHuzhuMembers() {
        int eventNum = 0;
        int huzhuMembers = 500 + dao.getMembers();

        return new WeixinListDao.TotalNum(huzhuMembers, eventNum);
    }

    public Boolean isVerified(String openid, String mobile, String vcode) {
        boolean verified = false;

        WeixinListDao.VcodeRow vcodeRow = dao.queryMsgInfo(openid, mobile);
        if(vcodeRow != null) {
            String dbVcode = vcodeRow.getVcode();
            int status = vcodeRow.getStatus();
            String update = vcodeRow.getupdate();

            String formatString = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat inputFormat = new SimpleDateFormat(formatString, Locale.ENGLISH);
            Date date = new Date();

            try {
                date = inputFormat.parse(update);
            } catch (ParseException e) {
                ApiLogger.error("time can't parse:" + update);
            }

            long updateTime = date.getTime() / 1000;
            long curTime = System.currentTimeMillis() / 1000;
            //System.out.println("updateTime: " + updateTime);
            //System.out.println("curTime: " + curTime);

            if (status == 0 && curTime - updateTime > 0 && curTime - updateTime <= 600) {
                if (vcode.trim().equals(dbVcode.trim())) {
                    dao.updateRegisterStatus(openid, mobile);
                    verified = true;
                }
            }
        }

        return verified;
    }

    public boolean isRegistered(String openid) {
       return dao.isRegistered(openid);
    }

    public SNSUserInfo getSnsUserInfo(String code) {
        SNSUserInfo snsUserInfo = null;

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken(Constants.appId, Constants.appSecret, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
        }

        return snsUserInfo;
    }

    public Boolean addMemberList(String openid, String nickname, String membersInfo) {
        boolean ret = false;

        if(membersInfo != null) {
            try {
                JSONArray jsonArray = JSON.parseArray(membersInfo);
                for(Iterator iter = jsonArray.iterator(); iter.hasNext();) {
                    JSONObject memberObj = (JSONObject) iter.next();
                    String personName = memberObj.getString("person_name");
                    String personId = memberObj.getString("person_id");

                    boolean dbRet = dao.insertMemberInfo(openid, nickname, personName, personId);
                    if (!Util.isTure(dbRet)) {
                        ApiLogger.error("insert into member table failed !");
                        return false;
                    }
                    ret = dbRet;
                }
            } catch (Exception e) {
                ApiLogger.error("fail to add member!", e);
            }
        }

        return ret;
    }

    public List<WeixinListDao.Row> getMemberByOpenid(String openid) {
        return dao.getMemberList(openid);
    }

    public WeixinListDao.MemberRow getMemberInfo(String personId) {
        return dao.queryMemberInfo(personId);
    }

    public boolean updataMemberBalance(String openid, String personid, String chargeStr) {
        boolean ret = true;

        double charge = Double.parseDouble(chargeStr);
        try {
            double dbBalance = dao.queryMemberInfo(personid).getBalance();
            double balance = dbBalance + charge;
            ret = dao.updateMemberBalance(openid, personid, balance);
        } catch (Exception e) {
            ApiLogger.error("charge failed: ", e);
            ret = false;
        }

        return ret;

    }
}
