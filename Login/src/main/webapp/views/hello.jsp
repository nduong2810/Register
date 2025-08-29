<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Hello</title>
  <style>
    body{font-family:system-ui;display:grid;place-items:center;min-height:100vh;background:#f7f7fb;margin:0}
    .card{background:#fff;padding:24px 32px;border-radius:16px;box-shadow:0 10px 24px rgba(0,0,0,.08);min-width:320px;text-align:center}
    a.btn{display:inline-block;margin-top:12px;padding:10px 14px;border-radius:10px;background:#ef4444;color:#fff;text-decoration:none;font-weight:700}
  </style>
</head>
<body>
  <div class="card">
    <%
      model.User u = (model.User) session.getAttribute("account");
      String name = (u != null && u.getFullName()!=null && !u.getFullName().isEmpty()) ? u.getFullName() : "bạn";
    %>
    <h1>Hello <%= name %></h1>

    <a class="btn" href="<%=request.getContextPath()%>/logout">Đăng xuất</a>
  </div>
</body>
</html>
