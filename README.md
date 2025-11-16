# Supermarket Management System - Customer Registration & Login Module

## Overview
This is a complete Java Servlet/J2EE web application implementing customer registration and login functionality for a supermarket management system. The implementation follows the exact workflow specified in `step.md`.

## Technologies Used
- **Java 8+**
- **Servlet API 4.0**
- **JSP (JavaServer Pages)**
- **MySQL Database**
- **Maven** (for dependency management)
- **Apache Tomcat 9+** (or any compatible servlet container)

## Project Structure
```
SupermarketManagement/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── supermarket/
│   │   │           ├── controller/
│   │   │           │   └── CustomerController.java
│   │   │           ├── dao/
│   │   │           │   └── CustomerDAO.java
│   │   │           ├── model/
│   │   │           │   └── Customer.java
│   │   │           └── util/
│   │   │               └── DatabaseConnection.java
│   │   ├── resources/
│   │   │   └── database.properties
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── views/
│   │       │   ├── LoginView.jsp
│   │       │   ├── MemberRegistrationView.jsp
│   │       │   └── Dashboard.jsp
│   │       └── index.jsp
├── database-schema.sql
├── pom.xml
└── README.md
```

## Implementation Flow (Following step.md)

### Registration Process:
1. **LoginView.jsp** - Customer clicks register button
2. **Navigation** - LoginView.jsp calls MemberRegistrationView.jsp
3. **Display Form** - MemberRegistrationView.jsp displays registration form
4. **Submit Form** - Customer enters information and clicks submit
5. **Controller Call** - MemberRegistrationView.jsp calls CustomerController
6. **doPost() Method** - CustomerController.doPost() is invoked
7. **Encapsulation** - doPost() creates Customer object to encapsulate data
8. **Entity Creation** - Customer class encapsulates entity information
9. **Return Result** - Customer object returned to doPost()
10. **DAO Call** - doPost() calls CustomerDAO with customer information
11. **Database Save** - CustomerDAO.createCustomer() saves to database
12. **DAO Result** - CustomerDAO returns result to doPost()
13. **Controller Result** - doPost() returns result to MemberRegistrationView.jsp
14. **Success Notification** - MemberRegistrationView.jsp displays success notification

## Database Setup

### 1. Install MySQL
Make sure MySQL is installed and running on your system.

### 2. Create Database
Run the SQL script provided:
```sql
mysql -u root -p < database-schema.sql
```

Or manually execute:
```sql
CREATE DATABASE supermarket_management;
USE supermarket_management;
-- Then run the rest of the schema from database-schema.sql
```

### 3. Configure Database Connection
Edit `src/main/java/com/supermarket/util/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/supermarket_management";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password"; // Update this
```

Or edit `src/main/resources/database.properties`:
```properties
db.password=your_mysql_password
```

## Building the Project

### Prerequisites
- JDK 8 or higher
- Maven 3.6+
- Apache Tomcat 9+
- MySQL 5.7+ or 8.0+

### Build Steps

1. **Clone/Navigate to project directory**
```bash
cd SupermarketManagement
```

2. **Build with Maven**
```bash
mvn clean install
```

This will:
- Compile all Java classes
- Download dependencies
- Create a WAR file in `target/SupermarketManagement.war`

## Deployment

### Option 1: Deploy to Tomcat Manually

1. Copy the WAR file to Tomcat webapps:
```bash
cp target/SupermarketManagement.war /path/to/tomcat/webapps/
```

2. Start Tomcat:
```bash
cd /path/to/tomcat/bin
./startup.sh  # Linux/Mac
startup.bat   # Windows
```

3. Access the application:
```
http://localhost:8080/SupermarketManagement
```

### Option 2: Use Maven Tomcat Plugin

```bash
mvn tomcat7:run
```

Access at: `http://localhost:8080/supermarket`

### Option 3: Deploy in IDE (Eclipse/IntelliJ)

1. Import project as Maven project
2. Configure Tomcat server in IDE
3. Deploy and run

## Using the Application

### 1. Registration
1. Navigate to `http://localhost:8080/SupermarketManagement`
2. Click "Register" or "Create New Account"
3. Fill in the registration form:
   - Full Name
   - Email Address
   - Phone Number
   - Username
   - Password
   - Confirm Password
4. Click "Register"
5. Upon success, you'll be redirected to login page

### 2. Login
1. Enter your username and password
2. Click "Sign In"
3. You'll be redirected to the Dashboard

### 3. Logout
- Click "Logout" button in the navigation bar

## Key Features

### Security Features
- Password strength indicator
- Client-side validation
- Server-side validation
- Session management
- Duplicate username/email detection

### User Experience
- Responsive design
- Clean and modern UI
- Success/error notifications
- Password confirmation
- Form validation

## Database Schema

### Customer Table
```sql
CREATE TABLE Customer (
    CustomerID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Phone VARCHAR(20),
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(20) DEFAULT 'Active'
);
```

### Staff Table (Unified)
```sql
CREATE TABLE Staff (
    StaffID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Phone VARCHAR(20),
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL, -- 'Management', 'Delivery', 'Sales'
    HireDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Status VARCHAR(20) DEFAULT 'Active'
);
```

## API Endpoints

### CustomerController
- **URL**: `/CustomerController`
- **Methods**: POST, GET

#### Registration
```
POST /CustomerController?action=register
Parameters:
- fullName
- email
- phone
- username
- password
- confirmPassword
```

#### Login
```
POST /CustomerController?action=login
Parameters:
- username
- password
```

#### Logout
```
GET /CustomerController?action=logout
```

## Future Enhancements

1. **Password Hashing** - Implement BCrypt for secure password storage
2. **Email Verification** - Send verification emails upon registration
3. **Password Recovery** - Forgot password functionality
4. **CAPTCHA** - Add CAPTCHA to prevent bots
5. **OAuth Integration** - Login with Google/Facebook
6. **Profile Management** - Allow users to update their information
7. **Order Management** - Complete shopping cart and order functionality

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check database credentials in `DatabaseConnection.java`
- Ensure database `supermarket_management` exists
- Check MySQL JDBC driver is in classpath

### Deployment Issues
- Ensure Tomcat is running on correct port (8080)
- Check for port conflicts
- Verify WAR file is properly deployed
- Check Tomcat logs in `logs/catalina.out`

### Build Issues
- Ensure Java 8+ is installed
- Verify Maven is properly configured
- Check internet connection for dependency download
- Run `mvn clean install -U` to force update dependencies

## Testing

### Test Accounts
After running `database-schema.sql`, you can test with:

**Customer Account:**
- Username: `johndoe`
- Password: `password123`

**Staff Accounts:**
- Management: `admin` / `admin123`
- Delivery: `delivery1` / `delivery123`
- Sales: `sales1` / `sales123`

## Support
For issues or questions, please refer to the project documentation or contact the development team.

## License
This project is created for educational purposes.

---
**Developed by:** Supermarket Management Team  
**Version:** 1.0.0  
**Last Updated:** October 2025
