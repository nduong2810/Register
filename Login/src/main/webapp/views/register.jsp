<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Tạo tài khoản</title>
  <style>
    :root{--bg:#f7f7fb;--card:#fff;--muted:#6b7280;--primary:#2563eb}
    *{box-sizing:border-box} body{margin:0;background:var(--bg);font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif}
    .wrap{min-height:100vh;display:grid;place-items:center;padding:24px}
    .card{width:100%;max-width:520px;background:var(--card);border-radius:16px;box-shadow:0 12px 26px rgba(0,0,0,.08);padding:28px}
    h1{margin:0 0 6px} p.lead{margin:0 0 18px;color:var(--muted)}
    label{display:block;font-weight:600;margin:12px 0 6px}
    input{width:100%;padding:12px 14px;border:1px solid #e5e7eb;border-radius:10px;font-size:14px}
    .grid{display:grid;grid-template-columns:1fr 1fr;gap:12px}
    .btn{margin-top:16px;padding:12px 16px;border:0;border-radius:10px;background:var(--primary);color:#fff;font-weight:700;cursor:pointer}
    .error{background:#fee2e2;color:#991b1b;border:1px solid #fecaca;padding:10px;border-radius:10px;margin:12px 0}
    .ok{background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;padding:10px;border-radius:10px;margin:12px 0}
    a{color:var(--primary);text-decoration:none} a:hover{text-decoration:underline}
  </style>
</head>
<body>
<div class="wrap">
  <div class="card">
    <h1>Tạo tài khoản</h1>
    <p class="lead">Điền thông tin để tạo tài khoản mới.</p>

    <% if (request.getAttribute("error") != null) { %>
      <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("alert") != null) { %>
      <div class="ok"><%= request.getAttribute("alert") %></div>
    <% } %>

    <form method="post" action="<%=request.getContextPath()%>/register">
      <label for="email">Email *</label>
      <input id="email" name="email" type="email" required
             value="<%= request.getAttribute("email")!=null?request.getAttribute("email"):"" %>" />

      <div class="grid">
        <div>
          <label for="username">Tài khoản *</label>
          <input id="username" name="username" required
                 value="<%= request.getAttribute("username")!=null?request.getAttribute("username"):"" %>" />
        </div>
        <div>
          <label for="fullname">Họ và tên *</label>
          <input id="fullname" name="fullname" required
                 value="<%= request.getAttribute("fullname")!=null?request.getAttribute("fullname"):"" %>" />
        </div>
      </div>

      <div class="grid">
        <div>
          <label for="password">Mật khẩu *</label>
          <input id="password" name="password" type="password" minlength="6" required />
        </div>
        <div>
          <label for="confirm">Xác nhận mật khẩu *</label>
          <input id="confirm" name="confirm" type="password" minlength="6" required />
        </div>
      </div>

      <label for="phone">Số điện thoại</label>
      <input id="phone" name="phone"
             value="<%= request.getAttribute("phone")!=null?request.getAttribute("phone"):"" %>" />

      <button class="btn" type="submit">Tạo tài khoản</button>
      <p style="margin-top:12px">Đã có tài khoản? <a href="<%=request.getContextPath()%>/login">Đăng nhập</a></p>
    </form>
  </div>
</div>
</body>
</html>
