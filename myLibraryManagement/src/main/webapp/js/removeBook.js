document.getElementById("submitBtn").addEventListener("click", async function () {
    const bookId = document.getElementById("bookId").value;
    const messageElement = document.getElementById("message");

    try {
        const response = await fetch(`removeBook?bookId=${encodeURIComponent(bookId)}`, {
            method: "DELETE",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
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
