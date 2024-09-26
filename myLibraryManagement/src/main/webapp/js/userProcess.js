
document.getElementById('borrowBook').addEventListener('click', function () {
    location.href = 'borrowBook.jsp';
});

document.getElementById('viewBorrowBooks').addEventListener('click', function () {
    location.href = 'viewBorrowBooks.jsp';
});

document.getElementById('logoutBtn').addEventListener('click', async function () {
    try {
        const response = await fetch('logoutUser', {
            method: 'POST',
        });

        if (response.ok) {
            window.location.href = 'index.jsp';
        } else {
            console.error('Failed to log out');
        }
    } catch (error) {
        console.error('Error during logout:', error);
    }
});


document.getElementById('goBackBtn').addEventListener('click', function () {
    window.history.back();
});
