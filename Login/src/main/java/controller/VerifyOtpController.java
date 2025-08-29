package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.OtpDao;
import dao.OtpDaoImpl;

@WebServlet("/verify")
public class VerifyOtpController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final OtpDao otpDao = new OtpDaoImpl();

    // Nếu ai đó mở /verify trực tiếp thì đưa về /forgot
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/forgot");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String email   = req.getParameter("email");
        String userIdS = req.getParameter("userId");
        String codeRaw = req.getParameter("code");

        // Làm sạch: chỉ giữ chữ số, cắt còn 6 ký tự
        String code = (codeRaw == null ? "" : codeRaw.replaceAll("\\D", ""));
        if (email == null || userIdS == null || email.isBlank() || userIdS.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/forgot");
            return;
        }
        if (code.length() != 6) {
            req.setAttribute("email", email);
            req.setAttribute("userId", userIdS);
            req.setAttribute("error", "Mã OTP phải gồm đúng 6 chữ số.");
            req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdS);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/forgot");
            return;
        }

        if (otpDao.isValid(userId, code)) {
            // Đánh dấu đã dùng & cho phép vào bước đặt mật khẩu
            otpDao.markUsed(userId, code);
            HttpSession s = req.getSession(true);
            s.setAttribute("otpVerifiedUserId", userId);

            req.setAttribute("email", email);
            req.setAttribute("userId", userId);
            req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
        } else {
            req.setAttribute("email", email);
            req.setAttribute("userId", userId);
            req.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn.");
            req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
        }
    }
}
