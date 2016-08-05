package org.huzhu.commons;

import cn.sina.api.commons.util.ApacheHttpClient;

/**
 * Created by guobao on 16/7/31.
 */
public class HttpInstance {
    private static ThreadLocal<ApacheHttpClient> localHttpClient = new ThreadLocal<ApacheHttpClient>(){
        @Override
        protected ApacheHttpClient initialValue() {
            return new ApacheHttpClient();
        }
    };

    public static ApacheHttpClient client() {
        return localHttpClient.get();
    }
}
