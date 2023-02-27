
# EmailMicroService

EmailMicroService is an application that allows you to store users email addresses from which you can send a message from an external source using SMTP API. The application provides a REST API that allows CRUD operations on users email addresses and sending messages to all recipients from the database.





## Requirements
To run the application, you must have JRE version 8 or later and a MySQL database installed.

## How to run the application
1. Clone the repository to your local directory:
git clone https://github.com/krespox/Email_Microservice.git

2. Create a database with two fields id and email address of the user
3. Open the application.properties file and set the database access data.
4. Launch the application using the command:
   spring-boot:run in command line
5. The application will be available at http://localhost:8080


## REST API
Get all email addresses
```http
  GET /email/all

  [
  {
    "id": 1,
    "email": "sample@gmail.com"
  },
  {
    "id": 2,
    "email": "ola.jank@o2.com"
  }
]
```
Get specific email addresses
```http
  GET /email/{id}
  {
    "id": 1,
    "email": "sample@gmail.com"
  }

```
Add all email addresses
```http
  POST /email/{id}

  [
  {
    "id": 1,
    "email": "sample@gmail.com"
  }
]
```
Add specific email addresses
```http
  POST /email/{id}
  
  {
    "id": 1,
    "email": "sample@gmail.com"
  }

```
Delete all emails addresses
```http
  DELETE /email/all

   [
  {
    "id": 1,
    "email": "sample@gmail.com"
  },
  {
    "id": 2,
    "email": "ola.jank@o2.com"
  }
]
```
Update all emails addresses
```http
  PUT /email/all

   [
  {
    "id": 1,
    "email": "olajank@gmail.com"
  },
  {
    "id": 2,
    "email": "ola.ala@o2.com"
  }
]
```
Send message to all emails addresses
```http
POST /email/send
Content-Type: application/json

{
  "subject": "Tittle",
  "message": "Content"
}
```

