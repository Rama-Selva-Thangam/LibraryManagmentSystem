<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page session="true" %> 
<% String username = (String) session.getAttribute("userLoggedIn").getUserName(); 
  if (username == null) {
      response.sendRedirect("userLogin.jsp"); return;
     } 
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Welcome - <%= username %></title>
    <link rel="stylesheet" href="styles/process.css" />
  </head>
  <body>
    <div id="container">
      <h1>Welcome, <%= username %>!</h1>

      <div class="button-container">
        <button id="borrowBook">Borrow a Book</button>
        <button id="searchBook">Search for a Book</button>
        <button id="returnBook">Return a Book</button>
        <button id="viewBorrowedBooks">View Borrowed Books</button>
      </div>

      <div class="go-back">
        <button id="goBackBtn">Go Back</button>
      </div>
    </div>
    <script src="js/userProcess.js"></script>
  </body>
</html>
