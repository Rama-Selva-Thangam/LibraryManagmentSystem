document.getElementById("submitBtn").addEventListener("click", async function () {
    const userId = document.getElementById("bookId").value;
    const messageElement = document.getElementById("message");

    try {
        const response = await fetch("removeBook", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({ bookId })
        });

        if (!response.ok) {
            const errorResult = await response.json();
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
