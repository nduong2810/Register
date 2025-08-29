package service;

import model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);

    // NEW: đăng ký, trả về message lỗi; null nếu thành công
    String register(User user);
}
