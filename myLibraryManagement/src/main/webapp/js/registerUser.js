document.getElementById("submitBtn").addEventListener("click", async function () {

    const userName = document.getElementById("userName").value.trim();
    const email = document.getElementById("email").value.trim();
    const address = document.getElementById("address").value.trim();
    const gender = document.querySelector('input[name="gender"]:checked')?.value.trim();
    const password = document.getElementById("password").value.trim();
    const messageElement = document.getElementById("message");

    messageElement.textContent = "";

    if (!userName || !email || !address || !gender || !password) {
        messageElement.textContent = "Please fill out all fields.";
        return;
    }

    const userNamePattern = /^[a-z_]+$/;
    if (!userNamePattern.test(userName)) {
        messageElement.textContent = "Username must contain only lowercase letters and underscores.";
        return;
    }

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
        messageElement.textContent = "Please enter a valid email address.";
        return;
    }

    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}$/;
    if (!passwordPattern.test(password)) {
        messageElement.textContent = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, and one special symbol.";
        return;
    }

    const formData = {
        userName: userName,
        email: email,
        address: address,
        gender: gender,
        password: password
    };

    try {
        const response = await fetch("/myLibraryManagement/admin/registerUser", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            messageElement.textContent = `An error occurred: ${response.statusText}`;
            return;
        }

        const result = await response.json();
        document.getElementById("registerUserForm").reset();
        messageElement.textContent = result.message;

    } catch (error) {
        messageElement.textContent = "An error occurred: " + error.message;
    }
});

document.getElementById("backBtn").addEventListener("click", function () {
    window.history.back();
});
