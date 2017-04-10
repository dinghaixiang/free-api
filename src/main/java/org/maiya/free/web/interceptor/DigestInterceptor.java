package org.maiya.free.web.interceptor;

import org.maiya.free.exception.DigestInvalidException;
import org.maiya.free.service.BaseUtilsService;
import org.maiya.free.utils.MD5;
import org.maiya.free.utils.Redis;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.maiya.free.utils.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanglei on 16/11/10.
 */
public class DigestInterceptor extends HandlerInterceptorAdapter {
    private static final String SEPARATOR = "$";
    private final Logger log = LoggerFactory.getLogger(DigestInterceptor.class);
    private static final String REDIS_PREFIX = "FAULT_APPSECRET:";

    @Resource
    private BaseUtilsService baseUtilsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> parameters = request.getParameterMap();

        String paramString = buildParamString(parameters);

        String digestText = buildDigestText(parameters, paramString);

        String digestResult = digest(digestText);

        if (!checkDigest(digestResult, parameters)) {
            throw new DigestInvalidException();
        }

        return true;
    }

    private boolean checkDigest(String digestResult, Map<String, String[]> parameters) {
        String[] requestDigests = parameters.get(Keys.DIGEST);

        if (ArrayUtils.isEmpty(requestDigests)) {
            return false;
        }

        String requestDigest = requestDigests[0];
        log.debug("RequestDigest: {}, ServerDigest", requestDigest, digestResult);
        return StringUtils.equals(requestDigest, digestResult);
    }

    private String digest(String digestText) {
        return MD5.digest(digestText);
    }

    private String buildDigestText(Map<String, String[]> parameters, String paramString) {
        String appId = parameters.get(Keys.APP_ID)[0];
        String appSecret = getAppSecret(appId);

        StringBuilder builder = new StringBuilder();
        builder.append(appId).append(SEPARATOR).append(paramString).append(SEPARATOR).append(appSecret);

        String digestText = builder.toString();
        log.debug("DigestText: {}", digestText);
        return digestText;
    }

    private String buildParamString(Map<String, String[]> parameters) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (Keys.DIGEST.equals(entry.getKey()) || Keys.IMAGE.equals(entry.getKey())) {
                continue;
            }
            treeMap.put(entry.getKey(), entry.getValue()[0]);
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            builder.append(entry.getKey()).append(SEPARATOR).append(entry.getValue()).append(SEPARATOR);
        }

        String paramString = StringUtils.removeEnd(builder.toString(), SEPARATOR);
        log.debug("ParamString: {}", paramString);
        return paramString;
    }

    private String getAppSecret(String appId) {
        String redisAppSecret = Redis.get(REDIS_PREFIX + appId);
        if (StringUtils.isNotEmpty(redisAppSecret)) {
            return redisAppSecret;
        }
        String appSecret = baseUtilsService.getAppSecret(appId);
        if (appSecret != null) {
            Redis.setex(REDIS_PREFIX + appId, appSecret, 30, TimeUnit.MINUTES);
        }
        return appSecret;
    }

}
