package com.easytools.tools;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * package: com.easytools.tools.EncodeUtils
 * author: gyc
 * description:与编码解码相关的工具类
 * <p>
 * urlEncode          : URL 编码
 * urlDecode          : URL 解码
 * base64Encode       : Base64 编码
 * base64Encode2String: Base64 编码
 * base64Decode       : Base64 解码
 * htmlEncode         : Html 编码
 * htmlDecode         : Html 解码
 * <p>
 * time: create at 2016/11/23 23:37
 */

public class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * URL编码
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符
     * @return 编码为UTF-8的字符串
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * RFC-3986标准
     *
     * @param input
     * @return 返回经过RFC-3986标准编码后的字符串
     */
    public static String urlEncodeWithRFC3986(final String input) {
        return urlEncode(input, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    /**
     * URL编码
     * <p>若系统不支持指定的编码字符集,则直接将input原样返回</p>
     *
     * @param input       要编码的字符
     * @param charsetName 字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * URL解码
     * <p>若想自己指定字符集,可以使用 {@link #urlDecode(String input, String charset)}方法</p>
     *
     * @param input 要解码的字符串
     * @return URL解码后的字符串
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * URL解码
     * <p>若系统不支持指定的解码字符集,则直接将input原样返回</p>
     *
     * @param input       要解码的字符串
     * @param charsetName 字符集
     * @return URL解码为指定字符集的字符串
     */
    public static String urlDecode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            String safeInput = input.replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
            return URLDecoder.decode(safeInput, charsetName);
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字符串
     * @return Base64编码后的字符串
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * Base64编码
     * <p>
     * CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
     * DEFAULT 这个参数是默认，使用默认的方法来加密
     * NO_PADDING 这个参数是略去加密字符串最后的”=”
     * NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
     * URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public static byte[] base64Encode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64编码
     *
     * @param input 要编码的字节数组
     * @return Base64编码后的字符串
     */
    public static String base64Encode2String(final byte[] input) {
        if (input == null || input.length == 0) return "";
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字节数组
     */
    public static byte[] base64Decode(final String input) {
        if (input == null || input.length() == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * 将编码后的字符串解码为字符串
     *
     * @param input 编码后的字符串
     * @return 原始数据
     */
    public static String base64Decode2String(final String input) {
        if (input == null || input.length() == 0) return "";
        return new String(base64Decode(input));
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字节数组
     * @return Base64解码后的字符串
     */
    public static byte[] base64Decode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64URL安全编码
     * <p>将Base64中的URL非法字符�?,/=转为其他字符, 见RFC3548</p>
     *
     * @param input 要Base64URL安全编码的字符串
     * @return Base64URL安全编码后的字符串
     */
    public static byte[] base64UrlSafeEncode(final String input) {
        return Base64.encode(input.getBytes(), Base64.URL_SAFE);
    }

    /**
     * Html编码
     *
     * @param input 要Html编码的字符串
     * @return Html编码后的字符串
     */
    public static String htmlEncode(final String input) {
        if (input == null || input.length() == 0) return "";
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Html解码
     *
     * @param input 待解码的字符串
     * @return Html解码后的字符串
     */
    public static CharSequence htmlDecode(final String input) {
        if (input == null || input.length() == 0) return "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }

    /**
     * 返回以空格间隔的二进制编码字符串
     *
     * @param input The input.
     * @return binary string
     */
    public static String binaryEncode(final String input) {
        if (input == null || input.length() == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (char i : input.toCharArray()) {
            sb.append(Integer.toBinaryString(i)).append(" ");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 返回UTF-8格式的字符串
     *
     * @param input binary string
     * @return UTF-8 String
     */
    public static String binaryDecode(final String input) {
        if (input == null || input.length() == 0) return "";
        String[] splits = input.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String split : splits) {
            sb.append(((char) Integer.parseInt(split, 2)));
        }
        return sb.toString();
    }
}
