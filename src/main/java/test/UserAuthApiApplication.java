package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration
public class UserAuthApiApplication {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){ return new BCryptPasswordEncoder(); }
	public static void main(String[] args) {
		SpringApplication.run(UserAuthApiApplication.class, args);
	}

}