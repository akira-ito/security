package ito.akira.edson.security.config;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ito.akira.edson.security.mode.User;
import ito.akira.edson.security.repository.UserRepository;

//@Service
@Component
public class TestUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);

		if (user == null)
			throw new UsernameNotFoundException("kkkk");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(),
				getGrantedAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(User user) {
		Collection<? extends GrantedAuthority> authorities;
		if (user.getEmail().equals("John")) {
			authorities = asList(() -> "ROLE_ADMIN", () -> "ROLE_BASIC");
		} else {
			authorities = asList(() -> "ROLE_BASIC");
//			new SimpleGrantedAuthority("kkk");
		}
//		AuthorityUtils.createAuthorityList("User");
		return authorities;
	}

}
