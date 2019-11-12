# javaee-webservice-example
Example Web App that demonstrates a simple REST (JAX-RS) functionality

 - Start by running the [initDB.sql](https://github.com/teohaik/javaee-webservice-example/blob/development/src/main/sql/initDB.sql) for setting up a table and some sample data.
 - Set your jta-data-source in persistence.xml
 - mvn clean install
 - deploy the war to your application server. Currently there are deployment descriptors for Weblogic 12c and Wildfly 
