document.getElementById('submitBtn').addEventListener('click', async function () {
    const bookName = document.getElementById('bookName').value.trim();

    if (bookName === "") {
        alert("Please enter a book name.");
        return;
    }

    try {
        const response = await fetch('getBooks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ filter: bookName })
        });

        if (!response.ok) {
            const result = await response.json();
            throw new Error(result.message || 'Failed to fetch books');
        }

        const books = await response.json();
        displayBooks(books);

    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while fetching books: ' + error.message);
    }
});

function displayBooks(books) {
    const table = document.querySelector('#books-div table');
    table.innerHTML = '';
    const message = document.getElementById("message");

    if (books.length === 0) {
        const noBooksMessage = `
            <tr>
                <td colspan="7" style="text-align: center;">No books found.</td>
            </tr>`;
        table.insertAdjacentHTML('beforeend', noBooksMessage);
        document.getElementById('books-div').style.display = 'block';
        return;
    }

    const headerRow = `
        <tr>
            <th>Book ID</th>
            <th>Book Name</th>
            <th>Author Name</th>
            <th>Edition</th>
            <th>Date of Publication</th>
            <th>Stock</th>
            <th></th>
        </tr>`;
    table.insertAdjacentHTML('beforeend', headerRow);

    books.forEach(book => {
        const formattedDate = formatDate(book.dateOfPublication);
        const shortenedBookName = book.bookName.length > 20 ? book.bookName.substring(0, 20) + '...' : book.bookName;

        const row = `
            <tr id="book-${book.bookId}">
                <td>${book.bookId}</td>
                <td>${shortenedBookName}</td>
                <td>${book.authorName}</td>
                <td>${book.edition}</td>
                <td>${formattedDate}</td>
                <td class="stock">${book.stock}</td>
                <td><button class="borrow-btn" data-book-id="${book.bookId}">GET</button></td>
            </tr>`;
        table.insertAdjacentHTML('beforeend', row);
    });

    document.querySelectorAll('.borrow-btn').forEach(button => {
        button.addEventListener('click', async function () {
            const bookId = this.getAttribute('data-book-id');

            try {
                const response = await fetch('borrowBooks', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ bookId })
                });

                const result = await response.json();

                if (response.ok) {
                    const bookRow = document.getElementById(`book-${bookId}`);
                    const stockCell = bookRow.querySelector('.stock');
                    stockCell.textContent = parseInt(stockCell.textContent) - 1;
                    alert(result.message || 'Book borrowed successfully!');
                } else {
                    alert(result.message || 'Error borrowing the book.');
                }
                document.getElementById("message").textContent = result.message;
            } catch (error) {
                console.error('Error:', error);
                alert('An error occurred while borrowing the book: ' + error.message);
            }
        });
    });

    document.getElementById('books-div').style.display = 'block';
}

function formatDate(dateString) {
    const date = new Date(dateString);

    if (isNaN(date.getTime())) {
        return 'Invalid date';
    }

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
}

document.getElementById('backBtn').addEventListener('click', function () {
    window.history.back();
});