document.getElementById("submitBtn").addEventListener("click", async function () {
    const formData = {
        userName: document.getElementById("userName").value,
        email: document.getElementById("email").value,
        address: document.getElementById("address").value,
        gender: document.querySelector('input[name="gender"]:checked').value,
        password: document.getElementById("password").value
    };

    try {
        const response = await fetch("registerUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });

        const result = await response.json();
        document.getElementById("message").textContent = result.message;

    } catch (error) {
        document.getElementById("message").textContent = "An error occurred: " + error.message;
    }
});

document.getElementById("backBtn").addEventListener("click", function () {
    window.history.back();
});