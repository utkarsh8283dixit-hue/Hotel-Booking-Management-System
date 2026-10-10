# Hotel Booking Management System

A console-based hotel booking management system developed using Java, JDBC, MySQL and Maven.

## Features

- Add hotel booking
- View all bookings
- Search booking
- Cancel booking
- Store booking data in MySQL

## Technologies Used

- Java 17
- JDBC
- MySQL
- Maven
- IntelliJ IDEA

## Database Setup

1. Create a MySQL database named `hotel_booking_db`.
2. Run the SQL file from the `database` folder.
3. Update the database username and password in `DBConnection.java`.

## Run the Project

```bash
mvn clean compile
mvn exec:java
