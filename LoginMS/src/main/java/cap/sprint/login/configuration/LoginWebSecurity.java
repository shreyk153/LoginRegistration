package cap.sprint.login.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cap.sprint.login.services.LoginServiceImpl;

@Configuration
@EnableWebSecurity
public class LoginWebSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	LoginServiceImpl loginService;			//Login Service object created.


	@Autowired
	public void configureDBUsers(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(loginService);		//Login Authentication data source configuration.
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();			//Encoding Strategy.
	}
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/admin/*","/create/*").hasRole("ADMIN")
		.antMatchers("/user/*").hasAnyRole("ADMIN", "USER")
		.antMatchers("/").permitAll().and().formLogin().and().httpBasic();			//Authorization strategy.
	}
}
