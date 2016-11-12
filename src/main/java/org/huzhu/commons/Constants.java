package org.huzhu.commons;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jihui2 on 2014/10/29.
 */
public class Constants {
    public static final String VERSION = "2.0.5";

    public static final long OP_DB_TIMEOUT = 500L;

    public static final String appId = "wxeb4e81f5354820f1";

    public static final String appSecret = "4dc5b1455d82b3959f571f6c59b0c2ce";

    public static final int membershipFee = 1;

    public static Set<String> accessIpSet = new HashSet<String>();

    static {
        accessIpSet.add("127.0.0.1");  //本地服务器
        accessIpSet.add("123.57.206.167"); //阿里云服务器
    }
}
