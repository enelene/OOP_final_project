**Quiz Website**

**Overview**

**Final project for Free University OOP class**

Welcome to the Quiz Website Java Web Application! This application allows users to create and play different kinds of quizzes, show their and other’s results, and have friends on the website with whom they can send messages. Below are the instructions to get started with the application.

**Requirements**

To ensure proper functionality of the application, make sure you have the following software and tools installed:

- Java 20+
- MySQL 8.0.25+
- Apache Tomcat 8.5.100

**Getting Started**

1. **Database Setup**
    - After cloning the repository, Go into the `QuizWebsite` directory.
    - Run the SQL script in your MySQL database to create the necessary database schema with this following command: `Get-Content .\schema.sql | mysql -u root -p quizwebsite`
2. **Database Configuration**
    - Open `/src/main/java/com/example/quizwebsite/listeners/DatabaseContextListener.java`.
    - Set the `datasource.username` and `datasource.password` fields with your MySQL database credentials.
3. **Application Features**
    - Users can register and log in to the application to access its features.
    - Users can create, edit, and play quizzes. Each quiz includes a title, description, and playing history on the Quiz summary page.
    - Users can send friend requests to each other, remove them, and also send notes.
    - The search functionality allows users to search for each other by their unique usernames.
    - Users can view each others' profiles and taken quizzes history.

**Building and Running**

1. **Using IntelliJ:**
    - This repository is integrated with IntelliJ IDEA, making it easy to build and run.
    - Simply open the project in IntelliJ and utilize the provided configurations to run the application.

© Free University of Tbilisi | 2024
