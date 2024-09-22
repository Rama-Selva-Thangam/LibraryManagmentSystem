<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("userLogin.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><%= username %></title>
    <link rel="stylesheet" href="styles/process.css" />
    
  </head>
  <body>
    <div id="container">
      <h1>Welcome, <%= username %>!</h1>

      <div class="button-container">
        <button id="borrowBook">Take a Book</button>
        <button id="searchBook">Search a Book</button>
        <button id="returnBook">Return a Book</button>
        <button id="viewBorrowBooks">View Books Taken</button>
      </div>

      <div class="go-back">
        <button id="goBackBtn">Go Back</button>
      </div>
    </div>
    <script src="js/userProcess.js"></script>
  </body>
</html>
