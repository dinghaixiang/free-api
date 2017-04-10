package org.maiya.free.controller;

import org.maiya.free.model.RespBean;
import org.maiya.free.service.CourseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by beck on 2017/4/7.
 */
@RestController
public class CourseListController {
    @Autowired
    private CourseListService courseListService;
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public RespBean init(){
        return new RespBean(courseListService.init());
    }
    @RequestMapping(value = "/getCourse",method = RequestMethod.POST)
    public RespBean getCourse(@RequestBody Map param){
        return new RespBean(courseListService.getAllClass(param));
    }
}
