package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import service.UserService;
import service.UserServiceImpl;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email    = trim(req.getParameter("email"));
        String username = trim(req.getParameter("username"));
        String fullName = trim(req.getParameter("fullname"));
        String password = trim(req.getParameter("password"));
        String confirm  = trim(req.getParameter("confirm"));
        String phone    = trim(req.getParameter("phone"));

        if (confirm == null || !confirm.equals(password)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            setBackValues(req, email, username, fullName, phone);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        User u = new User();
        u.setEmail(email);
        u.setUserName(username);
        u.setFullName(fullName);
        u.setPassWord(password);
        u.setPhone(phone);
        u.setRoleid(3); // user thường

        String err = userService.register(u);
        if (err == null) {
            req.setAttribute("alert", "Tạo tài khoản thành công! Hãy đăng nhập.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", err);
            setBackValues(req, email, username, fullName, phone);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }

    private static String trim(String s) { return s == null ? null : s.trim(); }
    private static void setBackValues(HttpServletRequest req, String email, String username, String fullName, String phone) {
        req.setAttribute("email", email);
        req.setAttribute("username", username);
        req.setAttribute("fullname", fullName);
        req.setAttribute("phone", phone);
    }
}
