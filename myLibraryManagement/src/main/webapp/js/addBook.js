document.getElementById('submitBtn').addEventListener('click', async () => {
    const formData = new FormData(document.getElementById('addBookForm'));
    const responseMessage = document.getElementById('responseMessage');

    try {
        const response = await fetch('addBook', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const result = await response.json();
            responseMessage.textContent = 'Book added successfully!';
            document.getElementById('addBookForm').reset();
        } else {
            responseMessage.textContent = 'Failed to add book. Please try again.';
        }
    } catch (error) {
        console.error('Error:', error);
        responseMessage.textContent = 'An error occurred. Please try again later.';
    }
});

document.getElementById('backBtn').addEventListener('click', () => {
    window.history.back();
});
