package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Hủy session hiện tại (đăng xuất)
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();

        // Xóa cookie "username" (ghi nhớ đăng nhập)
        Cookie c = new Cookie("username", "");
        c.setMaxAge(0); // xóa ngay
        c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
        resp.addCookie(c);

        // (Tùy chọn) xóa cờ OTP nếu còn
        Cookie otp = new Cookie("otp", "");
        otp.setMaxAge(0);
        otp.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
        resp.addCookie(otp);

        // Quay về trang đăng nhập
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
