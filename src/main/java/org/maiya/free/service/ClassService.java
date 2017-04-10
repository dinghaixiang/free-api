package org.maiya.free.service;

import org.maiya.free.utils.Collections;
import org.maiya.free.utils.eql.FreeDql;
import org.n3r.eql.EqlTran;
import org.n3r.eql.util.Closes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/4/2.
 */
@Service
public class ClassService {
    public List<Map> getClassDetail(Map map) {
        List<Map> mapList = new FreeDql().select("getClassDetail").params(map).execute();
        mapList.stream().forEach(a -> a.put("classPic", "pic/" + a.get("classPic")));
        mapList.stream().forEach(a -> a.put("photo", "pic/" + a.get("photo")));
        return mapList;
    }

    public String orderClass(Map map) {
        EqlTran tran = new FreeDql().newTran();
        List lists = new FreeDql().select("findOrder").params(map).execute();
        if (!"0".equals(String.valueOf(lists.get(0)))) {
            return "2";
        }
        List<Map> maps = new FreeDql().select("findWeekDay").params(map).execute();
        Map classInfo = maps.get(0);
        long num = new FreeDql().selectFirst("findTime").params(classInfo).execute();
        if (num != 0) {
            return "5";
        }
        map.putAll(classInfo);
        List remainList = new FreeDql().select("findRemainSize").params(map).execute();
        if ((Integer) remainList.get(0) > 0) {
            try {
                tran.start();
                new FreeDql().update("updateRemainSize").params(map).useTran(tran).execute();
                new FreeDql().insert("orderClass").useTran(tran).params(map).execute();
                tran.commit();
                return "1";
            } catch (Exception e) {
                e.printStackTrace();
                tran.rollback();
            } finally {
                Closes.closeQuietly(tran);
            }
            return "4";
        } else {
            return "3";
        }
    }

    public Map getAllOrder(Map param) {
        List<Map> maps = new FreeDql().select("getAllOrder").params(param).execute();
        String[] row = {"", "", "", "", ""};
        List row1 = new ArrayList(Arrays.asList(row));
        List row2 = new ArrayList(Arrays.asList(row));
        List row3 = new ArrayList(Arrays.asList(row));
        List row4 = new ArrayList(Arrays.asList(row));
        List row5 = new ArrayList(Arrays.asList(row));
        for (int i = 0; i < maps.size(); i++) {
            if ("1".equals(maps.get(i).get("day"))) {
                int num = Integer.valueOf(maps.get(i).get("week").toString()) - 1;
                row1.remove(num);
                row1.add(num, maps.get(i).get("className"));
            }
            if ("2".equals(maps.get(i).get("day"))) {
                int num = Integer.valueOf(maps.get(i).get("week").toString()) - 1;
                row1.remove(num);
                row1.add(num, maps.get(i).get("className"));
            }
            if ("3".equals(maps.get(i).get("day"))) {
                int num = Integer.valueOf(maps.get(i).get("week").toString()) - 1;
                row1.remove(num);
                row1.add(num, maps.get(i).get("className"));
            }
            if ("4".equals(maps.get(i).get("day"))) {
                int num = Integer.valueOf(maps.get(i).get("week").toString()) - 1;
                row1.remove(num);
                row1.add(num, maps.get(i).get("className"));
            }
            if ("5".equals(maps.get(i).get("day"))) {
                int num = Integer.valueOf(maps.get(i).get("week").toString()) - 1;
                row1.remove(num);
                row1.add(num, maps.get(i).get("className"));
            }
        }
        return Collections.asMap("row1", row1, "row2", row2, "row3", row3, "row4", row4);
    }
}
