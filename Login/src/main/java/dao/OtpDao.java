package dao;

import java.time.Instant;

public interface OtpDao {
    boolean save(int userId, String code, Instant expiresAt);
    boolean isValid(int userId, String code);
    void markUsed(int userId, String code);
}
