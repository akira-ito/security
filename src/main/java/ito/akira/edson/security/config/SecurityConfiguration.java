package ito.akira.edson.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static String REALM = "MY_TEST_REALM";
	
	@Autowired
	private TestUserDetailsService testUserDetailsService;
	
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1").password("secret1").roles("USER")
//                .and()
//                .withUser("user2").password("secret2").roles("USER");
//    }
	
	@EnableSpringHttpSession
	class HttpSessionConfig {

		@Bean
		SessionRepository<ExpiringSession> inmemorySessionRepository() {
			return new MapSessionRepository();
		}

		@Bean
		HttpSessionStrategy httpSessionStrategy() {
			return new HeaderHttpSessionStrategy();
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/users").permitAll()
//			.antMatchers("/users").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and().httpBasic()
			.and().requestCache().requestCache(new NullRequestCache())
//			.and().httpBasic()
//				.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
			.and().sessionManagement().maximumSessions(1);//.and().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// We don't need sessions to be created.
//				.and().formLogin().disable();
	}
	
	@Override
	  public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(testUserDetailsService)
		.passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
//	    builder
//	        .inMemoryAuthentication()
//	        .withUser("garrincha").password("123")
//	            .roles("USER")
//	        .and()
//	        .withUser("zico").password("123")
//	            .roles("USER");
	  }

}
