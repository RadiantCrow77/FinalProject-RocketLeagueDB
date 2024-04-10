Use this file to help test endpoints through API:
"ERD_plus.rubric.png" is helpful to help visualize the project.

1. POST a player  -- works as of 4/9
http://localhost:8080/rocketleagueserver/player
Body:
{
"playerName": "Donald Duck",
"hoursPlayed": 7001,
"playerAnthem": "Mister Saxobeat"
}

2. GET/READ all players -- works 4/9
http://localhost:8080/rocketleagueserver/player

3. GET/READ player by ID -- works 4/9
http://localhost:8080/rocketleagueserver/player/1

4. PUT/UPDATE player by ID -- works 4/9
http://localhost:8080/rocketleagueserver/player/1
Body:
{
"playerName": "Donald Duck",
"hoursPlayed": 70024,
"playerAnthem": "Mister Saxobeat"
}

5. POST a car to a player by ID -- works 4/9
http://localhost:8080/rocketleagueserver/player/1/car
{
"carBodyName": "Mitsubishi Eclipse",
"carColor": "Gray",
"carPaintFinish": "Standard"
}

6. GET/READ all cars for a player -- works 4/9
http://localhost:8080/rocketleagueserver/player/1/car

7. -- DELETE all cars in garage -- DO NOT ALLOW --- not allowed on purpose -- works 4/6
http://localhost:8080/rocketleagueserver/player/1/car

8. GET one car via player ID and car ID -- works 4/9
this one grabs the car with ID = 3
http://localhost:8080/rocketleagueserver/player/1/car/3

9. PUT / CHANGE an aspect of a car by ID  -- works, 4/9 -- note: DOES NOT work for cars that are not directly added to the player (you can either POST a new car to the player or assign manually in DBeaver by editing)
http://localhost:8080/rocketleagueserver/player/1/car/3
// player ID = 1, car ID = 3
{
"carColor": "Orange" 
}

10. DELETE a car by ID -- works 4/9
http://localhost:8080/rocketleagueserver/player/1/car/4

11. READ all ranks -- works 4/9
http://localhost:8080/rocketleagueserver/rank

12. POST rank by car ID -- works 4/9
http://localhost:8080/rocketleagueserver/car/{carId}/rankEarned
{
"rankId": 1
}

13. GET current rank earned by ONE car -- 4/9: not yet implemented, stretch goal
http://localhost:8080/rocketleagueserver/car/{carId}/rankEarned
