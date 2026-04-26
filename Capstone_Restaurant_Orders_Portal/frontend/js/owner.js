const role = localStorage.getItem("role");

if (role !== "RESTAURANT_OWNER") {
    alert("Access Denied");
    window.location.href = "index.html";
}