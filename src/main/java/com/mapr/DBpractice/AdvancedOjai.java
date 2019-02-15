package com.mapr.DBpractice;

import com.mapr.db.MapRDB;
import com.mapr.db.Admin;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.*;
import org.ojai.store.QueryCondition.Op;


public class AdvancedOjai {
    public static void main(String[] args) {

        String OJAI_CONNECTION_URL = "ojai:mapr:"; //driver for the connection
        //Full path including namespace /mapr/MyCluster/apps/OJAI01
        String tablePath = "/user/som/products";

        Connection connection = DriverManager.getConnection(OJAI_CONNECTION_URL);

        DocumentStore table = connection.getStore(tablePath);//getJSONTable(tablePath, connection);


//			Document document = threadStore.findById(thread.getId());


        Product prod1 = new Product();

        prod1.setId("prod0134522");
        //TODO 1: Add other attributes to prod1 object:
        /*set
         * Name = "Hi tech MapR pencil"
         * Description = "Gives the owner the power to debug all MapR DB bugs"
         * Selling Price = 34.56
         * Cost Price = 20.57
         */
        prod1.setName("Hi tech MapR pencil");
        prod1.setDescription("Gives the owner the power to debug all MapR DB bugs");
        prod1.setSellingPrice(34.56);
        prod1.setCostPrice(20.57);


        Document document = connection.newDocument(prod1);

        // TODO 2: insert document into the table
        table.insertOrReplace(document);

        // TODO 3: flush to the server
        table.flush();
        System.out.println("Record after insert :\t\t" + table.findById("prod0134522"));


        Document partDocument = connection.newDocument()
                .set("_id", "lead045673")
                .set("Name", "lead")
                .set("Description", "In case you run out of lead");


        Document attributeDocument = connection.newDocument()//TODO 4: create a new document, we would nest another document within this one
                .set("Size", "Huge")
                .set("Weight", "Less than yo mama")
                .set("Brand", "MapR")
                .set("Parts.lead045673", partDocument);
        //TODO 5: set other properties
        /*Set
         * Brand = "MapR"
         * Parts.lead045673 = partDocument
         * NOTE: the id field of the partDocument would disappear since the json path already has the same id in it.
         */

        DocumentMutation mutation = connection.newMutation()
                //TODO 6: Set Attributes = attributeDocument
                .set("Attributes", attributeDocument)
                .set("Manufacturer.ID", "MapR007")
                .set("Manufacturer.Name", "MapR")
                .set("Manufacturer.Address", "San Jose");

        //TODO 7: update document with _id = "prod0134522"
        table.update("prod0134522", mutation);

        //TODO 8: flush commits
        System.out.println("Record after mutation :\t\t" + table.findById("prod0134522"));
        table.flush();



			QueryCondition qCondition = connection.newCondition()
					//TODO 9: create QueryCondition object for the condition :(Name="Hi tech MapR pencil" and Cost Price >= 20.4) or Selling Price >= 100.30
					.or()
					.and()
					.is("Name", Op.EQUAL, "Hi tech MapR pencil")
					.is("Cost Price", Op.GREATER_OR_EQUAL, 20.4)
					.close()
                    .is("Selling Price",  Op.GREATER_OR_EQUAL, 100.30)
					.close()
					.build();

/*
        Query qCondition = connection.newQuery()
                //TODO 9: create QueryCondition object for the condition :(Name="Hi tech MapR pencil" and Cost Price >= 20.4) or Selling Price >= 100.30
                .where(
                        connection.newCondition()
                                .is("Name", Op.EQUAL, "Hi tech MapR pencil")
                                .build()
                                .is("Cost Price", Op.GREATER_OR_EQUAL, 20.4)
                                .build()
//                                .or()
//                                .is("Selling Price", Op.GREATER_OR_EQUAL, 100.30)
//                                .build()
                )
                .build();
*/


        try (DocumentStream documentStream = table.find(qCondition)) {

            System.out.println("Query Condition result");
            for (Document doc : documentStream) {
                System.out.println(doc.asJsonString());
            }
        }

        //TODO 10: Delete document with _id = "prod0134522", DocumentStore.delete() method
        table.delete("prod0134522");
        System.out.println("After Deletion :\t\t" + table.findById("prod0134522"));

    }

    public static DocumentStore getJSONTable(String tablePath, Connection connection) {
        Admin admin = MapRDB.newAdmin();

        DocumentStore store = null;
        if (!admin.tableExists(tablePath)) {
            admin.createTable(tablePath).close();
            store = connection.getStore(tablePath);

        } else {
            store = connection.getStore(tablePath);
        }

        return store;
    }
}
