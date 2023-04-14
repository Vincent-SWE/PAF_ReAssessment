package ibf2022.batch2.paf.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    
    @Value("${MONGO_URL}")
    private String connectionString;

    @Value("${DATABASE}")
    private String dataBase;

    private MongoClient client = null;


    @Bean
    public MongoClient mongoClient() {
        System.out.println(connectionString);
        if (null == client)
            client = MongoClients.create(connectionString);
        try {
            client.listDatabaseNames();

            System.out.println("Was able to connect to MongoDB");
        } 
        catch (Exception e) {
            System.err.println("Could not connect to MongoDB" + e.getMessage());

        }

        return client;


    }


    @Value("${mongo.url}")
    private String mongoUrl;

    @Bean
    public MongoTemplate createMongoTemplate() {

        MongoClient mongoClient = MongoClients.create(mongoUrl);

        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "restaurantDb");

            return mongoTemplate;

    }

    

}
