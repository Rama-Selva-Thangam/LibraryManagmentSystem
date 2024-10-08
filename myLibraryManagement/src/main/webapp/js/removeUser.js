document.getElementById("submitBtn").addEventListener("click", async function () {
    const userId = document.getElementById("userId").value.trim();
    const messageElement = document.getElementById("message");

    try {
        const response = await fetch("/myLibraryManagement/admin/removeUser", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({ userId })
        });

        if (!response.ok) {
            const errorResult = await response.json();
            throw new Error(errorResult.message || "Network response was not ok.");
        }

        const result = await response.json();
        document.getElementById("removeUserForm").reset();
        alert(result.message);
    } catch (error) {
        messageElement.textContent = "An error occurred: " + error.message;
    }
});

document.getElementById("backBtn").addEventListener("click", function () {
    window.history.back();
});
