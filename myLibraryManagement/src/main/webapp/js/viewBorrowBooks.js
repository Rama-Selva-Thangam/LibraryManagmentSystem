document.addEventListener('DOMContentLoaded', async function () {
    try {
        const response = await fetch('viewBorrowBooks', {
            method: 'POST',
        });

        if (!response.ok) {
            throw new Error('Failed to fetch borrowed books.');
        }

        const books = await response.json();
        displayBooks(books);

    } catch (error) {
        console.error('Error:', error);
        document.getElementById('message').textContent = 'Error fetching borrowed books.';
    }
});

function displayBooks(books) {
    const table = document.getElementById('booksTable');
    table.innerHTML = '';

    if (books.length === 0) {
        table.innerHTML = '<tr><td colspan="7">No borrowed books.</td></tr>';
        return;
    }

    const headerRow = `
        <tr>
            <th>Book ID</th>
            <th>Book Name</th>
            <th>Author Name</th>
            <th>Date of Issue</th>
            <th>Date of Return</th>
            <th>Status</th>
            <th>Action</th>
        </tr>`;
    table.insertAdjacentHTML('beforeend', headerRow);

    books.forEach(book => {
        const dateOfIssue = new Date(book.dateOfIssue).toLocaleDateString();
        const dateOfReturn = book.dateOfReturn ? new Date(book.dateOfReturn).toLocaleDateString() : 'Not returned';
        const row = `
            <tr id="book-${book.bookId}">
                <td>${book.bookId}</td>
                <td>${book.bookName}</td>
                <td>${book.authorName}</td>
                <td>${dateOfIssue}</td>
                <td>${dateOfReturn}</td>
                <td>${book.status}</td>
                <td>${book.status === 'not returned' ? `<button class="return-btn" data-book-id="${book.bookId}">Return</button>` : ''}</td>
            </tr>`;
        table.insertAdjacentHTML('beforeend', row);
    });

    document.querySelectorAll('.return-btn').forEach(button => {
        button.addEventListener('click', async function () {
            const bookId = this.getAttribute('data-book-id');
            try {
                const response = await fetch('returnBook', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ bookId: bookId })  
                });

                const result = await response.json();
                if (response.ok) {
                    document.getElementById(`book-${bookId}`).querySelector('td:nth-child(6)').textContent = 'returned';
                    document.getElementById("message").textContent = result.message;
                    this.remove(); 
                } else {
                    document.getElementById("message").textContent = result.message;
                }
            } catch (error) {
                console.error('Error returning book:', error);
                document.getElementById("message").textContent = 'Error returning the book.';
            }
        });
    });
}
