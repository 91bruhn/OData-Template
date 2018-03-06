import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by bruhn on 10.12.2017.
 */
public class Connection {

    public static void main(String[] args) {
        MongoClient client = handleConnection();
        List<String> dbs = client.getDatabaseNames();
        for (String db : dbs) {
            System.out.println(db);
        }

//        todo now veraltet DB: MongoDatabase database = mongoClient.getDatabase(“myMongoDb”)

        //get collection/table
        DB db = client.getDB("testdb2");
        DBCollection table = db.getCollection("user");

        //Display all collections from selected database.
        db = client.getDB("testdb3");
        Set<String> tables = db.getCollectionNames();

        for (String coll : tables) {
            System.out.println(coll);
        }

//        saveExample(db);
        findExample(db);
    }

    public static MongoClient handleConnection() {
        MongoClient mongoClient = null;
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("testMongo_DB");
        //not necessary  -  boolean auth = db.authenticate("username", "password".toCharArray());
        return mongoClient;
    }

    /**
     * Save a document (data) into a collection (table) named “user”.
     */
    public static void saveExample(MongoDatabase db) {
        MongoCollection<Document> table = db.getCollection("user");
        Document document = new Document();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());
        table.insertOne(document);
    }

    /**
     * Update a document where “name=mkyong”.
     */
    public static void updateExample(DB db) {
        DBCollection table = db.getCollection("user");

        BasicDBObject query = new BasicDBObject();
        query.put("name", "mkyong");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", "mkyong-updated");

        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);

        table.update(query, updateObj);
    }

    /**
     * Find document where “name=mkyong”, and display it with DBCursor
     */
    public static void findExample(DB db) {
        DBCollection table = db.getCollection("user");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    /**
     * Find document where “name=mkyong”, and delete it.
     */
    public static void deleteExample(DB db) {
        DBCollection table = db.getCollection("user");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        table.remove(searchQuery);
    }
}
