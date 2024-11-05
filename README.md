## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Note:
- `settings and launch`: please change the project name in these files
- `sql`: please install postgresql

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies). For usability, all the jar files of dependencies has been included in src

Database Structure

This database schema is designed to manage user profiles, coaches, bank accounts, and gear for a system. Below is a breakdown of each table, its columns, and relationships.

Tables Overview
Bank Table (bank)

Stores bank account information for users.
Columns:
accntno (VARCHAR, primary key): Unique identifier for each bank account.
accntpass (VARCHAR): Password associated with the bank account.
bank_balance (DECIMAL): Current balance in the bank account.
Coaches Table (coaches)

Stores details of coaches available to users.
Columns:
coachname (VARCHAR, primary key): Name of the coach.
coachexperience (INT): Number of years of experience the coach has.
coachmail (VARCHAR): Email address of the coach.
cost (DECIMAL): Cost of hiring the coach.
imagepath (VARCHAR): Path to the coach's image.
User Coaches Table (usercoaches)

Tracks the coaches each user has selected.
Columns:
usermail (VARCHAR, foreign key from usercred): Email address of the user.
coachnames (VARCHAR): Comma-separated list of coach names associated with the user.
User Credentials Table (usercred)

Stores core information about each user.
Columns:
usermail (VARCHAR, primary key): Email address of the user.
userpass (VARCHAR): Password for the user's account.
age (INT): User's age.
experience (INT): Years of experience the user has (optional).
gender (VARCHAR): User's gender.
weight (DOUBLE): User's weight.
height (DOUBLE): User's height.
points (INT): Points accumulated by the user.
User Gear Table (usergear)

Tracks details of gear owned and used by users.
Columns:
usermail (VARCHAR, foreign key from usercred): Email address of the user.
gearname (VARCHAR): Name of the gear.
geartype (VARCHAR): Type of gear.
lastkm (DOUBLE): Last recorded kilometer usage of the gear.
boughtdate (DATE): Purchase date of the gear.
Relationships and Constraints
Foreign Key Constraints:

The usermail field in usercoaches and usergear references the usermail field in the usercred table, establishing a relationship between users and their coaches/gear.
Automatic Row Insertion:

When a new row is added to the usercred table (i.e., when a new user is created), a corresponding empty row is automatically added to the usercoaches table, preparing it for future coach selections by that user.
Purpose
This schema provides a robust structure for managing user profiles, associated bank accounts, available coaches, and gear usage. It maintains relational integrity and supports easy lookups and updates across the various tables, essential for an application managing profiles and tracking user interactions with coaches and gear.
