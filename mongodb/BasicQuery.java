import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

MongoClientURI uri = new MongoClientURI( "mongodb://xxxxxxxxx" );
MongoClient mongoClient = new MongoClient(uri);

final MongoDatabase database = mongoClient.getDatabase("test");

Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        MongoCollection<Document> collection = database.getCollection("col");
        Document myDoc = collection.find().first();
        Log.i("xxx", myDoc.toJson());
    }
});
thread.start();
