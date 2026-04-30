const token = localStorage.getItem("token");

function loadCart() {
    fetch("http://localhost:8080/api/cart-items/user", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {
        console.log("Cart Data:", data);

        const container = document.getElementById("cartContainer");
        container.innerHTML = "";

        let total = 0;

        data.forEach(item => {

            total += item.price * item.quantity;

            container.innerHTML += `
                <div class="cart-card">
                    
                    <div class="left">
                        <img src="${item.imageUrl || '../assets/menu_item_static.jpg'}" />
                        <div>
                            <div class="item-name">${item.menuItemName}</div>
                            <div class="price">₹ ${item.price}</div>
                        </div>
                    </div>

                    <div class="controls">
                        <span class="qty">Qty: ${item.quantity}</span>
                        <span class="remove" onclick="removeItem(${item.cartItemId})">✖</span>
                    </div>

                </div>
            `;
        });

        document.getElementById("totalAmount").innerText = total;
    });
}

function removeItem(cartItemId) {
    fetch("http://localhost:8080/api/cart-items/" + cartItemId, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(() => loadCart());
}

function placeOrder() {
    showAddressForm();
}

function showAddressForm() {
    document.getElementById("addressForm").style.display = "block";
}

function confirmOrder() {

    const addressData = {
        streetAddress: document.getElementById("street").value,
        city: document.getElementById("city").value,
        pincode: document.getElementById("pincode").value
    };

    if (!addressData.streetAddress || !addressData.city || !addressData.pincode) {
        showMessage("Please fill all address fields", "error");
        return;
    }
    
    fetch("http://localhost:8080/api/addresses", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(addressData)
    })
    .then(res => res.json())
    .then(savedAddress => {

        const orderRequest = {
            addressId: savedAddress.id
        };

        return fetch("http://localhost:8080/api/orders", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(orderRequest)
        });

    })
    .then(res => {
        if (!res.ok) throw new Error("Order failed");
        return res.json();
    })
    .then(order => {
        showMessage("Order placed successfully", "success");
        document.getElementById("cartContainer").innerHTML = "";
        document.querySelector(".footer").style.display = "none";
        document.getElementById("addressForm").style.display = "none";

        document.getElementById("orderSuccessBox").classList.remove("hidden");

        document.getElementById("cancelBtnStyled").onclick = () => cancelOrder(order.id);

        startCountdown();
    })
    .catch(err => {
        showMessage("Something went wrong while placing order", "error");
    });
}

function startCountdown() {
    let timeLeft = 30;

    const countdownEl = document.getElementById("countdown");
    const cancelBtn = document.getElementById("cancelBtnStyled");

    window.countdownInterval= setInterval(() => {
        timeLeft--;

        let seconds = timeLeft < 10 ? "0" + timeLeft : timeLeft;
        countdownEl.innerText = "00:" + seconds;

        if (timeLeft <= 0) {
            clearInterval(window.countdownInterval);

            cancelBtn.disabled = true;
            cancelBtn.innerText = "Cannot cancel now";
            cancelBtn.style.background = "#ccc";
            cancelBtn.style.cursor = "not-allowed";
        }
    }, 1000);
}

function cancelOrder(orderId) {

    fetch("http://localhost:8080/api/orders/cancel/" + orderId, {
        method: "PUT",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(() =>{
        showMessage("Order cancelled successfully", "success");
    
        clearInterval(window.countdownInterval);

        const btn = document.getElementById("cancelBtnStyled");
        btn.disabled = true;
        btn.innerText = "Cancelled";

        document.querySelector(".success-card").innerHTML = `
            <div class="success-left">
                <div class="check-icon" style="background:#9e9e9e;">✖</div>
                <div>
                    <h3 style="color:#d32f2f;">Order Cancelled</h3>
                    <p>Your order has been cancelled successfully.</p>
                </div>
            </div>
        `;
        document.getElementById("countdown").innerText = "00:00";

    })
    .catch(err => {
        showMessage("Failed to cancel order", "error");
    });
}

function showMessage(message, type = "info") {
    const box = document.getElementById("messageBox");

    box.innerText = message;
    box.className = ""; // reset
    box.classList.add(type);

    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 3000);
}