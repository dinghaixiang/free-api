package org.maiya.free.controller;

import org.apache.commons.collections.MapUtils;
import org.maiya.free.model.RespBean;
import org.maiya.free.service.CourseConfigureService;
import org.maiya.free.utils.Collections;
import org.n3r.eql.EqlPage;
import org.n3r.idworker.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by beck on 2017/4/8.
 */
@RestController
public class CourseConfigureController {
    @Autowired
    private CourseConfigureService service;

    @RequestMapping(value = "/courseConfInit", method = RequestMethod.POST)
    public RespBean getAllCourses() {
        EqlPage page = new EqlPage(0, 6);
        return new RespBean(Collections.asMap("courseList", service.getAllCourses(page), "eqlPage", page));
    }
    @RequestMapping(value = "/courseList",method = RequestMethod.POST)
    public RespBean getAllCourseList(@RequestBody  Map map){
        EqlPage page=new EqlPage(MapUtils.getInteger(map,"startIndex"),MapUtils.getInteger(map,"pageRows"));
        return new RespBean(Collections.asMap("courseList", service.getAllCourses(page), "eqlPage", page));
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    public RespBean addCourse(@RequestParam String className, @RequestParam String studentSize,
                              @RequestParam String classRoom, @RequestParam String week, @RequestParam String day,
                              @RequestParam String teacher, @RequestParam MultipartFile classPic,
                              @RequestParam String classIntro, @RequestParam String classType) {
        String newFileName = null;
        if (!classPic.isEmpty()) {
            try {
                String format=classPic.getOriginalFilename().split("\\.")[1];
                newFileName= UUID.randomUUID().toString()+"."+format;
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("D:/free-pic/" + newFileName)));
                out.write(classPic.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int num = service.insertClassTeacher(Collections.asMap("classId", Id.next(), "className", className, "studentSize",
                studentSize, "classRoom", classRoom, "classIntro", classIntro, "week", week, "day", day, "teacherId", teacher,
                "classType", classType, "picName", newFileName,"tcId",Id.next()));
        return new RespBean(num);
    }

    @RequestMapping(value = "/getTeacherAndType", method = RequestMethod.POST)
    public RespBean getAllTeacherAndType() {
        return new RespBean(Collections.asMap("teacherList", service.getAllTeacher(), "courseType", service.getAllClassType()));
    }
    @RequestMapping(value = "/deleteCourse",method = RequestMethod.POST)
    public RespBean delete(@RequestBody Map map){
        return new RespBean(service.deleteCourse(map));
    }
}