function loadMyOrders() {

    fetch("http://localhost:8080/api/orders/user", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Failed to fetch orders");
        }
        return res.json();
    })
    .then(data => {
        displayOrders(data);
    })
    .catch(err => {
        console.error(err);
        alert("Error loading orders");
    });
}

function displayOrders(orders) {

    const container = document.getElementById("ordersContainer");
    container.innerHTML = "";

    if (orders.length === 0) {
        container.innerHTML = "<p>No orders found</p>";
        return;
    }

    orders.forEach(order => {

        const div = document.createElement("div");
        div.classList.add("order-card");

        let statusColor = "black";

        if (order.status === "PLACED") statusColor = "orange";
        else if (order.status === "DELIVERED") statusColor = "green";
        else if (order.status === "CANCELLED") statusColor = "red";

        div.innerHTML = `
            <hr>
            <p><b>Order ID:</b> ${order.id}</p>
            <p><b>Restaurant:</b> ${order.restaurantId}</p>
            <p><b>Total:</b> ₹${order.totalAmount}</p>
            <p><b>Status:</b> <span style="color:${statusColor}">${order.status}</span></p>
            <p><b>Address:</b> ${order.address}</p>
            <p><b>Time:</b> ${order.createdAt}</p>
        `;
        container.appendChild(div);
    });
}