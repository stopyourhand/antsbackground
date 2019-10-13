package com.ants.antsbackground.controller.people;


import com.ants.antsbackground.service.people.AdministratorService;
import com.ants.antsbackground.util.InterfaceAnalysisUtil;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 对管理员进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/24
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    /**
     * 登录功能
     *
     * @param account
     * @param passWord
     * @return
     */
    @PostMapping(value = "/login")
    public Map login(@RequestParam(value = "account") int account,
                            @RequestParam(value = "passWord") String passWord) {

        //保存返回给前端信息的hashMap
        Map resultMap = new HashMap<>(16);

        //判断输入的账号是否有效
        if (account <= 0) {
            resultMap.put("judge", false);
            resultMap.put("msg", "账号输入有误，请重新输入账号!");
            return resultMap;
        }

        //判断输入的密码是否为空
        if (passWord == null || "".equals(passWord)) {
            resultMap.put("judge", false);
            resultMap.put("msg", "密码不能为空，请重新输入密码!");
            return resultMap;
        }

        //从数据库中获取用户输入账号对应的密码
        String adminPassWord = administratorService.getAdministratorPassword(account);

        //判断从数据库中取出来的密码是否有效
        if (adminPassWord == null || "".equals(adminPassWord)) {
            resultMap.put("judge", false);
            resultMap.put("msg", "密码有误!");
            return resultMap;
        }

        //判断用户密码是否正确，依次来判断是否登录成功
        if (adminPassWord.equals(passWord)) {
            resultMap.put("judge", true);
            resultMap.put("msg", "登录成功!");
            return resultMap;
        } else {
            //使用学校结口的工具类
            InterfaceAnalysisUtil interfaceAnalysisUtil = new InterfaceAnalysisUtil();
            //调用学校接口进行登录
            String accounts = String.valueOf(account);
            resultMap = interfaceAnalysisUtil.analysis(accounts, passWord);

            return resultMap;
        }


    }

    /**
     * 退出登录功能
     * @param httpServletRequest
     * @return
     */
    @PostMapping(value = "/exit")
    public Map exit(HttpServletRequest httpServletRequest) {
        //保存返回给前端信息的hashMap
        Map resultMap = new HashMap<>(16);

        //获取当前会话
        HttpSession session = httpServletRequest.getSession();

        //移除studentId这个session属性，相当于注销状态
        session.removeAttribute("studentId");
        //使整个客户端对应的Session失效，里面的所有东西都会被清空，同时也释放了资源。
//        session.invalidate();

        resultMap.put("judge", true);
        return resultMap;

    }


    /**
     * 修改管理员密码
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PatchMapping(value = "/update")
    public Map<String, String> updateAdminPassword(HttpServletRequest request,
                                                   @RequestParam(value = "oldPassword") String oldPassword,
                                                   @RequestParam(value = "newPassword") String newPassword) {
        //保存返回给前端数据信息的hashMap
        Map<String, String> resultMap = new HashMap<>(16);

        //对原来的密码是否为空进行判断,即判断数据是否符合要求
        if (oldPassword == null || "".equals(oldPassword)) {
            resultMap.put("msg", "原密码不能为空,请重新输入密码!");
            return resultMap;
        }
        //对新输入的密码是否为空进行判断,即判断数据是否符合要求
        if (newPassword == null || "".equals(newPassword)) {
            resultMap.put("msg", "新密码不能为空,请重新输入密码!");
            return resultMap;
        }

        //获取当前会话
        HttpSession session = request.getSession();
        //获取管理员的id
        Integer adminId = 171543117;//(Integer)session.getAttribute("adminId");

        //用来保存数据库方法参数值的hashMap
        Map parameterMap = new HashMap(16);
        parameterMap.put("adminId", adminId);
        parameterMap.put("adminPassword", newPassword);

        //获取管理员原来的密码
        String adminOldPassword = administratorService.getOldPassword(adminId);
        if (adminOldPassword == null || "".equals(adminOldPassword)) {
            resultMap.put("msg", "原密码获取失败,请重新获取!");
            return resultMap;
        }

        if (!adminOldPassword.equals(oldPassword)) {
            resultMap.put("msg", "输入的原密码错误!");
            return resultMap;
        }

        int result = administratorService.updateAdminPassword(parameterMap);
        if (result <= 0) {
            resultMap.put("msg", "修改密码失败!");
            return resultMap;
        }

        resultMap.put("msg", "修改密码成功!");
        return resultMap;
    }

}
