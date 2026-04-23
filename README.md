# e-commerce-keycloak-store

---

### repository structure

```
e-commerce-keycloak-store/
├── .git/
├── README.md
├── e-commerce-storefront/          # Client-facing app
│   ├── .env                        # Environment variables (ignored)
│   ├── .gitignore                  # Project-specific gitignore
│   ├── pom.xml                     # Maven config
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vbforge/client/
│   │   │   │   ├── config/         # Spring configuration classes
│   │   │   │   ├── controller/     # Web controllers
│   │   │   │   ├── dto/            # Data Transfer Objects
│   │   │   │   ├── exception/      # Custom exceptions and handlers
│   │   │   │   ├── entity/         # JPA entities
│   │   │   │   ├── mapper/         # Mapper for DTO and entities
│   │   │   │   ├── repository/     # JPA repositories
│   │   │   │   ├── service/        # Business logic
│   │   │   │   └── StorefrontApp.java
│   │   │   ├── resources/
│   │   │   │   ├── static/         # CSS, JS, images
│   │   │   │   ├── templates/      # Thymeleaf templates
│   │   │   │   └── application.yml # App config
│   │   └── test/                   # Unit/integration tests
│   └── logs/                       # Log files (ignored)
└── e-commerce-admin/               # Admin app (similar structure)
    ├── .env
    ├── .gitignore
    ├── pom.xml
    ├── src/
    │   ├── main/
    │   │   ├── java/com/vbforge/admin/
    │   │   │   ├── config/         # Spring configuration classes
    │   │   │   ├── controller/     # Web controllers
    │   │   │   ├── dto/            # Data Transfer Objects
    │   │   │   ├── exception/      # Custom exceptions and handlers
    │   │   │   ├── entity/         # JPA entities
    │   │   │   ├── mapper/         # Mapper for DTO and entities
    │   │   │   ├── repository/     # JPA repositories
    │   │   │   ├── service/        # Business logic
    │   │   │   └── ECommerceAdminApplication.java
    │   │   ├── resources/
    │   │   │   ├── static/
    │   │   │   ├── templates/
    │   │   │   └── application.yml
    │   └── test/
    └── logs/
```

---
### Keycloak flow setup and start

1) download: keycloak-26.6.1
2) extract all
3) change config for mysql instead default H2
4) create db in workbench
5) to start from bin folder: `path\keycloak-26.6.1\bin>` run this command: `.\kc.bat start-dev`
6) go to: http://localhost:8080/


---
### applications running ports:

- keycloak running on port               http://localhost:8080/
- client's application running on port   http://localhost:8081/
- admin's application running on port    http://localhost:8082/
