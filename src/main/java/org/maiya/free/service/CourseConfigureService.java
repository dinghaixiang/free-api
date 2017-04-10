package org.maiya.free.service;

import org.maiya.free.utils.eql.FreeDql;
import org.n3r.eql.EqlPage;
import org.n3r.eql.EqlTran;
import org.n3r.eql.util.Closes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/4/8.
 */
@Service
public class CourseConfigureService {
    public List<Map> getAllCourses(EqlPage page) {
        return new FreeDql().select("getAllCourse").limit(page).execute();
    }

    public List<Map> getAllTeacher() {
        return new FreeDql().select("getAllTeacher").execute();
    }

    public List<Map> getAllClassType() {
        return new FreeDql().select("getAllClassType").execute();
    }

    public int insertClassTeacher(Map map) {
        EqlTran eqlTran = new FreeDql().newTran();
        try {
            eqlTran.start();
            new FreeDql().insert("insertClass").params(map).useTran(eqlTran).execute();
            new FreeDql().insert("insertTeacherClass").params(map).useTran(eqlTran).execute();
            eqlTran.commit();
            return 1;
        } catch (Exception e) {
            eqlTran.rollback();
            e.printStackTrace();
        } finally {
            Closes.closeQuietly(eqlTran);
        }
        return 0;
    }

    public int deleteCourse(Map map){
      return new FreeDql().update("updateCourseValid").params(map).execute();
    }
}
