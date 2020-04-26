package de.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "de.webshop")
public class Webshop {

    public static void main(String[] args) {
        SpringApplication.run(Webshop.class, args);
    }

}