# Product Inventory Dashboard

## Project Overview

This project is a simple Product Inventory Dashboard built using HTML, CSS, and JavaScript.
It helps in managing products, applying filters, and viewing inventory analytics in a user-friendly way.

The project simulates real-world behavior like API loading and data persistence using localStorage.

---

## Features

### Dashboard

This shows the main UI of the application before and after products are rendered.

Dahsboard before rendering Products.
![Dashboard](screenshots/Dashboard%20before%20product%20rendering.jpg)

Dashboard after rendering Products.
![Dashboard](screenshots/Dashboard%20after%20Product%20rendering.jpg)

---

### Search Functionality

Users can search products by name.
The search is case-insensitive and updates results in real-time while typing.

![Search Feature](screenshots/Search%20feature.jpg)

---

### Category Filter

Products can be filtered based on category such as Electronics, Clothing, Books, etc.

Category filter
![Category Filter](screenshots/Category%20filter.jpg)

Category filter + search
![Category Filter](screenshots/Category%20filter%20+%20search.jpg)

---

### Low Stock Filter

Displays products where stock is less than 5.

![Low Stock Filter](screenshots/low%20stock.jpg)

---

### Sorting Options

Users can sort products using:

* Price Low → High
* Price High → Low
* A → Z
* Z → A

* Price Low → High
![Sorting Feature](screenshots/Sort%20price%20low%20to%20high.jpg)

* Price High → Low
![Sorting Feature](screenshots/Sort%20price%20high%20to%20low.jpg)

* A → Z
![Sorting Feature](screenshots/Sort%20alphabatically%20A%20to%20Z.jpg)

* Z → A
![Sorting Feature](screenshots/Sort%20alphabatically%20Z%20to%20A.jpg)

---

### Dashboard Analytics

Displays important inventory data:

* Total number of products
* Total inventory value
* Out of stock products

![Dashboard](screenshots/Analytics%20Dashboard.jpg)

---

### Add Product

Users can add new products using the form.
The new product is instantly added to the UI and stored in localStorage.

Adding product shows validations.
![Add Product](screenshots/Add%20product%20validations.jpg)

Add product form
![Add Product](screenshots/Add%20Product%20form.jpg)

Add Product Analytics Updated
![Add Product](screenshots/Add%20product%20form%20Dashboard%20updated.jpg)

product has been added in card layout
![Add Product](screenshots/Add%20product%20form%20Dashboard%20updated%202.jpg)

---

### Delete Product

Each product card has a delete button.
Clicking it removes the product from UI and localStorage.

Before deleting product
![Delete Product](screenshots/Delete%20product%201.jpg)

After deleting product
![Delete Product](screenshots/Delete%20Product%202.jpg)

Before deleting the Analytics
![Delete Product](screenshots/before%20deleting%20product.jpg)

After deleting the Analytics updated
![Delete Product](screenshots/After%20deleting%20Product.jpg)

After deleting product, it has been removed from local Storage too.
![Delete Product](screenshots/Deleted%20Product%20removed%20from%20local%20Storage%20too.jpg)

---

### Disable filter while loading (Bonus feature)

Filters are disabled until data is fully loaded to prevent incorrect actions.

![Disable filter](screenshots/Bonus%20feature%20disable%20filter%20while%20loading.jpg)

---

### Loader spinner instead of loading text (Bonus feature)

A loading spinner is shown when the page loads.
This simulates API delay using JavaScript Promise and setTimeout.

![Loader spinner](screenshots/Bonus%20feature%20loading%20spinner%20instead%20of%20text.jpg)

---

###  Edit Product (Bonus Feature)

Users can edit product details using a simple prompt-based interface.

![Edit Product](screenshots/edit%20feature%20functionality.jpg)

---

### Data Persistence (localStorage)

All product data is stored in browser localStorage.
Even after refreshing the page, the data remains saved.

Data before adding to local Storage
![Local Storage](screenshots/loacal%20Storage%201.jpg)

Data after adding to local Storage
![Local Storage](screenshots/loacal%20Storage%202.jpg)

---

### Responsive Design (Bonus)

The layout adjusts for smaller screens like mobile devices.

![Responsive](screenshots/Bonus%20feature%20Responsive%20design.jpg)


### Conclusion

This project demonstrates how core JavaScript concepts can be used to build a practical and interactive web application. It covers important functionalities like filtering, sorting, dynamic rendering, and data persistence using localStorage.

The dashboard provides a user-friendly way to manage products and visualize inventory insights. Additionally, features like loading simulation and responsive design make the application closer to real-world scenarios.

Overall, this project helped strengthen my understanding of DOM manipulation, event handling, and state management in JavaScript.