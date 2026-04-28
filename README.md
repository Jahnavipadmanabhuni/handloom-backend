# HandloomHub — Spring Boot + MySQL Backend

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

---

## Step 1 — Configure MySQL

Open `src/main/resources/application.properties` and set your MySQL password:

```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

MySQL will auto-create the database `handloom_db` on first run.

---

## Step 2 — Run the Backend

```bash
cd handloom-backend
mvn spring-boot:run
```

Server starts at **http://localhost:8080**

On first run it will print:
```
✅ Sample data seeded successfully!
   admin@handloom.com     / admin123
   artisan@handloom.com   / artisan123
   buyer@handloom.com     / buyer123
   marketing@handloom.com / marketing123
```

---

## Step 3 — Update the React Frontend

Copy ALL files from `handloom-frontend-updates/src/` into your React project's `src/` folder, replacing the existing files.

Then start your React app:
```bash
cd handloom-platform
npm start
```

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login, receive JWT |
| GET | /api/products | List all products |
| GET | /api/products/{id} | Get one product |
| POST | /api/products | Create product (artisan) |
| PUT | /api/products/{id} | Update product |
| DELETE | /api/products/{id} | Delete product |
| GET | /api/products/my-products | Artisan's products |
| GET | /api/cart | View cart |
| POST | /api/cart/add | Add to cart |
| PUT | /api/cart/update | Update quantity |
| DELETE | /api/cart/remove/{id} | Remove item |
| POST | /api/orders | Place order |
| GET | /api/orders/my-orders | Buyer's orders |
| GET | /api/orders | All orders (admin) |
| PUT | /api/orders/{id}/status | Update order status |
| GET | /api/campaigns | All campaigns |
| POST | /api/campaigns | Create campaign |
| GET | /api/admin/users | All users (admin) |
| DELETE | /api/admin/users/{id} | Delete user (admin) |

---

## Authentication

All protected endpoints need this header:
```
Authorization: Bearer <token>
```

The token is returned from `/api/auth/login` and `/api/auth/register`.
The React frontend handles this automatically via `src/services/api.js`.

---

## Project Structure

```
handloom-backend/
├── src/main/java/com/handloom/
│   ├── HandloomBackendApplication.java
│   ├── config/
│   │   ├── CorsConfig.java
│   │   ├── DataInitializer.java
│   │   ├── JwtUtil.java
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   ├── CampaignController.java
│   │   └── AdminController.java
│   ├── model/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Cart.java / CartItem.java
│   │   ├── Order.java / OrderItem.java
│   │   └── Campaign.java
│   ├── repository/        (JPA interfaces)
│   ├── service/           (business logic)
│   └── dto/               (request/response objects)
└── src/main/resources/
    └── application.properties

handloom-frontend-updates/src/
├── services/api.js        ← NEW: replaces all localStorage calls
├── context/AuthContext.js ← UPDATED: uses real API + JWT
├── components/auth/Login.js     ← UPDATED
├── components/auth/Register.js  ← UPDATED
├── components/artisan/ArtisanDashboard.js  ← UPDATED
├── components/buyer/BuyerDashboard.js      ← UPDATED
├── components/admin/AdminDashboard.js      ← UPDATED
├── components/marketing/MarketingDashboard.js ← UPDATED
└── pages/
    ├── CartPage.js        ← UPDATED
    ├── ProductsPage.js    ← UPDATED
    └── ProductDetail.js   ← UPDATED
```
