# walkingfish_spring

## Configuration

### MySQL with Docker
Install mysql image and run the container :

```bash
docker run --name mysql -p3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
```

Then, in the `application-mysql.properties` file, adapt the following fields :

```
spring.datasource.username=root
spring.datasource.password=my-secret-password
```

### ActiveMQ with Docker

Run the latest ActiveMQ container :

```bash
docker pull rmohr/activemq
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
```

### How to build and run the app

```bash
./mvnw clean install
./mvnw spring-boot:run

# or

java -jar target/<app-name>.war
```

## Sources
### Configuration + Deployment on Jelastic Cloud

https://www.virtuozzo.com/company/blog/kubernetes-public-ip-address/
https://www.virtuozzo.com/application-platform-docs/public-ip/?lang=en
https://www.virtuozzo.com/application-platform-docs/custom-domains/#how-to-configure-dns-record

https://www.virtuozzo.com/application-platform-docs/connection-to-mysql/
https://www.virtuozzo.com/application-platform-docs/connect-db-hibernate/
https://stackoverflow.com/questions/43328762/how-to-deploy-spring-boot-application-with-postgresql-on-the-cloud-jelastic
https://www.virtuozzo.com/company/blog/configure-database-connectivity-in-jelastic/
https://www.virtuozzo.com/application-platform-docs/maven-plugin/
https://www.virtuozzo.com/application-platform-docs/database-connection-strings/
https://www.virtuozzo.com/application-platform-docs/endpoints/?lang=en

### Files uploaded + save on server
https://stackoverflow.com/questions/27909583/write-a-file-to-resources-folder-in-spring
https://stackoverflow.com/questions/18664579/recommended-way-to-save-uploaded-files-in-a-servlet-application
https://stackoverflow.com/questions/2422468/how-can-i-upload-files-to-a-server-using-jsp-servlet
https://www.bezkoder.com/spring-boot-file-upload/
https://stackoverflow.com/questions/10847994/spring-mvc-save-uploaded-multipartfile-to-specific-folder
https://stackoverflow.com/questions/28268499/understanding-multipartfile-transferto-method-in-spring-mvc

### Pagination
https://www.baeldung.com/spring-thymeleaf-pagination

### Testing
https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/

### Carousel
https://splidejs.com/tutorials/thumbnail-carousel/

### Max file size
https://spring.io/guides/gs/uploading-files/#:~:text=max%2Dfile%2Dsize%20is%20set,spring.