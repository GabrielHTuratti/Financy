package com.turatti.financy.data.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    private final String connectionString = "mongodb+srv://yPestiss:devJunior_J8hgFJylHkbGkGc9@financydb.jz0yv.mongodb.net/?retryWrites=true&w=majority&appName=FinancyDB";

    @Override
    protected String getDatabaseName(){
        return "MDB_Financy_OPS";
    }

    private MongoClient mongoClient;
    @SuppressWarnings("null")
    @Bean
    @Override
    public MongoClient mongoClient(){
        ConnectionString connString = new ConnectionString(connectionString);
        ServerApi svAPI = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connString)
        .serverApi(svAPI)
        .build();
        return MongoClients.create(settings);
    }
    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Bean
    public MongoClient getMongoClient(){
        return mongoClient;
    }

}
