package com.italo.TaskBrother.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv(){
        try{
            Dotenv dotenv = Dotenv.configure()
                    .filename(".env.local")
                    .ignoreIfMissing()
                    .load();

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });
        }catch(Exception e){
            System.err.println("Erro ao carregar .env.local: " + e.getMessage());
        }
    }

    @PostConstruct
    public void teste(){
        System.out.println("Isso é um teste");
    }
}
