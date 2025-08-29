package dao;

import java.sql.*;
import java.time.Instant;
import utils.DBConnection;

public class OtpDaoImpl implements OtpDao {

    @Override
    public boolean save(int userId, String code, Instant expiresAt) {
        String sql = "INSERT INTO dbo.PasswordResetOtp(userId,code,expiresAt) VALUES(?,?,?)";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, code);
            ps.setTimestamp(3, Timestamp.from(expiresAt));
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean isValid(int userId, String code) {
        String sql = "SELECT TOP 1 1 FROM dbo.PasswordResetOtp " +
                     "WHERE userId=? AND code=? AND isUsed=0 AND expiresAt>SYSUTCDATETIME() " +
                     "ORDER BY id DESC";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, code);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void markUsed(int userId, String code) {
        String sql = "UPDATE dbo.PasswordResetOtp SET isUsed=1 WHERE userId=? AND code=?";
        try (Connection c = new DBConnection().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, code);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
