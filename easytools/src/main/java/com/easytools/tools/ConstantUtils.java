package com.easytools.tools;

/**
 * package: com.easytools.tools.ConstUtils
 * author: gyc
 * description:常量相关工具类
 * time: create at 2016/11/23 14:24
 */

public class ConstantUtils {

    private ConstantUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;

    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;

    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;

    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    /******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    public static final int MSEC = 1;

    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;

    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = 60000;

    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = 3600000;

    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    /******************** 正则相关常量 ********************/
    /**
     * 正则：手机号（简单）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";

    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3," +
            "5-8])|(18[0-9])|(147))\\d{8}$";

    /**
     * 正则：电话号码
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";

    /**
     * 正则：身份证号码15位
     */
    public static final String REGEX_IDCARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)" +
            "|3[0-1])\\d{3}$";

    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_IDCARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(" +
            "([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 正则：URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";

    /**
     * 正则：汉字
     */
    public static final String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";

    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     */
    public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";

    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-" +
            "(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|" +
            "(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)" +
            "-02-29)$";

    /**
     * 正则：IP地址，不带端口号
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}" +
            "(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 正则：IP地址，带端口号
     */
    public static final String REGEX_IP_PORT = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(:([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$)";


    /************************以下是与颜色相关的常量************************/
    /************************16进制************************/
    /**
     * 白色
     */
    public static final int WHITE = 0xffffffff;

    /**
     * 白色 - 半透明
     */
    public static final int WHITE_TRANSLUCENT = 0x80ffffff;

    /**
     * 黑色
     */
    public static final int BLACK = 0xff000000;

    /**
     * 黑色 - 半透明
     */
    public static final int BLACK_TRANSLUCENT = 0x80000000;

    /**
     * 透明
     */
    public static final int TRANSPARENT = 0x00000000;

    /**
     * 红色
     */
    public static final int RED = 0xffff0000;

    /**
     * 红色 - 半透明
     */
    public static final int RED_TRANSLUCENT = 0x80ff0000;

    /**
     * 红色 - 深的
     */
    public static final int RED_DARK = 0xff8b0000;

    /**
     * 红色 - 深的 - 半透明
     */
    public static final int RED_DARK_TRANSLUCENT = 0x808b0000;

    /**
     * 绿色
     */
    public static final int GREEN = 0xff00ff00;

    /**
     * 绿色 - 半透明
     */
    public static final int GREEN_TRANSLUCENT = 0x8000ff00;

    /**
     * 绿色 - 深的
     */
    public static final int GREEN_DARK = 0xff003300;

    /**
     * 绿色 - 深的 - 半透明
     */
    public static final int GREEN_DARK_TRANSLUCENT = 0x80003300;

    /**
     * 绿色 - 浅的
     */
    public static final int GREEN_LIGHT = 0xffccffcc;

    /**
     * 绿色 - 浅的 - 半透明
     */
    public static final int GREEN_LIGHT_TRANSLUCENT = 0x80ccffcc;

    /**
     * 蓝色
     */
    public static final int BLUE = 0xff0000ff;

    /**
     * 蓝色 - 半透明
     */
    public static final int BLUE_TRANSLUCENT = 0x800000ff;

    /**
     * 蓝色 - 深的
     */
    public static final int BLUE_DARK = 0xff00008b;

    /**
     * 蓝色 - 深的 - 半透明
     */
    public static final int BLUE_DARK_TRANSLUCENT = 0x8000008b;

    /**
     * 蓝色 - 浅的
     */
    public static final int BLUE_LIGHT = 0xff36a5E3;

    /**
     * 蓝色 - 浅的 - 半透明
     */
    public static final int BLUE_LIGHT_TRANSLUCENT = 0x8036a5E3;

    /**
     * 天蓝
     */
    public static final int SKYBLUE = 0xff87ceeb;

    /**
     * 天蓝 - 半透明
     */
    public static final int SKYBLUE_TRANSLUCENT = 0x8087ceeb;

    /**
     * 天蓝 - 深的
     */
    public static final int SKYBLUE_DARK = 0xff00bfff;

    /**
     * 天蓝 - 深的 - 半透明
     */
    public static final int SKYBLUE_DARK_TRANSLUCENT = 0x8000bfff;

    /**
     * 天蓝 - 浅的
     */
    public static final int SKYBLUE_LIGHT = 0xff87cefa;

    /**
     * 天蓝 - 浅的 - 半透明
     */
    public static final int SKYBLUE_LIGHT_TRANSLUCENT = 0x8087cefa;

    /**
     * 灰色
     */
    public static final int GRAY = 0xff969696;

    /**
     * 灰色 - 半透明
     */
    public static final int GRAY_TRANSLUCENT = 0x80969696;

    /**
     * 灰色 - 深的
     */
    public static final int GRAY_DARK = 0xffa9a9a9;

    /**
     * 灰色 - 深的 - 半透明
     */
    public static final int GRAY_DARK_TRANSLUCENT = 0x80a9a9a9;

    /**
     * 灰色 - 暗的
     */
    public static final int GRAY_DIM = 0xff696969;

    /**
     * 灰色 - 暗的 - 半透明
     */
    public static final int GRAY_DIM_TRANSLUCENT = 0x80696969;

    /**
     * 灰色 - 浅的
     */
    public static final int GRAY_LIGHT = 0xffd3d3d3;

    /**
     * 灰色 - 浅的 - 半透明
     */
    public static final int GRAY_LIGHT_TRANSLUCENT = 0x80d3d3d3;

    /**
     * 橙色
     */
    public static final int ORANGE = 0xffffa500;

    /**
     * 橙色 - 半透明
     */
    public static final int ORANGE_TRANSLUCENT = 0x80ffa500;

    /**
     * 橙色 - 深的
     */
    public static final int ORANGE_DARK = 0xffff8800;

    /**
     * 橙色 - 深的 - 半透明
     */
    public static final int ORANGE_DARK_TRANSLUCENT = 0x80ff8800;

    /**
     * 橙色 - 浅的
     */
    public static final int ORANGE_LIGHT = 0xffffbb33;

    /**
     * 橙色 - 浅的 - 半透明
     */
    public static final int ORANGE_LIGHT_TRANSLUCENT = 0x80ffbb33;

    /**
     * 金色
     */
    public static final int GOLD = 0xffffd700;

    /**
     * 金色 - 半透明
     */
    public static final int GOLD_TRANSLUCENT = 0x80ffd700;

    /**
     * 粉色
     */
    public static final int PINK = 0xffffc0cb;

    /**
     * 粉色 - 半透明
     */
    public static final int PINK_TRANSLUCENT = 0x80ffc0cb;

    /**
     * 紫红色
     */
    public static final int FUCHSIA = 0xffff00ff;

    /**
     * 紫红色 - 半透明
     */
    public static final int FUCHSIA_TRANSLUCENT = 0x80ff00ff;

    /**
     * 灰白色
     */
    public static final int GRAYWHITE = 0xfff2f2f2;

    /**
     * 灰白色 - 半透明
     */
    public static final int GRAYWHITE_TRANSLUCENT = 0x80f2f2f2;

    /**
     * 紫色
     */
    public static final int PURPLE = 0xff800080;

    /**
     * 紫色 - 半透明
     */
    public static final int PURPLE_TRANSLUCENT = 0x80800080;

    /**
     * 青色
     */
    public static final int CYAN = 0xff00ffff;

    /**
     * 青色 - 半透明
     */
    public static final int CYAN_TRANSLUCENT = 0x8000ffff;

    /**
     * 青色 - 深的
     */
    public static final int CYAN_DARK = 0xff008b8b;

    /**
     * 青色 - 深的 - 半透明
     */
    public static final int CYAN_DARK_TRANSLUCENT = 0x80008b8b;

    /**
     * 黄色
     */
    public static final int YELLOW = 0xffffff00;

    /**
     * 黄色 - 半透明
     */
    public static final int YELLOW_TRANSLUCENT = 0x80ffff00;

    /**
     * 黄色 - 浅的
     */
    public static final int YELLOW_LIGHT = 0xffffffe0;

    /**
     * 黄色 - 浅的 - 半透明
     */
    public static final int YELLOW_LIGHT_TRANSLUCENT = 0x80ffffe0;

    /**
     * 巧克力色
     */
    public static final int CHOCOLATE = 0xffd2691e;

    /**
     * 巧克力色 - 半透明
     */
    public static final int CHOCOLATE_TRANSLUCENT = 0x80d2691e;

    /**
     * 番茄色
     */
    public static final int TOMATO = 0xffff6347;

    /**
     * 番茄色 - 半透明
     */
    public static final int TOMATO_TRANSLUCENT = 0x80ff6347;

    /**
     * 橙红色
     */
    public static final int ORANGERED = 0xffff4500;

    /**
     * 橙红色 - 半透明
     */
    public static final int ORANGERED_TRANSLUCENT = 0x80ff4500;

    /**
     * 银白色
     */
    public static final int SILVER = 0xffc0c0c0;

    /**
     * 银白色 - 半透明
     */
    public static final int SILVER_TRANSLUCENT = 0x80c0c0c0;

    /**
     * 高光
     */
    public static final int HIGHLIGHT = 0x33ffffff;

    /**
     * 低光
     */
    public static final int LOWLIGHT = 0x33000000;
}
