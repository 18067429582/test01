package com.bjpowernode.store.web;

import com.bjpowernode.store.domain.User;
import com.bjpowernode.store.service.UserService;
import com.bjpowernode.store.util.JsonResult;
import com.bjpowernode.store.web.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bjpowernode.store.web.BaseController.OK;

@RestController
@RequestMapping("user")
public class UserControllern extends BaseController{
    /**
     * idea检测功能，接口是不能直接创建bean对象的
     * 解决方案：改变spring的权限，在settings中搜inspections，设置spring core的权限
     */
    @Autowired
    private UserService userService;

    /**
     * 约定大于配置：
     * 注册:POVO接收参数
     * @param user
     * @return
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    /**
     * 登录：普通类型参数接收
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpServletRequest request){
        User user = userService.verify(username,password);
        request.getSession().setAttribute("user",user);
        return new JsonResult<User>(OK,user);
    }


    /**
     * 改密码
     * @return
     */
    @RequestMapping("changePassword")
    public JsonResult<Void> change(String oldPassword, String newPassword, HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        String username = user.getUsername();
        userService.updatePassword(id,username,oldPassword,newPassword);
        return new JsonResult<Void>(OK);
    }

    /**
     * 查询用户数据
     * @return
     */
    @RequestMapping("get_by_username")
    public JsonResult<User> change(HttpSession session){
        User user1 = (User) session.getAttribute("user");
        Integer id = user1.getUid();
        String username = user1.getUsername();
        User user = userService.findByUsername(username);
    return new JsonResult<>(OK,user);
    }

    /**
     * 更新用户数据
     * @return
     */
    @RequestMapping("change_info")
    public JsonResult<Void> upData(User user,HttpSession session){
        User user1 = (User) session.getAttribute("user");
        Integer id = user1.getUid();
        String username = user1.getUsername();
        user.setUsername(username);
        user.setUid(id);
        userService.updateUserInformation(user);
        return new JsonResult<>(OK);
    }

    /**
     * 更新用户头像数据:get请求提交数据在2kb左右，所以使用post，
     * post 理论上讲是没有大小限制的，HTTP 协议规范也没有进行大小限制，但实际上
     * post 所能传递的数据量大小取决于服务器的设置和内存大小。
     * @return
     */
    //设置文件上传的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 *1024;
    //设置限制文件上传的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping("change_avatar")
    public JsonResult<String> updataAvatar(@RequestParam ("file") MultipartFile file, HttpSession session){
        //MultipartFile file:接收图片文件，可以获取任何文件类型的数据
        //判断文件是否为空
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空，请重新上传");
        }
        if (file.getSize() > AVATAR_MAX_SIZE){
            throw  new FileSizeException("文件太大");
        }
        if (!AVATAR_TYPE.contains((file.getContentType()))){
            throw new FileTypeException("文件类型不正确");
        }
        //上传的文件.../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //file对象指向这个路径，之后判断file是否存在
        File dir = new File(parent);
        if (!dir.exists()){//检查目录是否存在
            dir.mkdirs();//创建目录
        }
        //获取文件的名称，UUID工具生成一个新的字符串作为文件名
        String fileName = file.getOriginalFilename();//返回的是一个文件名，不包含目录结构
        System.out.println(fileName);
        //保存文件后缀
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        //拼接文件名
        fileName = (UUID.randomUUID().toString().toUpperCase())+suffix;
        System.out.println(fileName);
        File dest = new File(dir,fileName);//此时是一个空文件
        //将参数携带的数据写入到空文件中，dest
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw  new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw  new FileUploadIOException("写入文件异常");
        }

        User user = (User) session.getAttribute("user");
        Integer id = user.getUid();
        String username = user.getUsername();
        String avatar = "/upload/"+fileName;
        user.setUsername(username);
        user.setUid(id);
        user.setAvatar(avatar);
        userService.updateUserAvatar(user);
        //返回用户头像的路径给前端页面，将来用于头像的展示使用
        return new JsonResult<>(OK,avatar);
    }
}
