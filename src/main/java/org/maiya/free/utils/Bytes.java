package org.maiya.free.utils;

import com.google.common.base.Charsets;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-12-22 下午2:04
 */
public class Bytes {

    public static byte[] getBytes(String str) {
        return str == null ? null : str.getBytes(Charsets.UTF_8);
    }

    public static String getString(byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }


}
