
document.getElementById('borrowBook').addEventListener('click', function () {
    location.href = 'borrowBook.jsp';
});

document.getElementById('viewBorrowBooks').addEventListener('click', function () {
    location.href = 'viewBorrowBooks.jsp';
});

document.getElementById('goBackBtn').addEventListener('click', function () {
    window.history.back();
});
