document.getElementById("submitBtn").addEventListener("click", async () => {
    const adminName = document.getElementById("userName").value.trim();
    const password = document.getElementById("password").value.trim();

    const adminLoginData = {
        userName: adminName,
        password: password
    };

    try {
        const response = await fetch('/myLibraryManagement/admin/adminLogIn', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(adminLoginData),
        });

        const data = await response.json();
        if (data.success === "true") {
            alert(data.message);
            window.location.href = "adminProcess.jsp"; 
        } else {
            alert(data.message);
        }
    } catch (error) {
        console.error('Error:', error);
        alert("An error occurred. Please try again.");
    }
});


document.getElementById("backBtn").addEventListener("click", () => {
    window.history.back();
});
