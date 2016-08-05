package org.huzhu.service;

import cn.sina.api.commons.util.ApacheHttpClient;

/**
 * Created by jihui2 on 2015/3/12.
 */
public class HttpInstance {
    private ApacheHttpClient httpClient = new ApacheHttpClient();

    public ApacheHttpClient client() {
        return httpClient;
    }
}
