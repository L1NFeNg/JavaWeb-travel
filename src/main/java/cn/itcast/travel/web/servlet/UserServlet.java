package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    // 声明UserService的业务对象
    UserService service = new UserServiceImpl();

    /**
     * 用户注册
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 验证码的校验
        String check = request.getParameter("check");
        // 从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        // 删除session，保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        // 不区分大小写比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            // 验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        // 1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        // 2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3.调用service的regist接受user对象完成注册操作
        boolean flag = service.regist(user);
        //ResultInfo：一个处理服务端返回给客户端信息和数据的实体类，在domain包中
        ResultInfo info = new ResultInfo();
        // 4.响应结果
        if (flag) {
            // 注册成功
            info.setFlag(true);
        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败，用户名已存在！");
        }
        // 将info对象序列化为json:jackson方法
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        // 将json数据写回客户端
        // 设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户激活
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            // 调用service完成激活
            boolean flag = service.active(code);

            // 判断标记
            String msg = null;
            if (flag) {
                // 激活成功
                msg = "激活成功,请<a href='login.html'>登录</a>";
            } else {
                // 激活失败
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 用户登录
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        // 2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        // 3.调用Service查询
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        // 4.判断用户对象是否为null
        if (u == null) {
            // 用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        // 5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            // 用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登录邮箱激活");
        }
        // 6.登录成功判断
        if (u != null && "Y".equals(u.getStatus())) {
            // 登录成功
            info.setFlag(true);
            request.getSession().setAttribute("user", u);
        }
        // 响应数据，将序列化的json写回客户端
        writeValue(info, response);
    }

    /**
     * 查找一个用户
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        // Test:System.out.println(user.toString());
        // 将user写回客户端
        writeValue(user, response);
    }

    /**
     * 用户退出
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 销毁session
        request.getSession().invalidate();
        // 跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
