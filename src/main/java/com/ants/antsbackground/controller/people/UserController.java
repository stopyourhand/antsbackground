package com.ants.antsbackground.controller.people;


import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.UserDTO;
import com.ants.antsbackground.service.people.AdministratorService;
import com.ants.antsbackground.service.people.UserService;
import com.ants.antsbackground.util.InterfaceAnalysisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对用户管理进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/24
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 所有用户的信息列表，以及回收站用户的信息列表
     * userType -> 0:代表所有用户，包括回收站的用户 1:回收站的用户
     *
     * @param currentPage
     * @param userType
     * @return
     */
    @GetMapping(value = "/list")
    public Map listUsers(@RequestParam(value = "currentPage") int currentPage,
                         @RequestParam(value = "userType") int userType) {
        //用来保存返回给前端数据的hashMap
        Map resultMap = new HashMap(16);
        //判断数据是否符合要求
        if (currentPage <= 0) {
            resultMap.put("msg", "页数数据传输错误!");
            return resultMap;
        }
        if (userType < 0 || userType > 1) {
            resultMap.put("msg", "用户状态类型传输错误!");
            return resultMap;
        }

        //用来保存传输给数据库参数的数据下标的值，limit
        Map<String, Integer> parameterMap = new HashMap<>(16);
        //limit数据获取初始下标
        int head = (currentPage - 1) * PageConsts.USER_MANAGEMENT_PAGE_NUMBER;
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.USER_MANAGEMENT_PAGE_NUMBER);

        //用户列表总页数
        int allPage = 0;

        switch (userType) {
            case 0:
                //获取数据库中所有用户的数据信息
                parameterMap.put("judge", 0);
                List<UserDTO> userDTOList = userService.listUsers(parameterMap);
                resultMap.put("userList", userDTOList);

                //统计所有用户的数量(包括回收站)
                int userNumber = userService.countUser(parameterMap);
                if (userNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                //获取所有用户的数量的总页数
                allPage = (userNumber / PageConsts.USER_MANAGEMENT_PAGE_NUMBER) + 1;
                resultMap.put("allPage", allPage);
                break;
            case 1:
                //获取数据库中回收站用户的数据信息
                parameterMap.put("judge", 1);
                //获取回收站用户
                parameterMap.put("userType", 1);
                List<UserDTO> userDTORecycleList = userService.listUsers(parameterMap);
                resultMap.put("userRecycleList", userDTORecycleList);

                //统计所有用户的数量(包括回收站)
                int userRecycleNumber = userService.countUser(parameterMap);
                if (userRecycleNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                //获取所有用户的数量的总页数
                allPage = (userRecycleNumber / PageConsts.USER_MANAGEMENT_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
        }

        return resultMap;
    }

}
