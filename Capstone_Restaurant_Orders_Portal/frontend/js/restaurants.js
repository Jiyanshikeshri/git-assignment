const role = localStorage.getItem("role");

if (role !== "USER") {
    alert("Access Denied");
    window.location.href = "login.html";
}

const BASE_URL = "http://localhost:8080/api/restaurants";

function loadRestaurants() {
    fetch(BASE_URL,{
    headers: {
        "Authorization": "Bearer " + token
    }})
        .then(res => res.json())
        .then(data => {
            console.log("Restaurants:", data);
            displayRestaurants(data);
        })
        .catch(err => console.error(err));
}

function displayRestaurants(restaurants) {
    const container = document.getElementById("restaurantList");

    container.innerHTML = "";

    restaurants.forEach(res => {

        const card = document.createElement("div");
        card.classList.add("restaurant-card");

        card.innerHTML = `
            <img src=${res.imageUrl || '../assets/pizza_landing.jpg'} alt="restaurant">

            <div class="restaurant-info">
                <h3>${res.name}</h3>
                <p>Click to view menu</p>
                <span class="tag">Explore</span>
            </div>
        `;

        card.onclick = () => {
            window.location.href = `menu.html?restaurantId=${res.id}`;
        };

        container.appendChild(card);
    });
}

loadRestaurants();