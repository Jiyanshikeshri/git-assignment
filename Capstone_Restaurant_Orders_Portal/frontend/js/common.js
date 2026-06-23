const token = localStorage.getItem("token");
const userId = localStorage.getItem("userId");

/**
 * Logout
 */
function logout() {
    localStorage.clear();
    window.location.href = "login.html";
}

/**
 * Load Cart Count
 */
function loadCartCount() {
    fetch("http://localhost:8080/api/cart-items/user", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
    if (!res.ok) {
        throw new Error("Failed");
    }
    return res.json();
    })
    .then(data => {
        const cartElem = document.getElementById("cartCount");
        if (cartElem) {
        let totalCount = 0;

        data.forEach(item => {
            totalCount += item.quantity;
        });

        cartElem.innerText = totalCount;
    }
    })
    .catch(err => {
    console.error(err);
});
}

/**
 * Load Wallet
 */
function loadWallet() {
    fetch("http://localhost:8080/api/users/" + userId, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(user => {
        const walletElem = document.getElementById("walletAmount");
        if (walletElem) {
            walletElem.innerText = user.walletBalance;
        }
    });
}

/**
 * Init
 */
function initNavbar() {
    if (token && userId) {
        loadCartCount();
        loadWallet();
    }
}