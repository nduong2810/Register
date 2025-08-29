package utils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;


public class Mailer {
    // Đặt biến môi trường: MAIL_USER, MAIL_PASS (App Password 16 ký tự, KHÔNG khoảng trắng)
	// utils/Mailer.java  (chỉ phần đọc biến)
	private static final String USER = (System.getenv("MAIL_USER") != null)
	        ? System.getenv("MAIL_USER") : System.getProperty("MAIL_USER");

	private static final String APP_PASSWORD = (System.getenv("MAIL_PASS") != null)
	        ? System.getenv("MAIL_PASS") : System.getProperty("MAIL_PASS");


    private static Session buildSession() {
        if (USER == null || APP_PASSWORD == null) {
            throw new IllegalStateException("Thiếu MAIL_USER/MAIL_PASS (Gmail App Password).");
        }
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        p.put("mail.smtp.connectiontimeout", "10000");
        p.put("mail.smtp.timeout", "10000");
        return Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, APP_PASSWORD);
            }
        });
    }

    public static void send(String to, String subject, String html) throws MessagingException {
        Session s = buildSession();
        s.setDebug(true);
        MimeMessage msg = new MimeMessage(s);
        msg.setFrom(new InternetAddress(USER));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(html, "text/html; charset=UTF-8");
        Transport.send(msg);
    }
}
