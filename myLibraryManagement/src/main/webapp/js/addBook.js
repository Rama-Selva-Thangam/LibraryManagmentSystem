document.getElementById('submitBtn').addEventListener('click', async () => {
    const responseMessage = document.getElementById('message');

    responseMessage.textContent = '';

    const bookData = {
        bookId: document.getElementById('bookId').value.trim(),
        bookName: document.getElementById('bookName').value.trim(),
        authorName: document.getElementById('authorName').value.trim(),
        edition: document.getElementById('edition').value.trim(),
        dateOfPublication: document.getElementById('dateOfPublication').value.trim(),
        stock: document.getElementById('stock').value.trim()
    };

    if (!bookData.bookId || !bookData.bookName || !bookData.authorName || !bookData.edition || !bookData.dateOfPublication || !bookData.stock) {
        responseMessage.textContent = 'Please fill out all fields.';
        return;
    }

    const idPattern = /^[a-zA-Z0-9_]+$/;
    if (!idPattern.test(bookData.bookId)) {
        responseMessage.textContent = 'Book ID must contain only alphanumeric characters and underscores.';
        return;
    }

    const namePattern = /^[a-zA-Z0-9\s]+$/;
    if (!namePattern.test(bookData.bookName)) {
        responseMessage.textContent = 'Book Name can only contain letters, numbers, and spaces.';
        return;
    }

    if (!namePattern.test(bookData.authorName)) {
        responseMessage.textContent = 'Author Name can only contain letters, numbers, and spaces.';
        return;
    }

    if (bookData.edition.trim().length === 0) {
        responseMessage.textContent = 'Edition cannot be empty.';
        return;
    }

    const today = new Date();
    const publicationDate = new Date(bookData.dateOfPublication);
    if (publicationDate > today) {
        responseMessage.textContent = 'Date of Publication cannot be in the future.';
        return;
    }

    if (bookData.stock < 0) {
        responseMessage.textContent = 'Stock cannot be negative.';
        return;
    }

    try {
        const response = await fetch('/myLibraryManagement/admin/addBook', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookData)
        });

        if (response.ok) {
            const result = await response.json();
            responseMessage.textContent = result.message;
            document.getElementById('addBookForm').reset();
        } else {
            const errorResult = await response.json();
            responseMessage.textContent = errorResult.message || 'Failed to add book. Please try again.';
        }
    } catch (error) {
        console.error('Error:', error);
        responseMessage.textContent = 'An error occurred. Please try again later.';
    }
});

document.getElementById('backBtn').addEventListener('click', () => {
    window.history.back();
});
