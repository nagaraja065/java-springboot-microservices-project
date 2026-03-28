# 🍽️ Grand Spice – Restaurant Booking System

## Quick Start

### Step 1: Setup MySQL Database
Run this SQL command:
```sql
CREATE DATABASE restaurant_db;
```

### Step 2: Update Database Password
Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    password: YOUR_MYSQL_PASSWORD_HERE
```

### Step 3: Run the Application
```bash
mvn spring-boot:run
```
OR open in IntelliJ/Eclipse and run `RestaurantBookingApplication.java`

### Step 4: Open Browser
Navigate to: **http://localhost:8080**

---

## Default Login Credentials

| Role | Email | Password |
|------|-------|----------|
| 🛡️ Admin | admin@restaurant.com | admin123 |
| 👤 Customer | customer@gmail.com | customer123 |

---

## Features

### Customer Features
- ✅ Register & Login
- ✅ Book a table (date, time, guests, special requests)
- ✅ View my bookings with status
- ✅ Cancel pending/confirmed bookings
- ✅ Anti-double-booking protection

### Admin Features
- ✅ Dashboard with booking statistics
- ✅ View all bookings
- ✅ Approve / Reject bookings
- ✅ Add new tables with capacity & location
- ✅ Enable/Disable table availability
- ✅ Delete tables

---

## Tech Stack
- **Backend**: Java 17 + Spring Boot 3.2
- **Frontend**: HTML + CSS (Thymeleaf templates)
- **Database**: MySQL
- **Security**: Spring Security with BCrypt
