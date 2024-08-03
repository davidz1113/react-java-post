package dev.project.backendcursojava;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.project.backendcursojava.models.responses.UserDetailResponseModel;
import dev.project.backendcursojava.security.Properties;
import dev.project.backendcursojava.shared.dto.UserDto;

@SpringBootApplication
@EnableJpaAuditing
public class BackendcursojavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendcursojavaApplication.class, args);
		System.out.println("Funcionando!");
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public Properties getProperties() {
		return new Properties();
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(UserDto.class, UserDetailResponseModel.class)
				.addMappings(m -> m.skip(UserDetailResponseModel::setPosts));

		return modelMapper;
	}

}
