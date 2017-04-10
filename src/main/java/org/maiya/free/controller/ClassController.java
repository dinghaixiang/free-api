package org.maiya.free.controller;

import org.maiya.free.model.RespBean;
import org.maiya.free.service.ClassService;
import org.n3r.idworker.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by beck on 2017/4/2.
 */
@RestController
public class ClassController {
    @Autowired
    private ClassService classService;
    @RequestMapping(value = "/getClassDetail",method = RequestMethod.POST)
    public RespBean getClassDetail(@RequestBody Map param){
        return new RespBean(classService.getClassDetail(param).get(0));
    }
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public RespBean orderClass(@RequestBody Map map){
        map.put("ucId", Id.next());
        return new RespBean(classService.orderClass(map));
    }
    @RequestMapping(value = "/allOrder",method = RequestMethod.POST)
    public RespBean getAllOrder(@RequestBody Map map){
        return new RespBean(classService.getAllOrder(map));
    }
}
