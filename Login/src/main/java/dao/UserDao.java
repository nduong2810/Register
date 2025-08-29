package dao;

import model.User;

public interface UserDao {
    User get(String username);
    User getByEmail(String email);          
    boolean updatePassword(int userId, String newPassword);
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    int create(User user);   // trả về số rows (1 nếu thành công)
}
