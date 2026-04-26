const BASE_URL = "http://localhost:8080/api/restaurants";

/**
 * Fetch all restaurants from backend
 */
function loadRestaurants() {
    fetch(BASE_URL)
        .then(res => res.json())
        .then(data => {
            console.log("Restaurants:", data);
            displayRestaurants(data);
        })
        .catch(err => console.error(err));
}

/**
 * Render restaurants on UI
 */
function displayRestaurants(restaurants) {
    const container = document.getElementById("restaurantList");

    container.innerHTML = "";

    restaurants.forEach(res => {

        const card = document.createElement("div");
        card.classList.add("restaurant-card");

        /**
         * Since no image in backend → using static image
         */
        card.innerHTML = `
            <img src="../assets/dominos.jpg" alt="restaurant">

            <div class="restaurant-info">
                <h3>${res.name}</h3>
                <p>Click to view menu</p>
            </div>
        `;

        /**
         * On click → go to menu page
         */
        card.onclick = () => {
            window.location.href = `menu.html?restaurantId=${res.id}`;
        };

        container.appendChild(card);
    });
}

/**
 * Load data when page opens
 */
loadRestaurants();