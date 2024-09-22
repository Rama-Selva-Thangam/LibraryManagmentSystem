
document.getElementById('addUserBtn').addEventListener('click', function () {
    window.location.href = 'registerUser.jsp';
});

document.getElementById('removeUserBtn').addEventListener('click', function () {
    window.location.href = 'removeUser.jsp';
});

document.getElementById('addBookBtn').addEventListener('click', function () {
    window.location.href = 'addBook.jsp';
});

document.getElementById('removeBookBtn').addEventListener('click', function () {
    window.location.href = 'removeBook.jsp';
});

document.getElementById('updateBookBtn').addEventListener('click', function () {
   window.location.href = 'updateBook.jsp';
});
document.getElementById('viewAllBooksBtn').addEventListener('click', function () {
    window.location.href = 'removeBook.jsp';
});

document.getElementById('viewAllUsersBtn').addEventListener('click', function () {
    window.location.href = 'updateBook.jsp';
});
document.getElementById('viewBooksIssued').addEventListener('click', function () {
    window.location.href = 'updateBook.jsp';
});
document.getElementById('goBackBtn').addEventListener('click', function () {
    window.history.back();
});
