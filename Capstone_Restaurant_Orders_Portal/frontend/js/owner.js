const role = localStorage.getItem("role");

if (role !== "RESTAURANT_OWNER") {
    alert("Access Denied");
    window.location.href = "index.html";
}

const BASE_URL = "http://localhost:8080/api/restaurants";

function loadRestaurants() {

    fetch(BASE_URL, {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => res.json())
    .then(data => {

        let html = `
            <h2>My Restaurants</h2>
            <button onclick="showCreateForm()">+ Create Restaurant</button>
            <div class="grid">
        `;

        data.forEach(r => {
            html += `
                <div class="card">
                    <img src="../assets/pizza.jpg" />
                    <h3>${r.name}</h3>

                    <button class="btn-edit" onclick="editRestaurant(${r.id})">Edit</button>
                    <button class="btn-delete" onclick="deleteRestaurant(${r.id})">Delete</button>
                    <button class="btn-manage" onclick="openRestaurant(${r.id})">Manage</button>
                </div>
            `;
        });

        html += "</div>";

        document.getElementById("content").innerHTML = html;
    })
    .catch(err => {
        console.error(err);
        alert("Failed to load restaurants");
    });
}

window.onload = loadRestaurants;