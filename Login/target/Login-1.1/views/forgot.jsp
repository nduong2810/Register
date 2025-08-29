<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Quên mật khẩu</title></head>
<body style="font-family:system-ui;padding:24px">
  <h1>Quên mật khẩu</h1>
  <p>Nhập email để nhận hướng dẫn đặt lại mật khẩu.</p>
  <form action="<%=request.getContextPath()%>/forgot" method="post">
    <label for="email">Email</label><br/>
    <input id="email" name="email" type="email" required style="padding:8px;width:320px"><br/><br/>
    <button type="submit" style="padding:8px 12px">Gửi</button>
    <a href="<%=request.getContextPath()%>/login" style="margin-left:8px">Quay lại đăng nhập</a>
  </form>
</body>
</html>
