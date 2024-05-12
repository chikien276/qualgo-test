# Take home test for Qualgo Technologies

## How to build and run

You need to install maven first. Please read the instruction [here](https://maven.apache.org/install.html).
After having maven install please run

1. Run `mvn install` to build project
2. Run `mvn -pl infrastructure spring-boot:run` to run the project
    * H2 database files will be created at `~/source/spring-boot-h2-db`
3. Access to http://localhost:8080/swagger-ui/index.html to visit project swagger
4. Register using `POST /auth/login` API
5. Then login using `POST /auth/login` API
    * The API will return JWT token. It's needed to pass as authentication header on other APIs
    * After having the token, you can use Authorize button on the UI add the token to request header
6. Use `POST /channels/create` API to create channel
7. Use `GET /channels` API to get list channel
    * Channel `id` is needed to send message to that channel
8. Use `POST /channels/{channelId}/send` API to send message to channel
9. Use `GET /channels/{channelId}/messages` to query messages from channel
10. Use `DELETE /channels/{channelId}/messages/{messageId}` to delete message from channel