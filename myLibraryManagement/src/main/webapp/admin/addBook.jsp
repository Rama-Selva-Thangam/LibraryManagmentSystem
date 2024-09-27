<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Add New Book</title>
    <link rel="stylesheet" type="text/css" href="../styles/addBook.css" />
  </head>
  <body>
    <div class="form-container">
      <h2>Add New Book</h2>
      <form id="addBookForm">
        <div class="form-group">
          <label for="bookId">Book ID</label>
          <input type="text" id="bookId" name="bookId" required />
        </div>
        <div class="form-group">
          <label for="bookName">Book Name</label>
          <input type="text" id="bookName" name="bookName" required />
        </div>
        <div class="form-group">
          <label for="authorName">Author Name</label>
          <input type="text" id="authorName" name="authorName" required />
        </div>
        <div class="form-group">
          <label for="edition">Edition</label>
          <input type="text" id="edition" name="edition" required />
        </div>
        <div class="form-group">
          <label for="dateOfPublication">Date of Publication</label>
          <input
            type="date"
            id="dateOfPublication"
            name="dateOfPublication"
            required
          />
        </div>
        <div class="form-group">
          <label for="stock">Stock</label>
          <input type="number" id="stock" name="stock" required />
        </div>
        <div class="form-group">
          <button type="button" id="submitBtn">Add Book</button>
        </div>
      </form>
      <button type="button" id="backBtn">Back</button>
      <p id="message"></p>
     
    </div>
    <script src="../js/addBook.js"></script>
  </body>
</html>
