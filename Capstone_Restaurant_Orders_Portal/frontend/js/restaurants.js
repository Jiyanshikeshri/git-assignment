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

        // Image
        const img = document.createElement("img");
        img.src = res.imageUrl || '../assets/pizza_landing.jpg';
        img.alt = "restaurant";

        // Info container
        const info = document.createElement("div");
        info.classList.add("restaurant-info");

        // Name
        const name = document.createElement("h3");
        name.textContent = res.name;

        // Description
        const desc = document.createElement("p");
        desc.textContent = "Click to view menu";

        // Tag
        const tag = document.createElement("span");
        tag.classList.add("tag");
        tag.textContent = "Explore";

        // Append all
        info.appendChild(name);
        info.appendChild(desc);
        info.appendChild(tag);

        card.appendChild(img);
        card.appendChild(info);

        card.onclick = () => {
            window.location.href = `menu.html?restaurantId=${res.id}`;
        };

        container.appendChild(card);
    });
}

loadRestaurants();