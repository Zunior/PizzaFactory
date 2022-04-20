# PizzaFactory
Spring boot project

Features:
1. Spring boot crud operations
2. Spring security implementation
3. H2 embedded database
4. Swagger implementation
5. Lombok with build pattern implementation on main dto object

All required links can be found in application.properties file
- h2 console  
- jdbc url for db login through h2 console
- swagger

Creation of jar file:  
Run As > Maven build > <name>: clean package > confirm  
Location: "target" directory  
  
In src>main>resources>static  is postman collection for testing api:  
Postman steps:  
  1. register user
  2. login user
     In response is new token string, which is needed to put in every other test step
  3. secure point
     Test authentication
  
Get pizzas method can accept call without authentications, others can not.

There are couple of integration and partial mocked unit tests
