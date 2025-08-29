<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Đăng nhập</title>
  <style>
    :root{--bg:#f7f7fb;--card:#fff;--text:#111;--muted:#6b7280;--primary:#2563eb;}
    *{box-sizing:border-box} body{margin:0;background:var(--bg);font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;color:var(--text)}
    .wrap{min-height:100dvh;display:grid;place-items:center;padding:24px}
    .card{width:100%;max-width:420px;background:var(--card);border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.08);padding:28px}
    h1{margin:0 0 8px;font-size:24px}
    p.lead{margin:0 0 20px;color:var(--muted)}
    label{display:block;font-weight:600;margin:14px 0 6px}
    input[type=text],input[type=password],input[type=email]{width:100%;padding:12px 14px;border:1px solid #e5e7eb;border-radius:10px;font-size:14px}
    .row{display:flex;justify-content:space-between;align-items:center;margin:12px 0}
    .actions{display:flex;gap:10px;margin-top:16px}
    .btn{appearance:none;border:0;border-radius:10px;padding:12px 14px;cursor:pointer;font-weight:600}
    .btn-primary{background:var(--primary);color:#fff;flex:1}
    .btn-ghost{background:transparent;color:var(--primary)}
    .help{display:flex;justify-content:space-between;align-items:center;margin-top:8px}
    a{color:var(--primary);text-decoration:none}
    a:hover{text-decoration:underline}
    .error{background:#fee2e2;color:#991b1b;border:1px solid #fecaca;padding:10px;border-radius:10px;margin-bottom:12px;font-size:14px}
  </style>
</head>
<body>
<div class="wrap">
  <div class="card">
    <h1>Đăng nhập</h1>
    <p class="lead">Nhập tài khoản và mật khẩu của bạn</p>

    <%-- Hiện thông báo lỗi nếu có (đặt request.setAttribute("error", "...") trong Controller) --%>
    <%
      String error = (String) request.getAttribute("error");
      if (error != null && !error.isEmpty()) {
    %>
      <div class="error"><%= error %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/login" method="post" autocomplete="on">
      <label for="username">Tài khoản</label>
      <input id="username" name="username" type="text" placeholder="Nhập tài khoản" required />

      <label for="password">Mật khẩu</label>
      <input id="password" name="password" type="password" placeholder="••••••••" required />

      <div class="row">
        <label style="display:flex;align-items:center;gap:8px;font-weight:500">
          <input type="checkbox" name="remember" value="true" />
          Ghi nhớ đăng nhập
        </label>
        <a href="<%=request.getContextPath()%>/forgot">Quên mật khẩu?</a>
      </div>

      <div class="actions">
        <button class="btn btn-primary" type="submit">Đăng nhập</button>
        <a class="btn btn-ghost" href="<%=request.getContextPath()%>/register">Tạo tài khoản</a>
      </div>
    </form>
  </div>
</div>
</body>
</html>
