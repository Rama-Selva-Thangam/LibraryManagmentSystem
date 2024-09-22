<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="librarymanagment.dto.Book" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>

<html>
<head>
    <title>View Books</title>
    <link rel="stylesheet" type="text/css" href="styles/vieBook.css">
</head>
<body>
    <h1>Book List</h1>

    <%
        ArrayList<Book> books = (ArrayList<Book>) request.getAttribute("books");
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>
        <div class="error"><%= error %></div>
    <%
        } else if (books != null && !books.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    %>
        <table border="1">
            <tr>
                <th>Book ID</th>
                <th>Book Name</th>
                <th>Author Name</th>
                <th>Edition</th>
                <th>Date of Publication</th>
                <th>Stock</th>
            </tr>
            <%
                for (Book book : books) {
            %>
                <tr>
                    <td><%= book.getBookId() %></td>
                    <td><%= book.getBookName() %></td>
                    <td><%= book.getAuthorName() %></td>
                    <td><%= book.getEdition() %></td>
                    <td><%= sdf.format(new Date(book.getDateOfPublication())) %></td>
                    <td><%= book.getStock() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <div>No books available.</div>
    <%
        }
    %>
    <button id="backBtn" onclick="window.history.back()">Go Back</button>
</body>
</html>

Make it Cleaner


I want do the java operations in the above and html codes are in below neatly