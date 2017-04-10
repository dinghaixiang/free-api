package org.maiya.free.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtils {

    public static String toString(Object obj) {
        return isNotNull(obj) ? String.valueOf(obj) : "";
    }

    public static String toString4Trim(Object obj) {
        return isNotNull(obj) ? String.valueOf(obj).trim() : "";
    }

    /**
     * 前端填充<br>
     * 自动补充obj为指定长度的字符串,如果obj本身长度大于iLen
     *
     * @param obj       指定字符串
     * @param intLen    指定长度
     * @param sTtrarget 填充字符
     * @return
     */
    public static String firstFullFixLenStr(Object obj, int intLen, String sTtrarget) {
        return fullFixLenString(obj, intLen, sTtrarget, true);
    }

    /**
     * 后端填充<br>
     * 自动补充obj为指定长度的字符串,如果obj本身长度大于iLen
     *
     * @param obj       指定字符串
     * @param intLen    指定长度
     * @param strTarget 填充字符
     * @return
     */
    public static String lastFullFixLenStr(Object obj, int intLen, String strTarget) {
        return fullFixLenString(obj, intLen, strTarget, false);
    }

    /**
     * 自动补充obj为指定长度的字符串,如果obj本身长度大于iLen
     *
     * @param obj       指定字符串
     * @param intLen    指定长度
     * @param strTarget 填充字符
     * @param blType    填充类型：ture 前端填充；false 后端填充
     * @return
     */
    public static String fullFixLenString(Object obj, int intLen, String strTarget, boolean blType) {
        if (isNotNull(obj)) {
            String str = toString(obj);
            if (str.length() < intLen) {
                if (blType) {
                    for (int i = intLen - str.length(); i > 0; i--) {
                        str = strTarget + str;
                    }
                } else {
                    StringBuffer strBuffer = new StringBuffer(str);
                    for (int i = intLen - str.length(); i > 0; i--) {
                        strBuffer.append(strTarget);
                    }
                    str = strBuffer.toString();
                }
                return str;
            }
            return str;
        }
        return "";
    }

    public static Integer toInteger(Object obj) {
        return isNotNull(obj) ? Integer.parseInt(toString(obj).trim()) : null;
    }

    public static Integer toInteger(Object obj, Integer defInt) {
        return isNotEmpty(obj) ? Integer.parseInt(toString(obj).trim()) : defInt;
    }

    public static Long toLong(Object obj) {
        return isNotEmpty(obj) ? Long.parseLong(toString(obj).trim()) : 0;
    }


    public static String toString(Object obj, String defStr) {
        return isNotNull(obj) ? String.valueOf(obj) : defStr;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null ? true : false;
    }

    /**
     * 非空
     *
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        if (isNotNull(obj) && !"".equalsIgnoreCase(toString(obj))) {
            return true;
        }
        return false;
    }

    /**
     * 从cookie读取时，转译成中文
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String decodeCookieString(Object obj) {
        return URLDecoder.decode(toString(obj));
    }

    /**
     * 往cookie中写中文时，转译中文
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String encodeCookieString(Object obj) {
        return URLEncoder.encode(toString(obj));
    }

}
