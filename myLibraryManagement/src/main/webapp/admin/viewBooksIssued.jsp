<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.text.SimpleDateFormat" %>
<%@ page import="librarymanagment.dto.Book" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Books Issued</title>
    <link rel="stylesheet" href="../styles/viewBook.css">
</head>
<body>

    <h1>Books Issued</h1>
    
    <table>
        <thead>
            <tr>
                <th>Transaction ID</th>
                <th>User Name</th>
                <th>Book Name</th>
                <th>Author Name</th>
                <th>Date of Issue</th>
                <th>Date of Return</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Book> issuedBooks = (List<Book>) session.getAttribute("issuedBooks");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                if (issuedBooks != null && !issuedBooks.isEmpty()) {
                    for (Book book : issuedBooks) {
                        String formattedIssueDate = sdf.format(new java.util.Date(book.getDateOfIssue()));

                        // Updated date of return check and formatting
                        String formattedReturnDate = (book.getDateOfReturn() > 0) 
                                                      ? sdf.format(new java.util.Date(book.getDateOfReturn())) 
                                                      : "Not returned";
            %>
                        <tr>
                            <td><%= book.getTransaction_id() %></td>
                            <td><%= book.getUserName() %></td>
                            <td><%= book.getBookName() %></td>
                            <td><%= book.getAuthorName() %></td>
                            <td><%= formattedIssueDate %></td>
                            <td><%= formattedReturnDate %></td>
                            <td><%= book.getStatus() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="7">No books issued.</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <p id="message"></p>
    <script src="../js/adminProcess.js"></script>
</body>
</html>
