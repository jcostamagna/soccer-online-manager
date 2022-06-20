# Soccer Online Manager Api

![technology Gradle](https://img.shields.io/badge/technology-Gradle-blue.svg)

This is a Java Gradle application where football ⚽️ fans can create fantasy teams and are be able to sell or buy players among them.

## Considerations

- There were some Assumptions and Questions validated.
- They are listed in "Comments" tab of [Soccer Online Manager Api [Backlog] Spreadsheet](https://docs.google.com/spreadsheets/d/10PDKVjHKCEhBqNc3emg6Yc1qKbk35tHdtCSHZQDbiCY/edit?usp=sharing)
- Also you can find there all the Backlog (Tasks) from the project and the process including a BurnDown Chart where you can see the daily progress.
- All the functional requirements were developed.
- All the inputs validations were developed. 
- For Country validations the API takes valid country names from Locale library (Java), using english lenguage with a capital letter in the first letter.

# Docker Compose

## Run the System
We can run the whole with only a command:
```bash
docker-compose up
```


The services can be run on the background with command:
```bash
docker-compose up -d
```

## Stop the System
Stopping all the running containers is also with a single command:
```bash
docker-compose down
```

If you need to stop and remove all containers, networks, and all images used by any service in <em>docker-compose.yml</em> file, use the command:
```bash
docker-compose down --rmi all
```

## How to run the application locally

1. Install [Java 11](https://oracle.com/ar/java/technologies/javase/jdk11-archive-downloads.html) and [Gradle 7.4.1](https://docs.gradle.org/7.4.1/release-notes.html)

2. Download the [Soccer Online Manager Api](https://git.toptal.com/screening/Juan-Costamagna-2) GitLab repository

3. Configure a Postgresql DataBase with dev properties:

   `spring.datasource.url=jdbc:postgresql://localhost:5432/soccerapi`

   `spring.datasource.username=soccerapi`

   `spring.datasource.password=123`

4. Go to the project's root and run:

   `./gradlew clean build`

   `java -jar build/libs/soccer-online-manager-0.0.1.jar com.jcostamagna.socceronlinemanager.SoccerOnlineManagerApplication`

## API Endpoints

### User Sign Up

```
POST /users/sign_up
```
Body:
```json
{
   "email": "user_email@gmail.com",
   "password": "1234"
}
```

it returns same user with a generated Team and Players


### User Log in
```
POST /users/log_in
```
Body:
```json
{
   "email": "user_email@gmail.com",
   "password": "1234"
}
```

it returns same user details with JWT in header Set-Cookie.

### Get Team Information
```
GET /teams
```

Authorization Header or Cookie required ("soccerOnlineAuth": "jwt.token")

It returns the Team and Players of the user logged.

### Get All Transfers Offers
```
GET /transfers
```

It returns the list of Players in the transfer list.

### Set player to transfer list
```
POST /transfers/set_player
```

Body:
```json
{
   "player": 1,
   "price": 2000000
}
```

Body with player id and price requested.

Authorization Header or Cookie required ("soccerOnlineAuth": "jwt.token")

It returns the Transfer Offer created.

### Buy player to transfer list
```
POST /transfers/buy_player
```

Body:
```json
{
   "player": 1
}
```

Body with player id.

Authorization Header or Cookie required ("soccerOnlineAuth": "jwt.token")

It returns the Transfer Offer bought it and deleted from transfer list.

### Update Team
```
PUT /teams/40
```

Body:
```json
{
   "name": "Barcelona",
   "country": "Argentina"
}
```

Body with Team name and country. Both are required.

Authorization Header or Cookie required ("soccerOnlineAuth": "jwt.token")

It returns Team Information.

### Update Player
```
PUT /teams/40
```

Body:
```json
{
   "firstName": "Juan",
   "lastName": "Gomes",
   "country": "Venezuela"
}
```

Body with Player first name, last name, and country. All are required.

Authorization Header or Cookie required ("soccerOnlineAuth": "jwt.token")

It returns Player Information.

### Get All Users (Only for dev mode)
```
GET /users
```

It returns a list of users.


## Questions

* [costamagna.jic@gmail.com](costamagna.jic@gmail.com)