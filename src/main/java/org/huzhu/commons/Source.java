package org.huzhu.commons;

import org.huzhu.util.Util;

import java.util.*;

/**
 * Created by jihui2 on 2015/3/12.
 */
public class Source {
    /*
    source结构
    52bit +4bit_source分组 + 3bit版本号 + 4bit随机数
    分组：
    0 部门内部
    1 部门外部
    2 公司外
     */
    /**
     *
     */
    public static final long offset = 1L << 16;

    public static long f0Source = buildSource(offset, 0, 0, 0x0);
    public static long f1Source = buildSource(offset + 1, 1, 0, 0x0);
    public static long f2Source = buildSource(offset + 2, 2, 0, 0x0);
    public static long f3Source = buildSource(offset + 3, 1, 0, 0x0);
    public static long f4Source = buildSource(offset + 4, 0, 0, 0x0);
    public static long f5Source = buildSource(offset + 5, 2, 0, 0x0);
    public static Set<Long> set = new HashSet<Long>();

    private static final long configSource = buildSource(offset + 13, 0, 0, 0x0); // 134244352
    private static final long testSource = buildSource(offset + 14, 0, 0, 0x0); // 134246400

    static {
        set.add(f0Source);//134217728, 微博策略内部测试
        set.add(f1Source);//134219904，微博-技术-用户运营-反垃圾
        set.add(f2Source);//134222080，用于公司外测试
        set.add(f3Source);//134224000，大数据展示系统
        set.add(f4Source);//134225920，大数据策略内部线上
        set.add(f5Source);//134228224，
        set.add(buildSource(offset + 6, 0, 0, 0x0));//134230016，大数据策略内部线上-分发控制
        set.add(buildSource(offset + 7, 0, 0, 0x0));//134232064，大数据策略内部线上-接收端调权
        set.add(buildSource(offset + 8, 0, 0, 0x0));//134234112，大数据策略内部线上-实时反馈
        set.add(buildSource(offset + 9, 0, 0, 0x0));//134236160，大数据策略内部线上-Feed Demo
        set.add(buildSource(offset + 10, 0, 0, 0x0));//134238208，内部运营
        set.add(buildSource(offset + 11, 1, 0, 0x0));//134240384，外部运营
        set.add(buildSource(offset + 12, 1, 0, 0x0));//134242432，平台Feed


        set.add(configSource);//134244352，内部设置接口
        set.add(testSource);//134246400，内部测试接口

        set.add(buildSource(offset + 15, 1, 0, 0x0));//134248576，图片处理部门调用,写长文本图片到redislist
        set.add(buildSource(offset + 16, 0, 0, 0x0));//134250496，长文本图片,微博小秘书私信通知,设置分发控制比,开元调用
    }

    public static long buildSource(long i, int group, int v, int r) {
        long ret = 0;
        int s = 0;
        int w = 4;
        ret |= (r & ((1L << w) - 1)) << s;
        s += w;
        w = 3;
        ret |= (v & ((1L << w) - 1)) << s;
        s += w;
        w = 4;
        ret |= (group & ((1L << w) - 1)) << s;
        s += w;
        w = 52;
        ret |= (i & ((1L << w) - 1)) << s;
        return ret;
    }

    public static boolean validate(String sourceStr) {
        if (null == sourceStr) {
            return false;
        }
        long source = Util.parseLong(sourceStr, 0L);
        return set.contains(source);
    }

    public static boolean validateConfig(String sourceStr) {
        if (null == sourceStr) {
            return false;
        }
        long source = Util.parseLong(sourceStr, 0L);
        return configSource == source;
    }

    public static boolean validateTest(String sourceStr) {
        if (null == sourceStr) {
            return false;
        }
        long source = Util.parseLong(sourceStr, 0L);
        return testSource == source;
    }

    public static void main(String[] args) {
        System.out.println(buildSource(offset + 15, 1, 0, 0x0));
        System.out.println(buildSource(offset + 16, 0, 0, 0x0));
    }
}
