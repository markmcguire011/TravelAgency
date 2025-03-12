# Golden Compass Travel Booking System

A Java-based travel booking application that allows users to browse, book, and manage travel packages including flights and hotel accommodations. The system includes both user and administrative interfaces for complete travel package management.

## Features

- **User Interface**

  - Browse available travel packages
  - Create and manage bookings
  - View booking history
  - Search destinations
  - View package details including flights and hotels

- **Admin Interface**
  - Create and manage travel packages
  - Add hotels and flights
  - Manage user accounts
  - Create additional admin accounts

## Installation

1. Clone the repository
2. Ensure you have Java 21 installed
3. Set up MySQL database (setup instructions provided separately)
4. Configure database connection in `src/utils/DatabaseConnection.java` with your database credentials
5. Compile and run the application:
   ```bash
   javac -d bin src/**/*.java
   java -cp "bin:lib/mysql-connector-j-8.x.x.jar" Main
   ```

## Usage

### For Users

1. Launch the application
2. Create a new account or log in
3. Browse available travel packages
4. Select desired package and dates
5. Complete booking process
6. View and manage bookings from the user dashboard

### For Administrators

1. Log in with admin credentials
2. Access admin dashboard to:
   - Create new travel packages
   - Add hotels and flights
   - Manage user accounts
   - View system data

## Architecture

The application is built using:

- Java Swing for the GUI
- MySQL for data persistence
- DAO pattern for database operations
- MVC architecture

## Contributors

- Mark McGuire
- Maxwell Dubow
- William Liu

## Technical Requirements

- **Java Version**: OpenJDK 21.0.5 or higher
- **Database**: MySQL
- **Screen Resolution**: Minimum 1024x768
- **Operating System**: Cross-platform (Windows, macOS, Linux)
- **Required Libraries**: MySQL Connector/J (JDBC driver)

## Notes

- Database setup scripts are provided separately
- The application requires an active internet connection for database access
