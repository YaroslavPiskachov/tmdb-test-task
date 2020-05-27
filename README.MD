For start app: 
1. Upadte tmdb.apikey in src\main\resources\application.properties
2. Run mvn clean install
3. Run mvn spring-boot:run
4. Open http://localhost:8080/swagger-ui.html 
5. Register user with POST http://localhost:8080/user/register with body: 
    {
        "email":"test_email",
        "password":"test_password"
    }
6. All other request are secured by stateless session basic auth so use creds above