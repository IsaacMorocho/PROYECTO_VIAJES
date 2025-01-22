package org.example;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Collections;
public class ConexionMongo {
    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE = "TravelBuddy";

    public static MongoDatabase getDatabase() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(HOST, PORT))))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase(DATABASE);
    }
}
