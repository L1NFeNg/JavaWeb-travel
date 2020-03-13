package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 完成方法的分发
        // 获取请求的路径
        String uri = req.getRequestURI(); // /user/add
//        System.out.println("请求的路径是" + uri);
        // 获取方法的名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println(methodName);
        // 获取方法的对象
        try {
            // getDeclaredMethod：忽略访问权限来获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 执行方法
//
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
