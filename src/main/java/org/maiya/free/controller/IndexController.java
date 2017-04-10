package org.maiya.free.controller;

import org.maiya.free.model.RespBean;
import org.maiya.free.service.CourseListService;
import org.maiya.free.service.IndexService;
import org.maiya.free.utils.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by beck on 2017/3/28.
 */
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private CourseListService courseListService;
    @RequestMapping("/index")
    public RespBean index(){
        return new RespBean(Collections.asMap("classList",indexService.queryClass(),"teacherList",indexService.queryTeacher()));
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public RespBean search(@RequestBody Map map){
        return new RespBean(Collections.asMap("classList",indexService.searchClass(map),"courseType",courseListService.getAlltype()));
    }
}
