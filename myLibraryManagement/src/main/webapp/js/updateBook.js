document.getElementById("submitBtn").addEventListener("click", async function () {
    const bookId = document.getElementById("bookId").value.trim();
    const stock = document.getElementById("stock").value.trim();
    const messageElement = document.getElementById("message");

    messageElement.textContent = "";

    if (!bookId) {
        messageElement.textContent = "Book ID cannot be empty.";
        return;
    }

    const idPattern = /^[a-zA-Z0-9_]+$/; 
    if (!idPattern.test(bookId)) {
        messageElement.textContent = "Book ID must contain only alphanumeric characters and underscores.";
        return;
    }

    if (!stock) {
        messageElement.textContent = "Stock cannot be empty.";
        return;
    }

    const stockNumber = parseInt(stock, 10);
    if (isNaN(stockNumber) || stockNumber < 0) {
        messageElement.textContent = "Stock must be a non-negative number.";
        return;
    }

    try {
        const response = await fetch("updateBook", {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                bookId: bookId,
                stock: stockNumber 
            })
        });

        if (!response.ok) {
            const errorResult = await response.json();
            messageElement.textContent = errorResult.message;
            throw new Error(errorResult.message || "Network response was not ok.");
        }

        const result = await response.json();
        document.getElementById("updateBookForm").reset();
        alert(result.message);
    } catch (error) {
        messageElement.textContent = "An error occurred: " + error.message;
    }
});

document.getElementById("backBtn").addEventListener("click", function () {
    window.history.back();
});
