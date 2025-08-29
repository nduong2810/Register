package dao;

import java.sql.*;
import model.User;
import utils.DBConnection;

public class UserDaoImpl implements UserDao {
    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    @Override
    public User get(String username) {
        String sql =
          "SELECT id, email, userName AS username, fullName AS fullname, " +
          "       passWord AS password, avatar, roleId AS roleid, phone, createdDate " +
          "FROM dbo.[User] WHERE userName = ?";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        String sql =
          "SELECT id, email, userName AS username, fullName AS fullname, " +
          "       passWord AS password, avatar, roleId AS roleid, phone, createdDate " +
          "FROM dbo.[User] WHERE email = ?";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) return map(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE dbo.[User] SET passWord=? WHERE id=?";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // NEW
    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT 1 FROM dbo.[User] WHERE userName = ?";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            try (ResultSet r = p.executeQuery()) { return r.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM dbo.[User] WHERE email = ?";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, email);
            try (ResultSet r = p.executeQuery()) { return r.next(); }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public int create(User u) {
        String sql = "INSERT INTO dbo.[User] " +
                     "(email, userName, fullName, passWord, roleId, phone, createdDate) " +
                     "VALUES (?,?,?,?,?,?,SYSUTCDATETIME())";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, u.getEmail());
            p.setString(2, u.getUserName());
            p.setString(3, u.getFullName());
            p.setString(4, u.getPassWord()); // hiện giữ plain text cho khớp login
            p.setInt(5, u.getRoleid());      // mặc định 3 (user thường)
            p.setString(6, u.getPhone());
            return p.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setEmail(rs.getString("email"));
        u.setUserName(rs.getString("username"));
        u.setFullName(rs.getString("fullname"));
        u.setPassWord(rs.getString("password"));
        u.setAvatar(rs.getString("avatar"));
        u.setRoleid(rs.getInt("roleid"));
        u.setPhone(rs.getString("phone"));
        u.setCreatedDate(rs.getDate("createdDate"));
        return u;
    }
}
