package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mmall.pojo.User;
import javax.servlet.http.HttpSession;

/**
 * Created by 帅虎的电脑 on 2019/1/18.
 */
@Controller//指定这个类为Controller
@RequestMapping("/user/")//所有的请求链接在/user地址下
public class UserController {
    @Autowired//依赖注入
    //业务层的对象
    private IUserService iUserService;
    //设置请求地址，请求方法
    @RequestMapping(value="login.do",method= RequestMethod.POST)
    @ResponseBody//返回json数据
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=iUserService.login(username,password);
        if(response.isSuccess())//如果请求成功，则在session中添加该用户
            session.setAttribute(Const.CURRENT_USER,response.getData());
        return response;//否则返回错误信息
    }
    @RequestMapping(value="logout.do",method= RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);//在session中移除该用户信息
        return ServerResponse.createBySuccess();
    }
    @RequestMapping(value="register.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }
    @RequestMapping(value="check_valid.do",method= RequestMethod.POST)//检查用户名或邮箱是否有效
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }
    @RequestMapping(value="get_user_info.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null)
            return ServerResponse.createBySuccess(user);
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }
    @RequestMapping(value="forget_get_question.do",method= RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }
    @RequestMapping(value="forget_check_answer.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
    @RequestMapping(value="forget_reset_password.do",method= RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }
    @RequestMapping(value="reset_password.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }
    @RequestMapping(value="update_infomation.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_infomation(HttpSession session,User user){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser ==null)
            return ServerResponse.createByErrorMessage("用户未登录");
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess())
            session.setAttribute(Const.CURRENT_USER,response.getData());
        return response;
    }
    @RequestMapping(value="get_infomation.do",method= RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null)
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登陆status=10");
        return iUserService.getInformation(currentUser.getId());
    }
}
