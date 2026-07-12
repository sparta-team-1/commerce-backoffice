package org.example.commercebackoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CommerceBackofficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceBackofficeApplication.class, args);
    }

}
