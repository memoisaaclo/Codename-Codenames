package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author Vivek Bengre
 * 	SQL is on branch 22
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

	public static UserRepository userRepo;
	
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
      
    }
    
 // Create 3 users with their machines
    /**
     * 
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
        	Main.userRepo = userRepository;

        };
    }
}
