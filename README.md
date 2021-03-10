

# Spring Server Challenge

-------------

This project was an entry for the **Alkemy Challenge**, the quota was to build a school management database, server and webpage in ~10 days with java Spring.

The hand-in commit is marked with a release tag.  
Future fixes for the known issues and general improvements are expected.  

Made without prior knowledge of Spring, its modules, Thymeleaf and the frontend workflow of jQuery and Bootstrap.   
Because of this, this project should be regarded as my primer into web development, issues are bound to happen and bound to be fixed in non-standard ways. The goal of this repository is to eventually learn and correct them without the urgency of deadlines.

### Building and running

The current release expects a mySQL database on port 3306 (mysql default)  

Content is served through port 8080 by default

Made with java 11 (adopt openJDK), spring 2.4.3, and managed with maven

Search github releases for the .jar, and check application.properties to change these defaults.


### Known issues

* No Custom error handling template

* Models shouldn't have validation logic inside them (?)

* Entities relationships have Eager fetching and lack of general optimization such as caching and persistence.

* Using spring boot default configurations

* No automatic testing


--------------------

##### Notes

Administrator login
* Username: 'Admin'    
* Password: 'admin', 'root' or 'foo'

User login
* Username: record number of student
* Password: 'DNI' of student