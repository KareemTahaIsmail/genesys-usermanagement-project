# Genesys User Management Project
> I opted to use *Spring Boot* for the backend, as well as *DynamoDB* for the database, given that these are the technologies used by your team. I'm genuinely interested in trying out DynamoDB and have been reading a lot about it. I chose to use the local version mainly because my AWS account needed verification to create a new table, and I encountered some issues while trying to resolve that. Hoping that's okay. Would have loved to deploy everything so that you can avoid locally setting it up, but anyway... although you didn't specifically request a UI, *I decided to create a basic login/sign-up/view-users interface* just for the sake of usability. The project idea is quite straightforward and I enjoyed adding extra features. For the API, I added the functionalities you requested, left some helpful comments, and optimized it as much as I could. *Please try to break it if you want*, hoping it doesn't. 
> 
> Again, thanks for the opportunity; this was fun, and I'm looking forward to meeting the team! **- Kareem**

[![Screenshot-2024-07-26-015138.png](https://i.postimg.cc/vBdXqC5W/Screenshot-2024-07-26-015138.png)](https://postimg.cc/v13WDqqB)
[![image.png](https://i.postimg.cc/VkKdHtM4/image.png)](https://postimg.cc/CRfhRzcf)
---
### Technologies Used:
- Backend: **Spring Boot (Maven)**
- Database: **DynamoDB (local)**
- Front-end: **React (TS)**
---
### Prerequisites:
- **JDK 21 or later**
    - If you're using an older JDK version: 
        - Update lombok dependency version from **1.18.30** to **1.18.20** in the **pom.xml**.
- **AWS CLI** (Optional)
- **Node.js** (Optional if you're not planning on using the UI)
---
## Instructions to Run:
### 1. Database
- Navigate into *DynamoLocalDB/*
    - Run this **bash** command:
        ```bash 
        java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
        ```
### 2. API
- Navigate into *usermanagement/src/main/java/*
    - Run **DynamoDBInitializer.java** to create the DB table *User*.
        - Make sure you got a **'Table created successfully.'** log message to confirm.
- Then navigate into *genesys/project/usermanagement*
    - Run **Application.java** to start up the server.

### 3. UI (Optional)
- Navigate to *user-management-ui/*
    - Run this command:
        ```console
        npm i
        ```
    - Should have installed, but just in case, install these required packages:
        ```console
        npm i @mui/material @emotion/react @emotion/styled @mui/icons-material axios date-fns
        ```
    - Start it up:
        ```console
        npm start
        ```
---
## Endpoints
> **Side note:** At Vanguard we never used DELETE or PUT, they were all POST calls. We'd name the endpoint something like */users/delete* and */users/update*.
### ```GET: /users``` : Fetch all users

### ```GET: /users/random.user@email.com``` : Fetch users by email
### ```POST: /users``` : Add Users
```json
{
    "email": "random.user@email.com",
    "name": "Random User",
    "password": "randompassword"
}
```
### ```POST: /users/login``` : User Login 
```json
{
    "email": "random.user@email.com",
    "password": "randompassword"
}
```
### ```PUT: /users/random.user@email.com``` : Update user name or/and password
```json
{
    "name": "Other User"
}
```
### ```DELETE: /users/random.user@email.com``` : Delete user