<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <title>Xác nhận mã OTP</title>
  <style>
    body{font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial,sans-serif;background:#f7f7fb;margin:0;min-height:100vh;display:grid;place-items:center}
    .card{width:100%;max-width:460px;background:#fff;border-radius:16px;box-shadow:0 12px 26px rgba(0,0,0,.08);padding:28px}
    h1{margin:0 0 8px;font-size:24px}
    p{margin:0 0 16px;color:#6b7280}
    label{display:block;font-weight:600;margin:12px 0 6px}
    input[type=text]{width:100%;padding:12px 14px;border:1px solid #e5e7eb;border-radius:10px;font-size:16px}
    .btn{appearance:none;border:0;border-radius:10px;padding:12px 16px;font-weight:700;cursor:pointer;background:#2563eb;color:#fff}
    .row{display:flex;justify-content:space-between;align-items:center;margin-top:12px}
    a{color:#2563eb;text-decoration:none}
    a:hover{text-decoration:underline}
    .error{background:#fee2e2;color:#991b1b;border:1px solid #fecaca;padding:10px;border-radius:10px;margin:12px 0}
    .notice{background:#ecfdf5;color:#065f46;border:1px solid #a7f3d0;padding:10px;border-radius:10px;margin:12px 0}
  </style>
</head>
<body>
  <div class="card">
    <h1>Xác nhận mã OTP</h1>
    <p>OTP đã gửi tới: <strong><%= request.getAttribute("email") %></strong></p>

    <% if (request.getAttribute("error") != null) { %>
      <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <form method="post" action="<%=request.getContextPath()%>/verify" autocomplete="one-time-code">
      <input type="hidden" name="email" value="<%= request.getAttribute("email") %>" />
      <input type="hidden" name="userId" value="<%= request.getAttribute("userId") %>" />

      <label for="code">Nhập mã 6 số</label>
      <input
        id="code"
        name="code"
        type="text"
        inputmode="numeric"
        pattern="[0-9]{6}"
        maxlength="6"
        placeholder="123456"
        title="Vui lòng nhập đúng 6 chữ số"
        required
        autofocus
      />

      <div class="row" style="margin-top:16px">
        <button class="btn" type="submit">Xác nhận</button>
        <a href="<%=request.getContextPath()%>/forgot">Gửi lại mã</a>
      </div>
    </form>
  </div>

  <script>
    // Giữ chỉ số, tự loại bỏ ký tự ngoài số khi gõ/paste
    const code = document.getElementById('code');
    code.addEventListener('input', () => {
      code.value = code.value.replace(/\D/g, '').slice(0, 6);
    });
  </script>
</body>
</html>
