const BASE_URL = "http://localhost:8080/api/users";

function register() {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const role = document.getElementById("role").value;
    const password = document.getElementById("password").value;

    const messageDiv = document.getElementById("message");

    messageDiv.innerText = "";
    messageDiv.className = "message";

    if (!firstName || !lastName || !email || !phoneNumber || !password || !role) {
        messageDiv.innerText = "Please fill all fields";
        messageDiv.classList.add("error");
        return;
    }

     const data = {
        firstName,
        lastName,
        email,
        role,
        phoneNumber,
        password
    };

    fetch(BASE_URL + "/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(res =>{
        if (!res.ok) {
            throw new Error("Registration failed");
        }
        return res.json();
    })
    .then(data => {
        console.log(data);

        messageDiv.innerText = "Registration successful!";
        messageDiv.classList.add("success");
        
        setTimeout(() => {
            window.location.href = "login.html";
        }, 1000);
    })
    .catch(err => {
        messageDiv.innerText = err.message;
        messageDiv.classList.add("error");
    });
}