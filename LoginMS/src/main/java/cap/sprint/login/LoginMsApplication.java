package cap.sprint.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class LoginMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginMsApplication.class, args);
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public Docket swaggerConfiguration()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				//.paths(PathSelectors.ant("/*"))
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("cap.sprint.login"))
				.build()
				.apiInfo(new ApiInfo("Login Service", "", "1.0", "", "Aaryan","",""));
	}
}

//Login Module. Author - Aaryan.