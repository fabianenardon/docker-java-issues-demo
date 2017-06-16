
package com.mycompany.integrationtests.sample;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * Integration test. This test should run inside docker
 * with docker exec.
 * 
 * @author fabiane
 */
public class AppIT {
    
    private static final String DOCKER_MONGO_HOST = "mongo";
    private static final int DOCKER_MONGO_PORT = 27017;
    
    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testSave() throws Exception {
        MongoClient mongo = new MongoClient(DOCKER_MONGO_HOST, DOCKER_MONGO_PORT);
        Morphia morphia = new Morphia();
        Datastore ds = morphia.createDatastore(mongo, App.DATABASE);
        try {
            
            MongoCursor<Document> result = ds.getMongo()
                                         .getDatabase(App.DATABASE)
                                         .getCollection(App.COLLECTION).find().limit(1).iterator();
            assertTrue(result.hasNext());
            assertEquals("Hello World", result.next().get("text"));
            System.out.println("ALL TESTS PASSED.");
        } finally {
            ds.getMongo().close();
        } 
    }
    
}
