# maprdb_ojai_basic_lesson5


1. Create Mapr DB table using following command.

    ```maprcli create table -path /user/som/products -tabletype json```

2. Build project

    ```mvn clean install```

3. Copy to cluster

    ```scp ./target/DEV3300-AdvancedOJAI-1.1-SNAPSHOT.jar <cluster>:/tmp```

4. run the application

    ```java -cp /tmp/DEV3300-AdvancedOJAI-1.1-SNAPSHOT.jar:`mapr classpath` com.mapr.DBpractice.AdvancedOjai```



Steps to Run from IDE : 
***********************

1. Keep the mapr-ojai-driver dependency alone as ojai dependencies and make sure to match with cluster version.

        <dependency>
			<artifactId>mapr-ojai-driver</artifactId>
			<groupId>com.mapr.ojai</groupId>
			<version>6.1.0-mapr</version>
		</dependency>

2. Generate mapr credentials of ticket at client node. 

    maprlogin password -user som
    
    
