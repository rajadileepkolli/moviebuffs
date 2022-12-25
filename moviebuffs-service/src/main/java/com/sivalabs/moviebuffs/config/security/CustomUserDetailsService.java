package com.sivalabs.moviebuffs.config.security;

import com.sivalabs.moviebuffs.core.entity.User;
import com.sivalabs.moviebuffs.core.model.SecurityUser;
import com.sivalabs.moviebuffs.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userService.getUserByEmail(username);
		if (user.isPresent()) {
			return new SecurityUser(user.get());
		}
		else {
			throw new UsernameNotFoundException("No user found with username " + username);
		}
	}

}
