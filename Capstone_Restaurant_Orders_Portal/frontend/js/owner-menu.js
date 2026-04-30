const BASE_MENU_URL = "http://localhost:8080/api/menu-items";

const params = new URLSearchParams(window.location.search);
const categoryId = params.get("categoryId");

const token = localStorage.getItem("token");
const restaurantId = localStorage.getItem("selectedRestaurantId");

function loadMenuItems() {
    fetch(BASE_MENU_URL + "/category/" + categoryId, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => renderMenuItems(data));
}

function renderMenuItems(items) {
    const container = document.getElementById("menuContent");

    let html = `
        <h2>Menu Items</h2>
        <button class="btn-create" onclick="showCreateForm()">
            + Create Menu Item
        </button>

        <div class="grid">
    `;

    items.forEach(item => {
        html += `
            <div class="card">
                <img src="${item.imageUrl || '../assets/menu_item_static.jpg'}" />
                <h3>${item.name}</h3>
                <p>₹ ${item.price}</p>

                <button class="btn-edit"
                    onclick="showEditForm(${item.id}, '${item.name}', ${item.price}, '${item.imageUrl || ""}')">
                    Edit
                </button>

                <button class="btn-delete"
                    onclick="deleteMenuItem(${item.id})">
                    Delete
                </button>
            </div>
        `;
    });

    html += "</div>";

    container.innerHTML = html;
}

function showCreateForm() {
    document.getElementById("menuContent").innerHTML = `
        <div class="form-card">
            <h3>Create Menu Item</h3>

            <input id="name" placeholder="Name">
            <input id="price" type="number" placeholder="Price">
            <input id="imageUrl" placeholder="Image URL">

            <button class="btn-create" onclick="createMenuItem()">Create</button>
            <button onclick="loadMenuItems()">Cancel</button>
        </div>
    `;
}

function createMenuItem() {

    const name = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const imageUrl = document.getElementById("imageUrl").value;

    if (!name || !price || !imageUrl) {
        showMessage("All fields are required", "error");
        return;
    }

    fetch(BASE_MENU_URL, {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name,
            price,
            imageUrl,
            categoryId,
            restaurantId
        })
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Menu item created successfully", "success");
        loadMenuItems();
    })
    .catch(() => {
        showMessage("Failed to create menu item", "error");
    });
}

function showEditForm(id, name, price, imageUrl) {
    document.getElementById("menuContent").innerHTML = `
        <div class="form-card">
            <h3>Edit Menu Item</h3>

            <input id="name" value="${name}">
            <input id="price" type="number" value="${price}">
            <input id="imageUrl" value="${imageUrl}">

            <button class="btn-edit" onclick="updateMenuItem(${id})">Update</button>
            <button onclick="loadMenuItems()">Cancel</button>
        </div>
    `;
}

function updateMenuItem(id) {

    const name = document.getElementById("name").value;
    const price = document.getElementById("price").value;
    const imageUrl = document.getElementById("imageUrl").value;

    if (!name || !price || !imageUrl) {
        showMessage("All fields are required", "error");
        return;
    }

    fetch(BASE_MENU_URL + "/" + id, {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name,
            price,
            imageUrl,
            categoryId,
            restaurantId
        })
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Menu item updated successfully", "success");
        loadMenuItems();
    })
    .catch(() => {
        showMessage("Update failed", "error");
    });
}

function deleteMenuItem(id) {

    if (!confirm("Are you sure you want to delete this menu item?")) {
        return;
    }

    fetch(BASE_MENU_URL + "/" + id, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) throw new Error();
        showMessage("Menu item deleted successfully", "success");
        loadMenuItems();
    })
    .catch(() => {
        showMessage("Delete failed", "error");
    });
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

loadMenuItems();