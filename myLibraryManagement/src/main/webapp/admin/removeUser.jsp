<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Remove User</title>
    <link rel="stylesheet" href="../styles/register.css" />
  </head>
  <body>
    <div class="form-container">
      <h2>Remove User</h2>

      <form id="removeUserForm">
        <div class="form-group">
          <label for="userId">Enter User ID to Remove :</label>
          <input type="number" id="userId" name="userId" required />
        </div>

        <div class="submit-btn">
          <button type="button" id="submitBtn">REMOVE</button>
          <button type="button" id="backBtn">GO BACK</button>
        </div>
      </form>

      <p id="message"></p>
    </div>
    <script src="../js/removeUser.js"></script>
  </body>
</html>
