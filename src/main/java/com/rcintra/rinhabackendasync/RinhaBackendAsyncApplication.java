package com.rcintra.rinhabackendasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/*
https://github.com/leandronsp/rinha-backend-ruby/
 */

@SpringBootApplication
@EnableCaching
public class RinhaBackendAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(RinhaBackendAsyncApplication.class, args);
    }

}
