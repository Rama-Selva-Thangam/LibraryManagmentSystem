document.getElementById("submitBtn").addEventListener("click", async () => {
    let userName = document.getElementById("userName").value;
    let password = document.getElementById("password").value;
    let message = document.getElementById("message");


    if (!userName || !password) {
        alert("Invalid User Credentials");
        return;
    }

    try {
        const response = await fetch('userlogin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userName: userName,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error('Login failed');
        }

        const result = await response.json();

        if (result.success) {
            window.location.href = "userProcess.jsp";
        } else {
            message.innterText = result.message || "Invalid User Credentials";
        }
    } catch (error) {
        console.error('Error:', error);
        message.innterText = 'An error occurred during the login process';
    }
});

document.getElementById("backBtn").addEventListener("click", () => {
    window.history.back();
});
