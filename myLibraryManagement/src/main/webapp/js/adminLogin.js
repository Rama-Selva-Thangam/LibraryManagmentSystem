document.getElementById("submitBtn").addEventListener("click", () => {
    let adminName = "admin@123";
    let passwordValue = "admin";
    let admin = document.getElementById("userName").value.trim();
    let password = document.getElementById("password").value.trim();

    if (admin != adminName || password != passwordValue) {
        alert("Invalid Admin Credentials");
        return;
    }
    document.getElementById("adminForm").reset();

    window.location.href = "adminProcess.jsp";
});

document.getElementById("backBtn").addEventListener("click", () => {
    window.history.back();
});
