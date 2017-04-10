package org.maiya.free.controller;

import org.apache.commons.collections.MapUtils;
import org.maiya.free.model.RespBean;
import org.maiya.free.service.TeacherConfigureService;
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
 * Created by beck on 2017/4/9.
 */
@RestController
public class TeacherConfigureController  {
    @Autowired
    private TeacherConfigureService service;
    @RequestMapping(value = "/getAllTeacher",method = RequestMethod.POST)
    public RespBean getAllTeacher(){
        EqlPage page = new EqlPage(0, 6);
        return new RespBean(Collections.asMap("teacherList",service.getAllTeacher(page),"eqlPage", page));
    }
    @RequestMapping(value = "/teacherList",method = RequestMethod.POST)
    public RespBean getAllCourseList(@RequestBody Map map){
        EqlPage page=new EqlPage(MapUtils.getInteger(map,"startIndex"),MapUtils.getInteger(map,"pageRows"));
        return new RespBean(Collections.asMap("courseList", service.getAllTeacher(page), "eqlPage", page));
    }

    @RequestMapping(value = "getAllEducation",method = RequestMethod.POST)
    public RespBean getAllEducation(){
        return new RespBean(service.getAllEducation());
    }
    @RequestMapping(value="addTeacher",method = RequestMethod.POST)
    public RespBean addTeacher(@RequestParam String teacherName,@RequestParam String introduction,
           @RequestParam MultipartFile photo,@RequestParam String education){
        String newFileName = null;
        if (!photo.isEmpty()) {
            try {
                String format=photo.getOriginalFilename().split("\\.")[1];
                newFileName= UUID.randomUUID().toString()+"."+format;
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("D:/free-pic/" + newFileName)));
                out.write(photo.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new RespBean(service.addTeacher(Collections.asMap("teacherId", Id.next(),"teacherName",teacherName,"introduction",introduction,"photo",newFileName,"education",education)));
    }
}
