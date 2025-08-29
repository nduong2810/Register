package controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import dao.OtpDao;
import dao.OtpDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import utils.Mailer;

@WebServlet("/forgot")
public class ForgotController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserDao userDao = new UserDaoImpl();
    private final OtpDao otpDao = new OtpDaoImpl();
    private final SecureRandom rng = new SecureRandom();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
    }

    // Nhập email -> nếu tồn tại: gửi OTP & tới trang verify; nếu không: báo "Email không tồn tại"
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");

        if (email == null || email.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập email.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        User u = userDao.getByEmail(email);
        if (u == null) {
            req.setAttribute("error", "Email không tồn tại.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        // Tạo OTP 6 số, hết hạn 10 phút
        String code = String.format("%06d", rng.nextInt(1_000_000));
        otpDao.save(u.getId(), code, Instant.now().plus(10, ChronoUnit.MINUTES));

        try {
            String html = "<p>Mã xác nhận đặt lại mật khẩu của bạn là: <b>" + code + "</b></p>"
                        + "<p>Mã có hiệu lực trong 10 phút.</p>";
            Mailer.send(u.getEmail(), "Mã xác nhận đặt lại mật khẩu", html);
        } catch (Exception ex) {
            // Nếu gửi thất bại, thông báo lỗi chung (không lộ chi tiết)
        	ex.printStackTrace();
            req.setAttribute("error", "Không gửi được email. Vui lòng thử lại sau.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
            return;
        }

        // Sang trang nhập OTP
        req.setAttribute("email", u.getEmail());
        req.setAttribute("userId", u.getId());
        req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
    }
}
