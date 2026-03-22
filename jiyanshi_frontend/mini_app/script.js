// Default product data
// as of now I have taken simple dummy data and later we will connect it with local storage

// let products = [
//     { id: 1, name: "Laptop", price: 55000, stock: 5, category: "electronics" },
//     { id: 2, name: "Shirt", price: 1200, stock: 10, category: "clothing" },
//     { id: 3, name: "Book", price: 500, stock: 2, category: "books" },
//     { id: 4, name: "Headphones", price: 2000, stock: 0, category: "electronics" },
//     { id: 5, name: "Shoes", price: 3000, stock: 4, category: "clothing" },
//     { id: 6, name: "Watch", price: 1500, stock: 8, category: "accessories" },
//     { id: 7, name: "Bag", price: 1000, stock: 6, category: "accessories" },
//     { id: 8, name: "Mobile", price: 25000, stock: 3, category: "electronics" }
// ];

// Load products from localStorage on page load. If localStorage is empty → use the default product list. (doing as per assignment)

let storedProducts = localStorage.getItem("products");

if (storedProducts) {
    products = JSON.parse(storedProducts);
} else {
    products = [
        { id: 1, name: "Laptop", price: 55000, stock: 5, category: "electronics" },
        { id: 2, name: "Shirt", price: 1200, stock: 10, category: "clothing" },
        { id: 3, name: "Book", price: 500, stock: 2, category: "books" },
        { id: 4, name: "Headphones", price: 2000, stock: 0, category: "electronics" },
        { id: 5, name: "Shoes", price: 3000, stock: 4, category: "clothing" },
        { id: 6, name: "Watch", price: 1500, stock: 8, category: "accessories" },
        { id: 7, name: "Bag", price: 1000, stock: 6, category: "accessories" },
        { id: 8, name: "Mobile", price: 25000, stock: 3, category: "electronics" }
    ];
}

// Loading simulation

// I am selecting loading text that is already there in html
const loadingText = document.getElementById("loading");

// this function will behave as fake API, means we are not creating actual backend we are just creating delay
function fetchProducts() {
    return new Promise(function (resolve) {
        setTimeout(function () {
            resolve(products);
        }, 1500);
    });
}

// Render products

//I have selected product grid where the cards will be shown
const productGrid = document.getElementById("productGrid");

// This function will show the products on UI.
function renderProducts(productList) {
    productGrid.innerHTML = "";
    if (productList.length === 0) {
        productGrid.innerHTML = "<p>No products found</p>";
        updateDashboard(productList);
        return;
    }

    // I have make card format for each product
    productList.forEach(function (product) {
        const card = document.createElement("div");
        card.classList.add("card");
        card.innerHTML = `
            <h3>${product.name}</h3>
            <p>Category: ${product.category}</p>
            <p>Price: ₹${product.price}</p>
            <p>Stock: ${product.stock}</p>
            <button onclick="deleteProduct(${product.id})">Delete</button>
        `;
        productGrid.appendChild(card);
    });
    updateDashboard(productList);
}

// Page load flow

//whenever the page will load this function will run
window.onload = function () {

    // At first loading text will apear
    loadingText.style.display = "block";
    // then fake API call
    fetchProducts().then(function (data) {
        loadingText.style.display = "none"; //this will remove loading text
        loadCategories(); // dropdown fill karega
        renderProducts(data); //render products on screen
    });
};

// Load categories function....when selecting categories
function loadCategories() {
    let categorySet = new Set();
    products.forEach(function (product) {
        categorySet.add(product.category);
    });

    const categoryDropdown = document.getElementById("categoryFilter");

    // reset dropdown
    categoryDropdown.innerHTML = '<option value="all">All Categories</option>';
    categorySet.forEach(function (category) {
        const option = document.createElement("option");
        option.value = category;
        option.textContent = category;
        categoryDropdown.appendChild(option);
    });
}

