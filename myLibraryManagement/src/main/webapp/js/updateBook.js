document.getElementById("submitBtn").addEventListener("click", async function () {
    const bookId = document.getElementById("bookId").value;
    const stock = document.getElementById("stock").value;
    const messageElement = document.getElementById("message");
    console.log(bookId+"  "+stock);

    try {
        const response = await fetch("updateBook", {
            method: "PUT",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                bookId: bookId,
                stock: stock
            })
        });

        if (!response.ok) {
            const errorResult = await response.json();
            messageElement.textContent = errorResult.message;
            throw new Error(errorResult.message || "Network response was not ok.");
        }

        const result = await response.json();
        alert(result.message);
    } catch (error) {
        messageElement.textContent = "An error occurred: " + error.message;
    }
});

document.getElementById("backBtn").addEventListener("click", function () {
    window.history.back();
});
