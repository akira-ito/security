package ito.akira.edson.security.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class ControllerMain {

	@RequestMapping()
	public ResponseEntity<List<String>> as() {
		return ResponseEntity.ok(Arrays.asList("kkkk", "sfd"));
	}

//	@Secured()
//	@PreAuthorize()
	@RequestMapping("/private")
	public ResponseEntity<List<String>> privated(@AuthenticationPrincipal final UserDetails user) {
		return ResponseEntity
				.ok(Arrays.asList(user.getAuthorities().toString(), user.getUsername(), user.getPassword()));
	}

	@RequestMapping("/test")
	public ResponseEntity<List<String>> test(@AuthenticationPrincipal final UserDetails user, HttpSession session) {
		System.out.println(session);
		return ResponseEntity
				.ok(Arrays.asList(user.getAuthorities().toString(), user.getUsername(), user.getPassword()));
	}

	@RequestMapping(value = "/logmeout", method = RequestMethod.POST)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return ResponseEntity.ok("ok");
	}

}
