User service

Description
    User service persists user details.
    Finds user by first name or last name. Also lists all users data in paginated manner.

Design approach :
    Used H2 in memory database to persist records
    Segregated repository & transaction level check
    Set up interface & service layer for better segregation of business logic & entities
    Handles exception in service via exception handlers, handled conflicts via optimistic lock exception check
    to avoid any inconsistent data state
    Created object mapper configuration to be reused across
    Standardized response structure in case of failure


Edge cases handled :
    Two users trying to update a same record will be met with object optimistic lock exception error, thus avoiding
    inconsistent data state
    For reading data, no lock is kept on record/row

Validations :
    On request body, input validations are kept via annotations
    Logical fail fast checks on business logic 

Code structure :
    Spotless plugin used to standardize code layout
    mvn spotless:apply

Run :
    dependencies
        java 17 env
        spring boot version 4.0.5

    commands
        mvn clean install -U
        mvn spring-boot:run

    
    
