package org.maiya.free.web.interceptor;

import org.maiya.free.exception.TimestampExpiredException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.maiya.free.utils.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by wanglei on 16/11/10.
 */
public class TimestampInterceptor extends HandlerInterceptorAdapter {
    // TODO: 配置化
    private static final Long MAX_DELAY = 10 * 60 * 1000L;
    private static final Logger LOG = LoggerFactory.getLogger(TimestampInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();
        String[] timestamps = parameters.get(Keys.TIMESTAMP);

        Long timestamp = parseTimestamp(timestamps);

        if (timestamp == null || !checkTimestamp(timestamp)) {
            throw new TimestampExpiredException();
        }

        return true;
    }

    private boolean checkTimestamp(Long timestamp) {
        long serverTime = Calendar.getInstance().getTimeInMillis();
        LOG.info("================client{},server{},timestamp{}", timestamp, serverTime, Math.abs(serverTime - timestamp));
        return Math.abs(serverTime - timestamp) < MAX_DELAY;
    }

    private Long parseTimestamp(String[] timestamps) {
        if (ArrayUtils.isEmpty(timestamps)) {
            return null;
        }

        String timestamp = timestamps[0];
        if (StringUtils.isEmpty(timestamp)) {
            return null;
        }

        try {
            return Long.parseLong(timestamp);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

}
