package ito.akira.edson.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Akdkfd extends GlobalAuthenticationConfigurerAdapter{
	
	@Autowired
	private TestUserDetailsService a;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(a).passwordEncoder(new BCryptPasswordEncoder());
	}
	
}
