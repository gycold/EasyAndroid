package com.easytools.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * package: com.easytools.tools.RoundUtils
 * author: gyc
 * description:四舍五入相关的工具类
 * time: create at 2018/1/20 0020 10:16
 */

public class RoundUtils {

    /**
     * 四舍五入操作
     *
     * @param num   被操作的数
     * @param scale 保留后几位
     *              如：
     *              System.out.println(MyMath.round(15.5, 0));//16
     *              System.out.println(MyMath.round(-15.5, 0));//-16
     *              System.out.println(MyMath.round(168.98765, 2));//168.99
     * @return
     */
    public static String round(double num, int scale) {
        if (scale > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("#0.");
            for (int i = 0; i < scale; i++) {
                sb.append("0");
            }
            String s = getFormatMoney(String.valueOf(num), sb.toString());
            return s;
        } else if (scale == 0) {
            return getFormatMoney(String.valueOf(num), "#0");
        }
        return getFormatMoney(String.valueOf(num));
    }

    /**
     * 四舍五入操作保留小数点后两位
     *
     * @param num 被操作的数
     *            如：
     *            System.out.println(MyMath.round(168.98765));//168.99
     * @return
     */
    public static String twoStringPoint(String num) {
        return getFormatMoney(num);
    }

    /**
     * BigDecimal四舍五入操作保留小数点后两位
     *
     * @param b 被操作的BigDecimal
     *          如：
     *          System.out.println(MyMath.round(168.98765));//168.99
     * @return
     */
    public static String twoBigDecimalPoint(BigDecimal b) {
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(result);
    }

    /**
     * 超过一万用小数点的方式代替
     *
     * @param money
     * @return
     */
    public static String convertMoney(BigDecimal money) {
        BigDecimal b = new BigDecimal(10000.00);//1万
        if (money.compareTo(b) >= 0) {//大于等于1万
            BigDecimal b2 = new BigDecimal(10000000.00);//1千万
            BigDecimal b3 = new BigDecimal(100000000.00);//1亿
            if (money.compareTo(b3) >= 0) {//大于等于1亿
                return money.divide(new BigDecimal(100000000), 2, BigDecimal.ROUND_HALF_UP).toString() + "亿";
            }
            if (money.compareTo(b2) >= 0) {//大于等于1千万
                return money.divide(new BigDecimal(10000000), 2, BigDecimal.ROUND_HALF_UP).toString() + "千万";
            }
            return money.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP).toString() + "万";
        } else if (money.compareTo(new BigDecimal(0)) == 0) {
            return "0.00";
        } else {
            return twoStringPoint(money.toString());
        }
    }

    /**
     * 数字转换，不带小数位
     *
     * @param num
     * @return
     */
    public static String convertNum(BigDecimal num) {
        BigDecimal b = new BigDecimal(10000);//1万
        if (num.compareTo(b) >= 0) {//大于等于1万
            BigDecimal b2 = new BigDecimal(10000000);//1千万
            BigDecimal b3 = new BigDecimal(100000000);//1亿
            if (num.compareTo(b3) >= 0) {//大于等于1亿
                return num.divide(new BigDecimal(100000000), 0, BigDecimal.ROUND_HALF_UP).toString() + "亿";
            }
            if (num.compareTo(b2) >= 0) {//大于等于1千万
                return num.divide(new BigDecimal(10000000), 0, BigDecimal.ROUND_HALF_UP).toString() + "千万";
            }
            return num.divide(new BigDecimal(10000), 0, BigDecimal.ROUND_HALF_UP).toString() + "万";
        } else if (num.compareTo(new BigDecimal(0)) == 0) {
            return "0";
        } else {
            return num.divide(new BigDecimal(1), 0, BigDecimal.ROUND_HALF_UP).toString();
        }
    }


    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)，例如：123456.00
     *
     * @param strMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney) {
        return getFormatMoney(strMoney, "#0.00");
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *                  #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                  ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr) {
        return getFormatMoney(strMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param strMoney
     * @param formatStr 格式(例如: ###.00 元),
     *                  #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                  ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale    使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(String strMoney, String formatStr, Locale locale) {
        Double doubleMoney;
        if (strMoney == null || strMoney.trim().equals("")) {
            strMoney = "0";
        }
        try {
            doubleMoney = Double.valueOf(strMoney);
        } catch (Exception e) {
            return strMoney;
        }
        return getFormatMoney(doubleMoney, formatStr, locale);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney) {
        return getFormatMoney(intMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *                  #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                  ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr) {
        return getFormatMoney(intMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param intMoney
     * @param formatStr 格式(例如: ###.00 元),
     *                  #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                  ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale    使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Integer intMoney, String formatStr, Locale locale) {
        if (intMoney == null) {
            intMoney = Integer.parseInt("0");
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(intMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param doubleMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney) {
        return getFormatMoney(doubleMoney, "#0.00");
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param doubleMoney
     * @param formatStr   格式(例如: ###.00 元),
     *                    #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                    ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        return getFormatMoney(doubleMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param doubleMoney
     * @param formatStr   格式(例如: ###.00 元),
     *                    #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                    ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale      使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(Double doubleMoney, String formatStr, Locale locale) {
        if (doubleMoney == null) {
            doubleMoney = Double.valueOf(0);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(doubleMoney);
    }

    /**
     * 转换成金钱的字符串,带格式(两位小数,千分位)
     *
     * @param bigDecimalMoney
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney) {
        return getFormatMoney(bigDecimalMoney, null);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param bigDecimalMoney
     * @param formatStr       格式(例如: ###.00 元),
     *                        #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                        ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     *                        使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr) {
        return getFormatMoney(bigDecimalMoney, formatStr, Locale.US);
    }

    /**
     * 转换成金钱的字符串,带格式
     *
     * @param bigDecimalMoney
     * @param formatStr       格式(例如: ###.00 元),
     *                        #占位表示可空,0占位表示少了就补0占位,E占位表示使用科学计数法,格式中加入其它字符就直接显示出来
     *                        ,如在前面或者后面加个[元]字.更多的请参考DecimalFormat
     * @param locale          使用哪国的数字格式,如美国的千分位来表示数字
     * @return
     * @create Dec 15, 2008 9:40:39 AM yuancong
     * @history
     */
    public static String getFormatMoney(BigDecimal bigDecimalMoney, String formatStr, Locale locale) {
        if (bigDecimalMoney == null) {
            bigDecimalMoney = BigDecimal.valueOf(0.00);
        }
        if (null == formatStr || formatStr.trim().equals("")) {
            formatStr = "￥#,##0.00";
        }
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);// 设置使用美国数字格式
        // (
        // 千分位
        // )
        df.applyPattern(formatStr);// 设置应用金额格式
        return df.format(bigDecimalMoney);
    }

    /**
     * 获取money类型的字符串
     *
     * @param cent
     * @return
     */
    public static String getMoneyStr(Long cent) {
        Money money = new Money();
        money.setCent(cent);
        return money.toString();
    }

    /**
     * 输入分的字符串，得到元的字符串
     *
     * @param cent
     * @return
     */
    public static String getMoneyStr(String cent) {
        if (cent != null && StringUtil.isNumeric(cent)) {
            Money money = new Money();
            money.setCent(Long.valueOf(cent));
            return money.toString();
        }
        return null;
    }
}
