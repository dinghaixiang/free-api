package org.maiya.free.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.mail.HtmlEmail;
import org.maiya.free.model.RespBean;
import org.maiya.free.service.LoginService;
import org.n3r.idworker.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * Created by beck on 2017/3/25.
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping("/login")
    public RespBean login(@RequestBody Map param){
        List<Map> userList=loginService.findUser(param);
        if(userList.size()==1){
            return new RespBean(userList.get(0));
        }else{
            return new RespBean("1","用户名或密码错误");
        }
    }
   @RequestMapping("/register")
    public RespBean register(@RequestBody Map param){
      param.put("userId", String.valueOf(Id.next()));
      if(loginService.findUserByName(param).size()!=0){
          return new RespBean("1","该用户名已存在");
      }
      return new RespBean(loginService.insertUser(param));
    }
    @RequestMapping(value = "/getRandom",method = RequestMethod.POST)
    public RespBean getRandom(@RequestBody Map param){
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.163.com");
            email.setAuthentication("maiyainternet@163.com", "maiya123456");
            email.setSubject("free学生系统");
            email.setCharset("UTF-8");
            try {
                email.setFrom("maiyainternet@163.com", "free学生系统");
                email.addTo(MapUtils.getString(param,"email"));
                SecureRandom secureRandom=new SecureRandom();
                String random=String.valueOf(secureRandom.nextLong()).substring(1,5);
                email.setMsg("验证码是"+random);
                email.send();
                param.put("random",random);
                loginService.insertEmailRandom(param);
                return new RespBean(1);
            } catch (Exception e) {
                e.printStackTrace();
                return new RespBean(0);
            }
        }
    @RequestMapping(value = "/saveEmail",method = RequestMethod.POST)
    public RespBean saveEmail(@RequestBody Map map){
      String random=loginService.getRandomByEmail(map);
        if(random.equals(map.get("random"))){
            loginService.updateEmail(map);
            return new RespBean(1);
        }else{
            return new RespBean(0);
        }
    }
    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
    public RespBean changeEmail(@RequestBody Map map){
        if(map.get("oldPwd").equals(loginService.getPwd(map))){
            return new RespBean(loginService.updatePwd(map));
        }else{
            return new RespBean("1","旧密码错误");
        }
    }
}
