package org.maiya.free.web.interceptor;

import org.maiya.free.exception.AppIdInvalidException;
import org.maiya.free.service.BaseUtilsService;
import org.maiya.free.utils.Redis;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.maiya.free.utils.Keys;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanglei on 16/11/10.
 */
public class AppIdInterceptor extends HandlerInterceptorAdapter {

    private static final String REDIS_PREFIX = "FAULT_APPID:";
    @Resource
    private BaseUtilsService baseUtilsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();
        String[] appIds = parameters.get(Keys.APP_ID);

        if (ArrayUtils.isEmpty(appIds)) {
            throw new AppIdInvalidException();
        }

        String appId = appIds[0];
        if (StringUtils.isEmpty(appId) || !checkAppId(appId)) {
            throw new AppIdInvalidException();
        }

        return true;
    }

    private boolean checkAppId(String appId) {
        String redisAppId = Redis.get(REDIS_PREFIX + appId);
        if (StringUtils.isNotEmpty(redisAppId)) {
            return redisAppId.equals(appId);
        }
        boolean flag = baseUtilsService.checkAppid(appId);
        if (flag) {
            Redis.setex(REDIS_PREFIX + appId, appId, 30, TimeUnit.MINUTES);
        }
        return flag;
    }
}
