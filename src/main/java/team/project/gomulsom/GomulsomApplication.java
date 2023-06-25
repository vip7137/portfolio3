package team.project.gomulsom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GomulsomApplication {

	public static void main(String[] args) {
		SpringApplication.run(GomulsomApplication.class, args);
	}

}
