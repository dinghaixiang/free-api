package org.maiya.free.service;

import org.maiya.free.utils.eql.FreeDql;
import org.springframework.stereotype.Service;

/**
 * Created by chenfengfu on 2016/11/16.
 */
@Service
public class BaseUtilsService {

    public boolean checkAppid(String appId) {
        int result = new FreeDql().selectFirst("checkAppid").params(appId).returnType(Integer.class).execute();
        if (result == 1) {
            return true;
        }
        return false;
    }

    public String getAppSecret(String appId) {
        return new FreeDql().selectFirst("getAppSecret").params(appId).execute();
    }

}
