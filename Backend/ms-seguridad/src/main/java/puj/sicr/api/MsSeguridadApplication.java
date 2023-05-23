package puj.sicr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"puj.sicr.*"})
@EntityScan(basePackages = {"puj.sicr.entidad"})
@EnableJpaRepositories(basePackages = {"puj.sicr.repository"})

public class MsSeguridadApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsSeguridadApplication.class, args);
	}

}
