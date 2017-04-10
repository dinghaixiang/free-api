package org.maiya.free.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * FIXME.
 *
 * @author wanglei
 * @since 14-12-22 下午1:49
 */
public class MD5 {
    private static final Logger LOG = LoggerFactory.getLogger(MD5.class);

    public static String digest(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(Bytes.getBytes(text));

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; ++i)
                sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));

            return sb.toString();
        } catch (Exception e) {
            LOG.error("MD5 digest failed", e);
            throw new RuntimeException(e);
        }
    }

}
