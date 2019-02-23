MongoClientURI uri = new MongoClientURI( "mongodb://xxxxxx" );
MongoClient mongoClient = new MongoClient(uri);

final MongoDatabase database = mongoClient.getDatabase("test");

Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        MongoCollection<Document> collection = database.getCollection("col");
        Document doc = new Document();
        doc.put("name", "tommy");
        doc.put("telephone", Arrays.asList("123", "456"));
        collection.insertOne(doc);
    }
});
thread.start();
