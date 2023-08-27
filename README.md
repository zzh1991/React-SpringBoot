# React-SpringBoot

[简体中文](README-zh.md)

<div align="center">
  <div align="center">
    <img src="https://badges.toozhao.com/badges/01EH2EP6R07GDNK946RERW9D1B/green.svg" />
    <a href="https://github.com/zzh1991/React-SpringBoot/blob/master/LICENSE">
      <img src="https://img.shields.io/github/license/zzh1991/React-SpringBoot.svg">
    </a>
    <a href="#">
      <img src="https://img.shields.io/github/languages/top/zzh1991/React-SpringBoot.svg">
    </a>
  </div>
</div>



[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fzzh1991%2FReact-SpringBoot.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fzzh1991%2FReact-SpringBoot?ref=badge_large)

## Demo: [Movie Helper](http://movie.zzhpro.com)

### Desktop display

![Desktop](./pictures/recent-movie-2007.png)

Recently, I improved the performance from 50 to 90.

![Page Test Insights](./pictures/page-speed-insights.png)

### Mobile display

<div align="center"><img src="./pictures/mobile-2007.png" alt="Mobile" width="400px" /></div>

## Git clone repo

Back-end project: [React-SpringBoot](https://github.com/zzh1991/React-SpringBoot)

Front-end project: [movie-helper-front](https://github.com/zzh1991/movie-helper-front)

```bash
git clone --recursive git@github.com:zzh1991/React-SpringBoot.git

# or
git clone git@github.com:zzh1991/React-SpringBoot.git
git submodule init
git submodule update
```

## 🚀 Usage

### Support Java 17 and Spring Boot 3

```bash
# add VM options
--add-opens java.base/java.lang.invoke=ALL-UNNAMED
```

### Start application

#### configure database

> configure your database name (an empty database), user, password in [src/main/resources/application.yml](https://github.com/zzh1991/React-SpringBoot/blob/master/src/main/resources/application.yml)

You could use PostgreSQL(default configuration) or MySQL. Also, you could use H2 database.

##### MySQL configuration

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/movie?allowPublicKeyRetrieval=true&useConfigs=maxPerformance&useSSL=false&serverTimezone=UTC&characterEncoding=utf8
    password: mysql
    username: root
    initialization-mode: never

  flyway:
    enabled: true
    baseline-on-migrate: true
    validate-on-migrate: false
    locations: classpath:db/migration/mysql
```

> remove postgres dependency and add mysql dependency in pom.xml

```xml
<!-- remove -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<!-- add -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

#### Configure Front

- `cd front`
- `yarn`: install dependency
- prepare front static files
  - dev mode
    - `yarn start`
  - Deploy mode
    - `yarn deploy`
- start the web app in IDE
- open browser
  - `dev mode`: go to http://localhost:3000
  - `deploy mode`: go to http://localhost:8080

### Update submodule for front end

```bash
cd front
git fetch origin master
git rebase origin/master

# or
git submodule update --remote front
```

## Java doc

```bash
mvn javadoc:javadoc
```

> open `target/site/apidocs/index.html` by browser

### Reference

- [recent movies](https://movie.douban.com/)
- [top movies](https://movie.douban.com/top250?start=0&filter=)

## Todo

### Improvement

- [x] sync recent / top movies
- [x] latest sync time
- [x] search by movie name
- [x] database upgrade
- [x] schedule task
- [x] java doc
- [x] lazy load
- [x] use timestamp with time zone

## Dependency

### [Front end](https://github.com/zzh1991/movie-helper-front)

### Backend

- Spring Boot 2
- PostgresQL
- Guava
- Swagger2
- Flyway
- Prometheus
- GraphQL
- Mybatis
- Mybatis-plus

## Endpoints

### Swagger UI

- http://localhost:8080/swagger-ui/index.html

### Monitoring Endpoint

- http://localhost:8080/actuator
- http://localhost:8080/actuator/prometheus

### GraphiQL Endpoint

- http://localhost:8080/graphiql

## Configuration

### Hot deploy for Intellij IDEA

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

- setting -> compiler -> make project automatically
- ctrl + shift + a -> registry -> auto make enabled

### Active dev profile

1. edit run/debug configuration
2. add environment variables: spring.profiles.active=dev

### Dependency Version Management

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>versions-maven-plugin</artifactId>
</plugin>
```

#### Display update

```bash
mvn versions:display-dependency-updates
```

#### Update dependency

```bash
mvn versions:use-next-releases

mvn versions:use-latest-releases
```

## Problems

### Migrate flyway 3.x to 5.x issue

> firstly should migrate to **4.2.0** and then 5.x

### H2 database conflict with `druid`?

### Mybatis `timestamptz` could not convert to `timestamp`?

> Refer to https://github.com/mybatis/mybatis-3/issues/1644

1. copy `LocalDateTimeTypeHandler.java` into project
2. configure `LocalDateTimeTypeHandler` bean

## Author

👤 **Zhihao Zhang**

- Github: [@zzh1991](https://github.com/zzh1991)

## Show your support

Please ⭐️ this repository if this project helped you!

## 📝 License

Copyright © 2023 [zzh1991](https://github.com/zzh1991).<br />
This project is [MIT](https://github.com/zzh1991/React-SpringBoot/blob/master/LICENSE) licensed.