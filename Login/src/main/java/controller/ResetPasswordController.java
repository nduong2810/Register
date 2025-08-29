package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.UserDao;
import dao.UserDaoImpl;

@WebServlet("/reset")
public class ResetPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDao userDao = new UserDaoImpl();

    // Không cho vào trực tiếp bằng GET (phải đi từ forgot -> forward)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/forgot");
    }

    // Nhận userId + password mới, xác nhận, sau đó cập nhật DB
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String userIdStr = req.getParameter("userId");
        String email = req.getParameter("email"); // để hiển thị lại khi lỗi
        String pw1 = req.getParameter("password");
        String pw2 = req.getParameter("confirm");

        if (userIdStr == null || userIdStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/forgot");
            return;
        }

        if (pw1 == null || pw2 == null || pw1.isBlank() || !pw1.equals(pw2)) {
            req.setAttribute("error", "Mật khẩu không hợp lệ hoặc không khớp.");
            req.setAttribute("userId", userIdStr);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
            return;
        }

        int userId = Integer.parseInt(userIdStr);
        boolean ok = userDao.updatePassword(userId, pw1);
        if (ok) {
            req.setAttribute("alert", "Đặt lại mật khẩu thành công. Hãy đăng nhập.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại.");
            req.setAttribute("userId", userIdStr);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
        }
    }
}
