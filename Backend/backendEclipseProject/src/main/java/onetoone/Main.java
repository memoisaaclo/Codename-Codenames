package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * @author benckell and isaaclo
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
class Main {

	public static UserRepository userRepo;

    public static GameRepository gameRepo;

    public static PlayerRepository playerRepo;
	
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
      
    }
    
 // Create 3 users with their machines
    /**
     * Isaac Lo and Ben Kelly
     * Creates instances on run
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        return args -> {
            Main.userRepo = userRepository;
            Main.playerRepo = playerRepository;
            Main.gameRepo = gameRepository;

            Player p1 = new Player(1234, true);
            Player p2 = new Player(4321, true);
            Player p3 = new Player(7777, true);
            playerRepository.save(p1);
            playerRepository.save(p2);
            playerRepository.save(p3);

            Game game = new Game(1222, "Jimmy's Game");
            game.addPlayer(p1);
            game.addPlayer(p2);
            game.addPlayer(p3);
            gameRepository.save(game);
        };
    }

    @Bean
    CommandLineRunner initUser() {
        return args -> {
            /*User user1 = new User("John", "john@somemail.com", new Date())
            userRepository.save(user3);*/
        };
    }

    /* "This Bean creates the documentation for all the APIs Which is exactly
       what is needed."
       - Some knowledgeable soul
       "He he, Bean."
       - Isaac Lo
     */
    @Bean
    public Docket getAPIdocs() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
