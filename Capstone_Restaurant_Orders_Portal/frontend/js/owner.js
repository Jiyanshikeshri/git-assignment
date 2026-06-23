const role = localStorage.getItem("role");

if (role !== "RESTAURANT_OWNER") {
    showMessage("Access Denied", "error");

    setTimeout(() => {
        window.location.href = "index.html";
    }, 1500);
}

const BASE_URL = "http://localhost:8080/api/restaurants/owner";

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
                    <img src=${r.imageUrl || '../assets/pizza_landing.jpg'} alt="restaurant" />
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

function showCreateForm() {
    document.getElementById("content").innerHTML = `
        <div class="form-card">
            <h3>Create Restaurant</h3>

            <input type="text" id="name" placeholder="Restaurant Name" />

            <select id="status">
                <option value="">Select Status</option>
                <option value="OPEN">OPEN</option>
                <option value="CLOSED">CLOSED</option>
            </select>

            <input type="text" id="imageUrl" placeholder="Image URL" />

            <button class="btn-create" onclick="createRestaurant()">Create</button>
            <button onclick="loadRestaurants()">Cancel</button>
        </div>
    `;
}

function createRestaurant() {

    const name = document.getElementById("name").value;
    const status = document.getElementById("status").value;
    const imageUrl = document.getElementById("imageUrl").value;

    if (!name || !status || !imageUrl) {
        showMessage("All fields are required", "error");
        return;
    }

    const data = {
        name: name,
        status: status,
        ownerId: localStorage.getItem("userId"),
        imageUrl: imageUrl
    };

    fetch("http://localhost:8080/api/restaurants", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        return res.json();
    })
    .then(() => {
        showMessage("Restaurant created successfully", "success");
        loadRestaurants();
    })
    .catch(() => {
        showMessage("Failed to create restaurant", "error");
    });
}

window.onload = loadRestaurants;

function deleteRestaurant(id) {

    fetch(`http://localhost:8080/api/categories/restaurant/${id}`, {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => res.json())
    .then(categories => {

        if (categories.length > 0) {
            showMessage("Delete categories first before deleting restaurant", "error");
            return;
        }

        // safe to delete
        return fetch("http://localhost:8080/api/restaurants/" + id, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        });
    })
    .then(res => {
        if (!res) return;

        if (!res.ok) throw new Error("Delete failed");

        showMessage("Restaurant deleted successfully", "success");
        loadRestaurants();
    })
    .catch(() => {
        showMessage("Delete failed", "error");
    });
}

function editRestaurant(id) {

    fetch(BASE_URL + "/" + id)
    .then(res => res.json())
    .then(r => {

        console.log("Status from backend:", r.status);

        document.getElementById("content").innerHTML = `
            <div class="form-card">
                <h3>Edit Restaurant</h3>

                <input type="text" id="name" value="${r.name}" />

                <select id="status">
                    <option value="">Select Status</option>
                    <option value="OPEN">OPEN</option>
                    <option value="CLOSED">CLOSED</option>
                </select>

                <input type="text" id="imageUrl" value="${r.imageUrl || ""}" />

                <button onclick="updateRestaurant(${r.id})">Update</button>
                <button onclick="loadRestaurants()">Cancel</button>
            </div>
        `;

        document.getElementById("status").value = r.status?.toUpperCase();
    });
}

function updateRestaurant(id) {

    const name = document.getElementById("name").value.trim();
    const status = document.getElementById("status").value;
    const imageUrl = document.getElementById("imageUrl").value.trim();

    console.log("Name:", name);
    console.log("Status:", status);
    console.log("Image:", imageUrl);

    if (name === "" || imageUrl === "") {
        showMessage("All fields are required", "error");
        return;
    }

    const data = {
        name: name,
        status: status,
        ownerId: localStorage.getItem("userId"),
        imageUrl: imageUrl
    };

    fetch("http://localhost:8080/api/restaurants/" + id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Restaurant updated successfully", "success");
        loadRestaurants();
    })
    .catch(() => {
        showMessage("Update failed", "error");
    });
}

function openRestaurant(id) {
    localStorage.setItem("selectedRestaurantId", id);
    loadCategories();
}

function loadCategories() {

    const restaurantId = localStorage.getItem("selectedRestaurantId");

    fetch("http://localhost:8080/api/categories/restaurant/" + restaurantId, {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => res.json())
    .then(data => {

        let html = `
            <h2>Categories</h2>
            <button class="btn-create" onclick="showCreateCategoryForm()">
                + Create Category
            </button>

            <div class="grid">
        `;

        data.forEach(cat => {
            html += `
                <div class="card">
                    <img src="${cat.imageUrl || '../assets/category_static_image.jpg'}" />
                    <h3>${cat.name}</h3>

                    <button class="btn-edit" onclick="editCategory(${cat.id})">Edit</button>
                    <button class="btn-delete" onclick="deleteCategory(${cat.id})">Delete</button>
                    <button class = "btn-manage" onclick="openMenu(${cat.id})">Manage</button>
                </div>
            `;
        });

        html += "</div>";

        document.getElementById("content").innerHTML = html;
    })
    .catch(err => {
        console.error(err);
        alert("Failed to load categories");
    });
}

function showCreateCategoryForm() {
    document.getElementById("content").innerHTML = `
        <div class="form-card">
            <h3>Create Category</h3>

            <input id="catName" placeholder="Category Name" />
            <input id="catImage" placeholder="Image URL" />

            <button onclick="createCategory()">Create</button>
            <button onclick="loadCategories()">Cancel</button>
        </div>
    `;
}

function createCategory() {

    const name = document.getElementById("catName").value.trim();
    const imageUrl = document.getElementById("catImage").value.trim();

    if (!name || !imageUrl) {
        showMessage("All fields are required", "error");
        return;
    }

    fetch("http://localhost:8080/api/categories", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name,
            imageUrl,
            restaurantId: localStorage.getItem("selectedRestaurantId")
        })
    })
    .then(() => {
        showMessage("Category created successfully", "success");
        loadCategories();
    })
    .catch(() => {
        showMessage("Failed to create category", "error");
    });
}

