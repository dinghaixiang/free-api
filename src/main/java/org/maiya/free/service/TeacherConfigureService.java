package org.maiya.free.service;

import org.maiya.free.utils.eql.FreeDql;
import org.n3r.eql.EqlPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/4/9.
 */
@Service
public class TeacherConfigureService {
    public List<Map> getAllTeacher(EqlPage page){
        List<Map> mapList= new FreeDql().select("getAllTeacher").limit(page).execute();
        mapList.stream().forEach(a -> a.put("photo", "pic/" + a.get("photo")));
        return mapList;
    }

    public List<Map> getAllEducation(){
      return new FreeDql().select("getAllEducation").execute();
    }
    public String getEducationNameById(Map map){
        return new FreeDql().selectFirst("getEducationNameById").params(map).execute();
    }
    public int addTeacher(Map map){
        map.put("educationName",getEducationNameById(map));
        return new FreeDql().insert("addTeacher").params(map).execute();
    }
}
