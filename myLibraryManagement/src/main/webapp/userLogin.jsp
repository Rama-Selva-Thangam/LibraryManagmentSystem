<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ADMIN</title>
    <link rel="stylesheet" href="styles/loginPage.css" />
  </head>
  <body>
    <div id="container">
      <form id="userForm">
        <label for="userName">USER ID :</label>
        <input
          type="text"
          id="userName"
          name="userName"
          placeholder="ENTER USER ID"
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
      <p id="message"></p>
    </div>
    <script src="js/userLogin.js"></script>
  </body>
</html>
