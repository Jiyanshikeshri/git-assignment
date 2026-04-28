const BASE_CATEGORY_URL = "http://localhost:8080/api/categories";
const BASE_MENU_URL = "http://localhost:8080/api/menu-items";

// Get restaurantId from URL
const params = new URLSearchParams(window.location.search);
const restaurantId = params.get("restaurantId");

async function loadData() {
    try {
        const [categories, items] = await Promise.all([
            fetch(BASE_CATEGORY_URL + "/restaurant/" + restaurantId, {
                headers: { "Authorization": "Bearer " + token }
            }).then(res => res.json()),

            fetch(BASE_MENU_URL + "/restaurant/" + restaurantId, {
                headers: { "Authorization": "Bearer " + token }
            }).then(res => res.json())
        ]);

        console.log("Categories:", categories);
        console.log("Items:", items);

        renderCategories(categories);
        renderMenu(categories, items);

    } catch (err) {
        console.error(err);
    }
}

/**
 * Render categories (TOP)
 */
function renderCategories(categories) {
    const container = document.getElementById("categoryList");
    container.innerHTML = "";

    categories.forEach(cat => {
        const div = document.createElement("div");
        div.classList.add("category");
        div.innerHTML = `
            <img src="../assets/category_static_image.jpg" />
            <p>${cat.name}</p>
        `;

        div.onclick = () => scrollToCategory(cat.id);

        container.appendChild(div);
    });
}

/**
 * Render menu grouped by category
 */
function renderMenu(categories, items) {
    const container = document.getElementById("menuContainer");
    container.innerHTML = "";

    categories.forEach(cat => {

        const section = document.createElement("div");
        section.classList.add("menu-section");
        section.id = "cat-" + cat.id;

        section.innerHTML = `<h2>${cat.name}</h2>`;

        const grid = document.createElement("div");
        grid.classList.add("menu-grid");

        // filter items for this category
        const filtered = items.filter(item => item.categoryName === cat.name);

        filtered.forEach(item => {
            const card = document.createElement("div");
            card.classList.add("menu-card");

            card.innerHTML = `
                <img src="../assets/menu_item_static.jpg" />
                <div class="menu-info">
                    <h3>${item.name}</h3>
                    <p class="price">₹ ${item.price}</p>
                    <button onclick="addToCart(${item.id})">Add to Cart</button>
                </div>
            `;

            grid.appendChild(card);
        });

        section.appendChild(grid);
        container.appendChild(section);
    });
}

/**
 * Scroll to section
 */
function scrollToCategory(id) {
    const element = document.getElementById("cat-" + id);

    element.scrollIntoView({
        behavior: "smooth"
    });
}

document.getElementById("searchBox").addEventListener("input", function () {
    const value = this.value.toLowerCase();

    const cards = document.querySelectorAll(".menu-card");

    cards.forEach(card => {
        const name = card.querySelector("h3").innerText.toLowerCase();

        if (name.includes(value)) {
            card.style.display = "block";
        } else {
            card.style.display = "none";
        }
    });
});

/**
 * Add item to cart
 */
function addToCart(menuItemId) {
    console.log("Token:", token);
    console.log("UserId:", userId);
    console.log("MenuItemId:", menuItemId);

    const cartRestaurantId = localStorage.getItem("cartRestaurantId");

    if (cartRestaurantId && cartRestaurantId != restaurantId) {
        alert("You can only order from one restaurant at a time");
        return;
    }

    fetch("http://localhost:8080/api/cart-items", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: userId,
            menuItemId: menuItemId,
            quantity: 1
        })
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Failed to add item");
        }
        return res.json();
    })
    .then(data => {
        console.log("Added to cart:", data);

        localStorage.setItem("cartRestaurantId", restaurantId);

        if (typeof loadCartCount === "function") {
            loadCartCount();
        }
        alert("Item added to cart");
    })
    .catch(err => {
        console.error(err);
        alert("Error adding item");
    });
}

loadData();