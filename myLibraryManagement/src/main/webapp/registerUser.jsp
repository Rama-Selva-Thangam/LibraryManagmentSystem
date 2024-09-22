<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Register User</title>
    <link rel="stylesheet" href="styles/register.css" />
  </head>
  <body>
    <div class="form-container">
      <h2>Register User</h2>

      <form id="registerUserForm">
        <div class="form-group">
          <label for="userName">Enter Username :</label>
          <input type="text" id="userName" name="userName" required />
        </div>

        <div class="form-group">
          <label for="email">Enter Email :</label>
          <input type="email" id="email" name="email" required />
        </div>

        <div class="form-group">
          <label for="address">Enter Address :</label>
          <textarea id="address" name="address" rows="3" required></textarea>
        </div>

        <div class="form-group">
          <label>Select Gender :</label>
          <div class="gender-options">
            <label>
              <input type="radio" name="gender" value="male" required /> Male
            </label>
            <label>
              <input type="radio" name="gender" value="female" required />
              Female
            </label>
          </div>
        </div>

        <div class="form-group">
          <label for="password">Enter Password :</label>
          <input type="password" id="password" name="password" required />
        </div>

        <div class="submit-btn">
          <button type="button" id="submitBtn">REGISTER</button>
          <button type="button" id="backBtn">GO BACK</button>
        </div>
      </form>

      <p id="message"></p>
    </div>

    <script src="js/registerUser.js"></script>
  </body>
</html>
