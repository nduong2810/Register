package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = this.get(username);
        if (user != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    // NEW
    @Override
    public String register(User u) {
        if (u == null) return "Dữ liệu không hợp lệ.";
        // validate tối thiểu
        if (isBlank(u.getEmail()) || isBlank(u.getUserName()) ||
            isBlank(u.getFullName()) || isBlank(u.getPassWord())) {
            return "Vui lòng nhập đầy đủ thông tin bắt buộc.";
        }
        if (!u.getEmail().matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            return "Email không hợp lệ.";
        }
        if (u.getPassWord().length() < 6) {
            return "Mật khẩu phải từ 6 ký tự.";
        }
        if (userDao.existsByEmail(u.getEmail())) {
            return "Email đã được sử dụng.";
        }
        if (userDao.existsByUsername(u.getUserName())) {
            return "Tài khoản đã tồn tại.";
        }
        // role mặc định
        if (u.getRoleid() == 0) u.setRoleid(3);
        int rows = userDao.create(u);
        return rows == 1 ? null : "Không thể tạo tài khoản. Vui lòng thử lại.";
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
