<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Library Management System - Admin</title>
    <link rel="stylesheet" href="../styles/process.css" />
</head>
<body>
    <div id="container">
        <h1>Admin Panel - Library Management System</h1>

        <div class="button-container">
            <button id="addUserBtn">Add New User</button>
            <button id="removeUserBtn">Remove User</button>
            <button id="addBookBtn">Add New Book</button>
            <button id="removeBookBtn">Remove Book</button>
            <button id="updateBookBtn">Update Book</button>
            <button id="viewAllBooksBtn">View all Books</button>
            <button id="viewAllUsersBtn">View all Users</button>
            <button id="viewBooksIssued">View Books Issued</button>
        </div>

        <div class="go-back">
            <button id="goBackBtn">Go Back</button>
            <button id="logoutBtn">Log Out</button>
        </div>
    </div>
    <script src="../js/adminProcess.js"></script>
</body>
</html>
