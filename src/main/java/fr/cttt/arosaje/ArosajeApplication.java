package fr.cttt.arosaje;

import fr.cttt.arosaje.model.Role;
import fr.cttt.arosaje.service.RoleService;
import fr.cttt.arosaje.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ArosajeApplication implements CommandLineRunner {
	private final RoleService roleService;

	private final StorageService storageService;

	public ArosajeApplication(RoleService roleService, StorageService storageService) throws IOException {
		this.roleService = roleService;
		this.storageService = storageService;
		this.storageService.retriveDb();
	}

	public static void main(String[] args) {
		SpringApplication.run(ArosajeApplication.class, args);
	}

	@PreDestroy
	public void onStop(){
		this.storageService.saveDb(new File("arosaje.db"));
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}
		};
	}


	@Override
	public void run(String... args) throws Exception {
		Role roleCustomer = new Role(1L, "Customer", null);
		Role roleKeeper = new Role(2L, "Keeper", null);
		Role roleBotanist = new Role(3L, "Botanist", null);
		this.roleService.saveRole(roleCustomer);
		this.roleService.saveRole(roleKeeper);
		this.roleService.saveRole(roleBotanist);
	}
}
