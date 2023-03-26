package com.yj.edu.controller;

import com.yj.utils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
//@CrossOrigin//解决跨域问题，一个地址访问另一个地址，当访问协议，ip,端口号有一个不一样，产生跨域问题
public class LoginController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R getInfo(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    public R logout(){
        return R.ok();
    }
}
