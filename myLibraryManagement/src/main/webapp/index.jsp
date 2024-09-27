<%@page import="java.time.LocalTime" %>
<!DOCTYPE html>
<html lang="en">
  <% 
    String welcome; 
    int hour = LocalTime.now().getHour();
    if (hour < 12) { 
      welcome = "Good Morning!"; 
    } else if (hour < 18) { 
      welcome = "Good Afternoon!"; 
    } else { 
      welcome = "Good Evening!"; 
    } 
  %>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>INDEX</title>
    <link rel="stylesheet" href="styles/index.css" />
  </head>
  <body>
    <h1><%= welcome %></h1>

    <div class="button-container">
      <button onclick="window.location.href='admin/adminLogin.jsp'">Admin Login</button>
      <button onclick="window.location.href='user/userLogin.jsp'">User Login</button>
    </div>
  </body>
</html>
