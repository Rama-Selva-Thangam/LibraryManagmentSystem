
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
document.getElementById('viewAllBooksBtn').addEventListener('click', async function () {
    try {
        const response = await fetch('viewBooks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ filter: "all" })
        });

        if (!response.ok) {
            throw new Error('Failed to fetch books');
        }
        window.location.href = "viewBook.jsp";


    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching books.');
    }
});


document.getElementById('viewAllUsersBtn').addEventListener('click', async function () {
    try {
        const response = await fetch('viewUsers', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch books');
        }
        window.location.href = "viewUsers.jsp";


    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching books.');
    }
});
document.getElementById('viewBooksIssued').addEventListener('click', function () {
    window.location.href = 'updateBook.jsp';
});
document.getElementById('goBackBtn').addEventListener('click', function () {
    window.history.back();
});
