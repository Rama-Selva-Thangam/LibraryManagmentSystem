<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="librarymanagment.dto.User" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" type="text/css" href="styles/viewBook.css">
</head>
<body>
    <h1>User List</h1>

    <%
        ArrayList<User> users = (ArrayList<User>) session.getAttribute("users");
        String error = (String) session.getAttribute("error");

        if (error != null) {
    %>
        <div class="error"><%= error %></div>
    <%
        } else if (users != null && !users.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>User ID</th>
                <th>User Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Gender</th>
            </tr>
            <%
                for (User user : users) {
            %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td><%= user.getUserName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getAddress() %></td>
                    <td><%= user.getGender() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <div>No users available.</div>
    <%
        }
    %>
    <button id="backBtn" onclick="window.history.back()">Go Back</button>
</body>
</html>
