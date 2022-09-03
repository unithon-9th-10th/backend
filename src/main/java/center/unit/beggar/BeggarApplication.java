package center.unit.beggar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BeggarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeggarApplication.class, args);
	}

}