// selecting search Input from html
const searchInput = document.getElementById("searchInput");
// Category filter
const categoryFilter = document.getElementById("categoryFilter");
// stock filter 
const stockFilter = document.getElementById("stockFilter");
// sorting filter
const sortOption = document.getElementById("sortOption");

// Applying filter function, this function will execute all filters at once
function applyFilters() {
    let filtered = products;

    // SEARCH FILTER
    let searchValue = searchInput.value.toLowerCase();
    filtered = filtered.filter(function (product) {
        return product.name.toLowerCase().includes(searchValue);
    });
    
    // CATEGORY FILTER
    let selectedCategory = categoryFilter.value;
    if (selectedCategory !== "all") {
        filtered = filtered.filter(function (product) {
            return product.category === selectedCategory;
        });
    }

    // STOCK FILTER
    let stockValue = stockFilter.value;
    if (stockValue === "low") {
        filtered = filtered.filter(function (product) {
            return product.stock < 5;
        });
    }

    // SORTING FILTER
    let sortValue = sortOption.value;

    // Price Low → High
    if (sortValue === "lowHigh") {
        filtered.sort(function (a, b) {
            return a.price - b.price;
        });
    }

    // Price High → Low
    if (sortValue === "highLow") {
        filtered.sort(function (a, b) {
            return b.price - a.price;
        });
    }

    // A → Z
    if (sortValue === "az") {
        filtered.sort(function (a, b) {
            return a.name.localeCompare(b.name);
        });
    }

    // Z → A
    if (sortValue === "za") {
        filtered.sort(function (a, b) {
            return b.name.localeCompare(a.name);
        });
    }
    renderProducts(filtered);
}

//this will apply filters when search input will change
searchInput.addEventListener("input", applyFilters);

// this will apply filters when category will change
categoryFilter.addEventListener("change", applyFilters);   

// this will apply filters when stock value will change
stockFilter.addEventListener("change", applyFilters);

//this will apply filters when we sort using options
sortOption.addEventListener("change", applyFilters);

// Dashboard Analytics

const totalProductsEl = document.getElementById("totalProducts");
const totalValueEl = document.getElementById("totalValue");
const outOfStockEl = document.getElementById("outOfStock");

function updateDashboard(productList) {

    // total products
    let totalProducts = productList.length;

    // total value
    let totalValue = 0;

    productList.forEach(function (product) {
        totalValue += product.price * product.stock;
    });

    // out of stock
    let outOfStock = productList.filter(function (product) {
        return product.stock === 0;
    }).length;

    // we are only updating numbers
    totalProductsEl.textContent = totalProducts;
    totalValueEl.textContent = totalValue;
    outOfStockEl.textContent = outOfStock;
}

// Add product feature
const nameInput = document.getElementById("name");
const priceInput = document.getElementById("price");
const stockInput = document.getElementById("stock");
const categoryInput = document.getElementById("category");
const addBtn = document.getElementById("addBtn");

addBtn.addEventListener("click", function () {

    // taking input values
    let name = nameInput.value;
    let price = Number(priceInput.value);
    let stock = Number(stockInput.value);
    let category = categoryInput.value;

    // basic validation
    if (name === "" || price <= 0 || stock < 0 || category === "") {
        alert("Please enter all valid details");
        return;
    }

    // making new product object 
    let newProduct = {
        id: products.length + 1,
        name: name,
        price: price,
        stock: stock,
        category: category
    };

    // adding new product to products array
    products.push(newProduct);
    // saving it in localstorage as well
    localStorage.setItem("products", JSON.stringify(products));

    // UI update (filters + render + dashboard, everything will get updated)
    applyFilters();

    // cleared/reset input field of form for next new product
    nameInput.value = "";
    priceInput.value = "";
    stockInput.value = "";
    categoryInput.value = "";
});

// Delete Product Feature

function deleteProduct(id) {

    // using filter to remove product
    products = products.filter(function (product) {
        return product.id !== id;
    });

    // updating localStorage as well
    localStorage.setItem("products", JSON.stringify(products));

    // UI update (filters + dashboard)
    applyFilters();
}