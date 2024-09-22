<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Update Book</title>
    <link rel="stylesheet" href="styles/register.css" />
  </head>
  <body>
    <div class="form-container">
      <h2>Update Book</h2>

      <form id="updateBookForm">
        <div class="form-group">
          <label for="bookId">Enter Book ID to Change Stock :</label>
          <input type="text" id="bookId" name="bookId" required />
        </div>
        <div class="form-group">
          <label for="stock">Enter the Stock :</label>
          <input type="number" id="stock" name="stock" required />
        </div>

        <div class="submit-btn">
          <button type="button" id="submitBtn">Update</button>
          <button type="button" id="backBtn">Go Back</button>
        </div>
      </form>

      <p id="message"></p>
    </div>
    <script src="js/changeBook.js"></script>
  </body>
</html>
