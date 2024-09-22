document.getElementById("submitBtn").addEventListener("click", () => {
    let admin = document.getElementById("userName").value;
    let password = document.getElementById("password").value;
    if (admin != adminName || password != passwordValue) {
        alert("Invalid User Credientials");
        return;
    }
    window.location.href = "adminProcess.jsp";

});
document.getElementById("backBtn").addEventListener("click", () => {
    window.history.back();
});