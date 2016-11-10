package org.huzhu.weixin.proj;

/**
 * 调用微信JS接口的临时票据
 *
 * @author guobao
 * @date 2016-08-07
 */
public class JsapiTicket {
    // 调用JS接口的临时票据
    private String ticket;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}