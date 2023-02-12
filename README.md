# Endpoints-monitoring-service

To start app run  **docker-compose up** in the folder EndpointsMonitoring.

It will start MySql database* and web service for endpoints monitoring. Database will be automatically prefilled with 2 users: Applifting and Batman. 
(Not the bast idea to store their access tokens directly in the code, but in a test task and just for the sake of simplisity I think it is acceptable :) )

Swagger UI is available under url **localhost:8080/swagger-ui/index.html** 

For authentication use HTTP header **accessToken**

\*DB is being persisted on C:\MySql. In case you want to change the path - you can do it in docker-compose.yml. Or you can remove *my_sql_volume* completely from *volumes* of *mysql*.
