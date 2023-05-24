package puj.sicr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@ComponentScan({"puj.sicr.*"})
@EntityScan(basePackages = {"puj.sicr.entidad"})
@EnableJpaRepositories(basePackages = {"puj.sicr.repository"})
public class MsPagosApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsPagosApplication.class, args);
	}

}
