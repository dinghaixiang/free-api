package org.maiya.free.service;

import org.maiya.free.utils.Collections;
import org.maiya.free.utils.eql.FreeDql;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/4/7.
 */
@Service
public class CourseListService {
    public Map init() {
        List<Map> mapList = getAllClass(null);
        return Collections.asMap("courseType", getAlltype(), "classList", mapList);
    }

    public List<Map> getAlltype() {
        return new FreeDql().select("getAllType").execute();
    }

    public List<Map> getAllClass(Map map) {
        List<Map> mapList= new FreeDql().select("getAllClass").params(map).execute();
        mapList.stream().forEach(a -> a.put("classPic", "pic/" + a.get("classPic")));
        return mapList;
    }

}
