package com.apd.www.web;

import com.apd.www.dao.UserRepository;
import com.apd.www.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by laintime on 15/4/22.
 */

@Controller
public class HelloController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    @ResponseBody
    public String sayHi()
    {

        User user=userRepository.findUserById(1011);
        return user.getUserName();
    }
}
