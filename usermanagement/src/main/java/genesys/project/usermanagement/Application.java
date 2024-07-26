package genesys.project.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;

@SpringBootApplication()
@EnableDynamoDBRepositories(basePackages = "genesys.project.usermanagement.repos")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
