<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Remove User</title>
    <link rel="stylesheet" href="styles/register.css" />
  </head>
  <body>
    <div class="form-container">
      <h2>Remove Book</h2>

      <form id="removeBookForm">
        <div class="form-group">
          <label for="bookId">Enter Book ID to Remove :</label>
          <input type="text" id="bookId" name="bookId" required />
        </div>

        <div class="submit-btn">
          <button type="button" id="submitBtn">REMOVE</button>
          <button type="button" id="backBtn">GO BACK</button>
        </div>
      </form>

      <p id="message"></p>
    </div>
    <script src="js/removeBook.js"></script>
  </body>
</html>
