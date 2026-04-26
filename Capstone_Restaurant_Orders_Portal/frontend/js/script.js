const BASE_URL = "http://localhost:8080/api/users";

function login() {
    const data = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    fetch(BASE_URL + "/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(data => {
        console.log(data);

        localStorage.setItem("token", data.token);

        alert("Login Successful");
    });
    window.location.href = "restaurants.html";
}

function register() {
    const data = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        password: document.getElementById("password").value,
        role: "USER"
    };

    fetch(BASE_URL + "/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(data => {
        console.log(data);
        alert("Registered Successfully");
        window.location.href = "index.html";
    });
}