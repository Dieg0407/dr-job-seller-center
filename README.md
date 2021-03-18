### Documentation
Swagger can be found in route /swagger-ui.html

### Configuration
Application properties:
```properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/seller_center
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto = validate

app.pubsub.subscription=projects/sellercenter-308001/subscriptions/test-topic-sub
app.inretail.url=https://inretail.mysellercenter.com/
app.inretail.login.path=sellercenter/api/v1/oauth/token
app.inretail.login.grant-type=password
app.inretail.login.username=admin@intercorp.com
app.inretail.login.password=<PATH>
app.inretail.login.authorization=<AUTH>
app.inretail.seller.path=sellercenter/api/v1/admin/sellers
```

As env variables
```env
spring_datasource_url=jdbc:postgresql://127.0.0.1:5432/seller_center
spring_datasource_driverClassName=org.postgresql.Driver
spring_datasource_username=postgres
spring_datasource_password=1234
spring_jpa_database-platform=org.hibernate.dialect.PostgreSQLDialect
spring_jpa_hibernate_ddl-auto=validate


app_pubsub.subscription=projects/sellercenter-308001/subscriptions/test-topic-sub
app_inretail_url=https://inretail.mysellercenter.com/
app_inretail_login_path=sellercenter/api/v1/oauth/token
app_inretail_login_grant-type=password
app_inretail_login_username=admin@intercorp.com
app_inretail_login_password=<PASS>
app_inretail_login_authorization=<AUTH>
app_inretail_seller_path=sellercenter/api/v1/admin/sellers

GOOGLE_APPLICATION_CREDENTIALS=/opt/configurations/credentials/sellercenter-dev.json
```