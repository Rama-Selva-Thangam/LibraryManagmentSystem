<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ADMIN</title>
    <link rel="stylesheet" href="styles/loginPage.css" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet" />
  </head>
  <body>
    <div id="container">
      <form id="adminForm" autocomplete="off"> 
        <label for="userName">ADMIN ID :</label>
        <input
          type="text"
          id="userName"
          name="userName"
          placeholder="Enter Admin ID"
          required
        />
        <label for="password">PASSWORD :</label>
        <input
          type="password"
          id="password"
          name="password"
          placeholder="Enter password"
          required
        />
        <button id="submitBtn" type="button">SUBMIT</button>
        <button id="backBtn" type="button">GO BACK</button>
      </form>
    </div>
    <script src="js/adminLogin.js"></script>
  </body>
</html>
