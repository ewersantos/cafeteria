package com.github.ewersantos.cafeteria;

import com.github.ewersantos.cafeteria.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CafeteriaApplication {

    @Bean
    public CommandLineRunner run( @Autowired ProdutoRepository repository ){
        return args -> {
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(CafeteriaApplication.class, args);
    }
}