function editCategory(id) {

    fetch("http://localhost:8080/api/categories/restaurant/" + localStorage.getItem("selectedRestaurantId"), {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => res.json())
    .then(categories => {

        const cat = categories.find(c => c.id === id);

        document.getElementById("content").innerHTML = `
            <div class="form-card">
                <h2>Edit Category</h2>

                <input id="catName" value="${cat.name}" placeholder="Category Name" />
                <input id="catImage" value="${cat.imageUrl || ''}" placeholder="Image URL" />

                <div class="form-actions">
                    <button class="btn-create" onclick="updateCategory(${id})">Update</button>
                    <button onclick="loadCategories()">Cancel</button>
                </div>
            </div>
        `;
    });
}

function updateCategory(id) {

    const name = document.getElementById("catName").value.trim();
    const imageUrl = document.getElementById("catImage").value.trim();

    if (!name || !imageUrl) {
        showMessage("All fields are required", "error");
        return;
    }

    fetch("http://localhost:8080/api/categories/" + id, {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token"),
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            imageUrl: imageUrl
        })
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Category updated successfully", "success");
        loadCategories();
    })
    .catch(() => {
        showMessage("Failed to update category", "error");
    });
}

function deleteCategory(id) {

    if (!confirm("Are you sure you want to delete this category?")) {
        return;
    }

    fetch("http://localhost:8080/api/categories/" + id, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Category deleted successfully", "success");
        loadCategories();
    })
    .catch(() => {
        showMessage("Failed to delete category", "error");
    });
}

function openMenu(categoryId) {
    const restaurantId = localStorage.getItem("selectedRestaurantId");

    window.location.href = 
        `owner-menu.html?categoryId=${categoryId}&restaurantId=${restaurantId}`;
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("selectedRestaurantId");

    window.location.href = "index.html";
}

function showMessage(message, type = "info") {
    const box = document.getElementById("messageBox");

    box.innerText = message;
    box.className = "message-box " + type;
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 3000);
}

function loadOwnerOrders() {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/orders/owner", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        console.log("Status:", res.status); 
        if (!res.ok) throw new Error("Failed to load orders");
        return res.json();
    })
    .then(data => displayOwnerOrders(data))
    .catch(err => {
        console.error(err);
        showMessage("Error loading orders");
    });
}

function displayOwnerOrders(orders) {

    const content = document.getElementById("content");

    content.innerHTML = "<h2>Orders</h2>";

    if (orders.length === 0) {
        content.innerHTML += "<p>No orders found</p>";
        return;
    }

    orders.forEach(order => {

        let statusColor = "black";

        if (order.status === "PLACED") statusColor = "orange";
        else if (order.status === "PENDING") statusColor = "blue";
        else if (order.status === "DELIVERED") statusColor = "green";
        else if (order.status === "CANCELLED") statusColor = "red";

        const card = document.createElement("div");
        card.classList.add("order-card");

        card.innerHTML = `
            <p><b>Order ID:</b> ${order.id}</p>
            <p><b>User ID:</b> ${order.userId}</p>
            <p><b>Restaurant:</b> ${order.restaurantName} (ID: ${order.restaurantId})</p>
            <p><b>Total:</b> ₹${order.totalAmount}</p>
            <p><b>Status:</b> <span style="color:${statusColor}">${order.status}</span></p>
            <p><b>Address:</b> ${order.address}</p>

            <select id="status-${order.id}">
                <option value="PLACED">PLACED</option>
                <option value="PENDING">PENDING</option>
                <option value="DELIVERED">DELIVERED</option>
                <option value="COMPLETED">COMPLETED</option>
                <option value="CANCELLED">CANCELLED</option>
            </select>

            <button onclick="updateOrderStatus(${order.id})">
                Update
            </button>

            <hr>
        `;

        content.appendChild(card);
    });
}

function updateOrderStatus(orderId) {

    const token = localStorage.getItem("token");
    const status = document.getElementById(`status-${orderId}`).value;

    fetch(`http://localhost:8080/api/orders/status/${orderId}?status=${status}`, {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => {
                throw new Error(text);
            });
        }
        return res.text();
    })
    .then(() => {
        showMessage(`Order marked as ${status}`, "success"); 
        loadOwnerOrders();
    })
    .catch(err => {
        console.error(err);

        let message = "Something went wrong";

        if (err.message.includes("not allowed")) {
            message = "You cannot update this order";
        }
        else if (err.message.includes("cancelled")) {
            message = "This order is already cancelled";
        }
        else if (err.message.includes("Order not found")) {
            message = "Order not found";
        }
        else if (err.message.includes("No enum constant")) {
            message = "Invalid status selected";
        }

        showMessage(message, "error");
    });
}