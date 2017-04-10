package org.maiya.free.service;

import org.maiya.free.utils.eql.FreeDql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/3/28.
 */
@Service
public class IndexService {
    public List<Map> queryClass(){
        List<Map> mapList=new FreeDql().select("queryClass").execute();
        mapList.stream().forEach(a->a.put("classPic","pic/"+a.get("classPic")));
        return mapList;
    }
    public List<Map> queryTeacher(){
        List<Map> mapList= new FreeDql().select("queryTeacher").execute();
        mapList.stream().forEach(a->a.put("photo","pic/"+a.get("photo")));
        return mapList;
    }
    public List<Map> searchClass(Map map){
        List<Map> mapList=new FreeDql().select("searchClass").params(map).execute();
        mapList.stream().forEach(a->a.put("classPic","pic/"+a.get("classPic")));
        return mapList;
    }
}
