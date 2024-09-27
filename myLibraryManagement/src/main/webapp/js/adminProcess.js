
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
        const response = await fetch('/myLibraryManagement/admin/viewBooks', {
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
        const response = await fetch('/myLibraryManagement/admin/viewUsers', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch Users');
        }
        window.location.href = "viewUser.jsp";


    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching Users.');
    }
});
document.getElementById('viewBooksIssued').addEventListener('click', async () => {
    try {
        const response = await fetch('/myLibraryManagement/admin/viewBooksIssued', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Failed to fetch Users');
        }
        window.location.href = "viewBooksIssued.jsp";


    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching Users.');
    }
});
document.getElementById('logoutBtn').addEventListener('click', async () => {
    try {
        const response = await fetch('/myLibraryManagement/user/logoutUser', {
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
