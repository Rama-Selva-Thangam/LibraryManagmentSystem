<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page session="true" %>
<%@ page import="librarymanagment.dto.User" %>
<% 
  User user = (User) session.getAttribute("userLoggedIn"); 
  
  if (user == null || user.getUserName() == null) {
      response.sendRedirect("userLogin.jsp"); 
      return;
  } 
  
  String username = user.getUserName();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Borrowd - <%= username %></title>
    <link rel="stylesheet" href="styles/borrowBook.css">
</head>
<body>
   <div id="container">
    <table id="booksTable">

    </table>
    <p id="message"></p>
    <button id="backBtn" type="button">BACK BUTTON</button>
   </div> 
   <script src="js/viewBorrowBooks.js"></script>
</body>
</html>