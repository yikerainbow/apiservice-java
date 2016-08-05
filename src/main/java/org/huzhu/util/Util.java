package org.huzhu.util;

import cn.sina.api.commons.redis.jedis.JedisMSServer;
import cn.sina.api.commons.redis.jedis.JedisPort;
import cn.sina.api.commons.util.ApiLogger;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jihui2 on 2015/3/12.
 */
public class Util {
    static private String IP;
    static {
        IP = _getLocalIp();
    }

    public static long parseLong(String str, long def) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return def;
        }
    }
    public static long parseLong(String str) throws Exception {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            throw new Exception("parameter error");
        }
    }
    public static int parseInt(String str) throws Exception {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new Exception("parameter error");
        }
    }
    public static int parseInt(String str, int def) throws Exception {
        if (null == str) {
            return def;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return def;
        }
    }
    public static boolean parseBoolean(String str) throws Exception {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            throw new Exception("parameter error");
        }
    }
    public static boolean parseBoolean(String str, boolean def) throws Exception {
        if (null == str) {
            return def;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            return def;
        }
    }

    public static String[] split(String str, String regex) throws Exception {
        if (null == str || "".equals(str)) {
            throw new Exception("parameter error");
        }
        String[] ret = null;
        try {
            ret = str.split(regex);
        } catch (Exception e) {
            throw new Exception("parameter error");
        }
        if (null == ret || 0 == ret.length) {
            throw new Exception("parameter error");
        }
        return ret;
    }
    public static Set<String> splitToSet(String str, String regex) throws Exception {
        Set<String> ret = new HashSet<String>();
        String[] strArray = split(str, regex);
        for (String string : strArray) {
            ret.add(string);
        }
        return ret;
    }

    public static boolean isTure(Boolean b) {
        if (null == b) {
            return false;
        }
        return b;
    }

    public static String[] longlistToArray(List<Long> list) {
        String[] ret = new String[list.size()];
        int i = 0;
        for (Long x : list) {
            ret[i++] = Long.toString(x);;
        }
        return ret;
    }

    public static String getRandomString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /* Added by guobao on 2015/7/7 */
    public static String[] stringlistToArray(List<String> list) {
        String[] ret = new String[list.size()];
        int i = 0;
        for (String x : list) {
            ret[i++] = x;
        }
        return ret;
    }

    public static String getParameterStringNotEmpty(HttpServletRequest request, String parameterName) throws Exception {
        String str = request.getParameter(parameterName);
        if (null == str || "".equals(str)) {
            throw new Exception("need parameter " + parameterName);
        }
        return str;
    }

    //// Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv|tw)",Pattern.CASE_INSENSITIVE)

    public static List<String> getDomainName(String url) {
        //Pattern p = Pattern.compile("(?<=http://|https://)[a-z,A-Z,0-9,\\.]*?\\.(com|cn|net|org|biz|info|cc|tv|tw)",Pattern.CASE_INSENSITIVE);
        Pattern p = Pattern.compile("(?<=http://|https://|//)[a-z,A-Z,0-9,\\.,\\-,_]+(?=\\/|:|#|\\?|$)",Pattern.CASE_INSENSITIVE); // TODO url host 缺合法字符
        Matcher matcher = p.matcher(url);
        List<String> list = new LinkedList<String>();
        if (matcher.find()) {
            String domain =  matcher.group();
            String[] arr = domain.split("\\.");
            list.add(domain);
            if (arr.length > 2) {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < arr.length-1; ++i) {
                    sb.setLength(0);
                    for (int j = i; j < arr.length-1; ++j) {
                        sb.append(arr[j]).append(".");
                    }
                    list.add(sb.append(arr[arr.length-1]).toString());
                }
            }
//            if (domain.length() >=4 && domain.substring(0,4).equals("www.")) {
//                domain = domain.substring(4, domain.length());
//            }
            return list;
        } else {
            return null;
        }
    }

    public static String intToIp(int i) {
        return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    }

    public static int ipToInt(final String addr) {
        final String[] addressBytes = addr.split("\\.");

        int ip = 0;
        for (int i = 0; i < 4; i++) {
            ip <<= 8;
            ip |= Integer.parseInt(addressBytes[i]);
        }
        return ip;
    }

    public static long ipToLong(final String addr) {
        final String[] addressBytes = addr.split("\\.");

        long ip = 0;
        for (int i = 0; i < 4; i++) {
            ip <<= 8;
            ip |= Integer.parseInt(addressBytes[i]);
        }
        return ip;
    }

    private static String _getLocalIp() {
        String localIp = null;
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress inet = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    inet = (InetAddress) addresses.nextElement();
                    if (inet.isSiteLocalAddress() && !inet.isLoopbackAddress() // 127.开头的都是lookback地址
                            && inet.getHostAddress().indexOf(":") == -1) {
                        localIp = inet.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            // 异常时视为获取失败
        }
        return localIp;
    }

    public static String getLocalIp() {
        return IP;
    }

    public static final int SIZEOF_LONG = Long.SIZE / Byte.SIZE;
    public static long toLong(byte[] bytes, int offset) {
        int length = SIZEOF_LONG;
        long l = 0;
        for(int i = offset; i < offset + length; i++) {
            l <<= 8;
            l ^= bytes[i] & 0xFF;
        }
        return l;
    }
    public static int putLong(byte[] bytes, int offset, long val) {
        if (bytes.length - offset < SIZEOF_LONG) {
            throw new IllegalArgumentException("Not enough room to put a long at"
                    + " offset " + offset + " in a " + bytes.length + " byte array");
        }
        for(int i = offset + 7; i > offset; i--) {
            bytes[i] = (byte) val;
            val >>>= 8;
        }
        bytes[offset] = (byte) val;
        return offset + SIZEOF_LONG;
    }
    public static String encodeUrl(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }
    public static Long getStartTime(){
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.set(Calendar.MILLISECOND, 0);
        return todayEnd.getTime().getTime();
    }
    public static Long getTomorrowStartTime(){
        return getStartTime() + 60L * 60 * 24 * 1000;
    }
    public static Long getStartTimeOfToday(Calendar todayEnd){
        todayEnd.set(Calendar.HOUR_OF_DAY, 0);
        todayEnd.set(Calendar.MINUTE, 0);
        todayEnd.set(Calendar.SECOND, 0);
        todayEnd.set(Calendar.MILLISECOND, 0);
        return todayEnd.getTime().getTime();
    }
    public static Long getTomorrowStartTime(Calendar todayEnd){
        return getStartTimeOfToday(todayEnd) + 60L * 60 * 24 * 1000;
    }
}
