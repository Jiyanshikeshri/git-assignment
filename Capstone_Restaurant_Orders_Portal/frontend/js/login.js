const BASE_URL = "http://localhost:8080/api/users";

function login() {

    const email = document.getElementById("email").value;
    const password =  document.getElementById("password").value;
    const messageDiv = document.getElementById("message");

    messageDiv.innerText = "";
    messageDiv.className = "message";

    // Basic validation
    if (!email || !password) {
        messageDiv.innerText = "Please fill all fields";
        messageDiv.classList.add("error");
        return;
    }

    const data = { email, password };

    fetch(BASE_URL + "/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Invalid credentials");
        }
        return res.json();
    })
    .then(data => {
        console.log(data);

        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userId", data.userId);

        // Success message
        messageDiv.innerText = "Login successful!";
        messageDiv.classList.add("success");

        // Redirect after 1 sec
        setTimeout(() => {
            if (data.role === "RESTAURANT_OWNER") {
            window.location.href = "owner-dashboard.html";
            } else {
                window.location.href = "restaurants.html";
            }
        },1000);
    })
    .catch(err => {
        messageDiv.innerText = err.message;
        messageDiv.classList.add("error");
    });
}
