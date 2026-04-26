const token = localStorage.getItem("token");

if (!token) {
    alert("Please login first");
    window.location.href = "index.html";
}

const BASE_URL = "http://localhost:8080/api/menu-items";

// Get restaurantId from URL
const params = new URLSearchParams(window.location.search);
const restaurantId = params.get("restaurantId");

function loadMenu() {

    fetch(BASE_URL + "/restaurant/" + restaurantId, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
    .then(res => {
        if (!res.ok) {
            if (res.status === 403 || res.status === 401) {
                alert("Session expired, please login again");
                localStorage.removeItem("token");
                window.location.href = "index.html";
            }
            throw new Error("API Error: " + res.status);
        }
        return res.json();
    })
    .then(data => {

        console.log("Menu items:", data);

        const container = document.getElementById("menuList");
        container.innerHTML = "";

        data.forEach(item => {

            const card = document.createElement("div");
            card.classList.add("menu-card");

            card.innerHTML = `
                <img src="../assets/default.jpg" />
                <div class="menu-info">
                    <h3>${item.name}</h3>
                    <p class="price">₹ ${item.price}</p>
                    <button>Add to Cart</button>
                </div>
            `;

            container.appendChild(card);
        });
    })
    .catch(err => {
        console.error("Error:", err);
    });
}

loadMenu();