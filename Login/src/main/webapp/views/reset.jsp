<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi"><head><meta charset="UTF-8"><title>Đặt lại mật khẩu</title>
<style>
  body{font-family:system-ui;display:grid;place-items:center;min-height:100vh;background:#f7f7fb;margin:0}
  .card{width:100%;max-width:420px;background:#fff;border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.08);padding:28px}
  label{display:block;font-weight:600;margin:12px 0 6px}
  input[type=password]{width:100%;padding:12px;border:1px solid #e5e7eb;border-radius:10px}
  .btn{margin-top:12px;padding:12px 16px;border:0;border-radius:10px;background:#2563eb;color:#fff;font-weight:700;cursor:pointer}
  .error{background:#fee2e2;color:#991b1b;border:1px solid #fecaca;padding:10px;border-radius:10px;margin:12px 0}
</style></head>
<body>
<div class="card">
  <h2>Đặt lại mật khẩu</h2>
  <p>Email: <strong><%= request.getAttribute("email") %></strong></p>

  <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
  <% } %>

  <form method="post" action="<%=request.getContextPath()%>/reset">
    <input type="hidden" name="userId" value="<%= request.getAttribute("userId") %>"/>
    <input type="hidden" name="email"  value="<%= request.getAttribute("email") %>"/>
    <label for="pw1">Mật khẩu mới</label>
    <input id="pw1" name="password" type="password" required />
    <label for="pw2">Xác nhận mật khẩu mới</label>
    <input id="pw2" name="confirm" type="password" required />
    <button class="btn" type="submit">Cập nhật mật khẩu</button>
  </form>
</div>
</body></html>
