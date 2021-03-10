package snttgr.alkemy.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class Application {

	public static final boolean DEBUG = false;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
