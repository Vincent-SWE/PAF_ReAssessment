package ibf2022.batch2.paf.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class AppConfig {
    
    @Value("${mongo.url}")
    private String mongoUrl;

    @Bean
    public MongoTemplate createMongoTemplate() {

        MongoClient mongoClient = MongoClients.create(mongoUrl);

        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "restaurantDb");

            return mongoTemplate;

    }

    

}
