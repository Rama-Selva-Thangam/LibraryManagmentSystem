document.getElementById('submitBtn').addEventListener('click', async () => {
    const responseMessage = document.getElementById('message');

    const bookData = {
        bookId: document.getElementById('bookId').value,
        bookName: document.getElementById('bookName').value,
        authorName: document.getElementById('authorName').value,
        edition: document.getElementById('edition').value,
        dateOfPublication: document.getElementById('dateOfPublication').value,
        stock: document.getElementById('stock').value
    };

    try {
        const response = await fetch('addBook', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookData)
        });

        if (response.ok) {
            console.log(response);
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
