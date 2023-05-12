package com.stage.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="KiteFever API", version="1.0", description="The API to get the weather, post and read feedback and subscribe to emails", 
								contact = @Contact(name = "Joran Van Belle", email = "joran.vanbelle@live.be", url = "https://github.com/JoranVanBelle")), 
					servers = @Server(url = "http://localhost:5000", description = "Default Server URL for development"))
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
	}
}
