# Event_Organizer

🧩 Overview
This Java-based Event Organizer is a desktop application that allows users to manage events efficiently. Each event contains details such as:

Event Name

Event Time

List of Attendees

The application is built using Java Swing for the GUI and JDBC (Java Database Connectivity) to interact with an SQL database. It supports core CRUD operations: Create, Read, Update, and Delete.

🧠 OOP Concepts Used
Inheritance is used to create a class hierarchy.
For example:

A base class Event contains common fields like name, time, and attendees.

A derived class SpecialEvent (or similar) could extend Event with additional features (like venue, host, etc.).

This demonstrates the application of Object-Oriented Programming (OOP) in real-world scenarios.

🖥 GUI – Java Swing
The user interface is built with Java Swing, providing a form where users can:

Input event details

View a table of all events

Select an event to update or delete

Buttons provided:

Insert – Add a new event to the database

Update – Modify an existing event

Delete – Remove an event

Display – Fetch and show all events from the SQL database

🛢 Backend – SQL Database
The project connects to a SQL database (like MySQL or SQLite) using JDBC. All operations (Insert, Update, Delete, Select) are executed through SQL queries triggered by the Swing buttons.

📦 JAR File
The project is exported as a .jar file, making it easily executable on any system with Java installed.

🔗 Features Summary
Event data storage (Name, Time, Attendees)

Java Swing UI

SQL database connectivity (JDBC)

CRUD operations

Inheritance used in class design

Packaged as .jar for easy distribution