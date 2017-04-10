package org.maiya.free.service;

import org.maiya.free.utils.eql.FreeDql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/3/25.
 */
@Service
public class LoginService {
    public List<Map> findUser(Map map){
        return new FreeDql().select("findUser").params(map).execute();
    }
    public Integer insertUser(Map map){
        return new FreeDql().insert("insertUser").params(map).execute();
    }
    public List<Map> findUserByName(Map map){
        return new FreeDql().select("findUserByUserName").params(map).execute();
    }
    public Integer insertEmailRandom(Map map){
        return new FreeDql().insert("insertEmailRandom").params(map).execute();
    }
    public Integer updateEmail(Map map){
        return new FreeDql().update("updateEmail").params(map).execute();
    }
    public String getRandomByEmail(Map map){
        return new FreeDql().selectFirst("findRandomByEmail").params(map).execute();
    }
    public String getPwd(Map map){
        return new FreeDql().selectFirst("findPwd").params(map).execute();
    }
    public Integer updatePwd(Map map){
        return new FreeDql().update("updatePwd").params(map).execute();
    }
}
