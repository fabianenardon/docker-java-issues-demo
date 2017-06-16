package com.mycompany.integrationtests.sample;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * Simple Mongo example. Saves a text to MongoDB.
 * 
 * @author fabianenardon
 */
public class App {
    
    public static final String DATABASE = "testdb";
    public static final String COLLECTION = "hello";
    
    public App() {
    }


    /**
     *
     * @param args
     *
     */
    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.err.println("MongoDB server not informed.");
            System.exit(1);
        }

        String mongoHost = args[0];
        
        MongoClient mongo = new MongoClient(mongoHost);
        Morphia morphia = new Morphia();
        Datastore ds = morphia.createDatastore(mongo, App.DATABASE);
        try {
            
            Document document = new Document();
            document.append("text", "Hello World");
            
            ds.getMongo()
              .getDatabase(App.DATABASE)
              .getCollection(App.COLLECTION).insertOne(document);

        } finally {
            ds.getMongo().close();
        } 

        
    }

}
