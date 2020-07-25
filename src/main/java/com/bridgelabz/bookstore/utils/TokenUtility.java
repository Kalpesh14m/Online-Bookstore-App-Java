package com.bridgelabz.bookstore.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.constants.Constant;
import com.bridgelabz.bookstore.exception.UserAuthorizationException;
import com.bridgelabz.bookstore.exception.UserNotFoundException;
import com.bridgelabz.bookstore.model.Role;
import com.bridgelabz.bookstore.model.User;
import com.bridgelabz.bookstore.repo.RoleRepository;
import com.bridgelabz.bookstore.repo.UserRepo;

@Component
public class TokenUtility {
	
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static String verifyResponse(Long userId, Long roleId) {
		return Constant.VERIFY_ADDRESS + JwtValidate.createJWT(userId, roleId, Constant.REGISTER_EXP);
	}

	public static String resetPassword(Long userId, Long roleId) {
		return Constant.RESET_PASSWORD + JwtValidate.createJWT(userId, roleId, Constant.REGISTER_EXP);
	}

	/**
	 * Method to authenticate User
	 * 
	 * @param token
	 * @return User
	 * @throws - UserAuthorizationException => if User is not a Seller -
	 *           UserNotFoundException => if User is not registered and tries to
	 *           login as seller
	 */
	public User authentication(String token, String roleName) {

		Long userId = Long.valueOf((Integer) JwtValidate.decodeJWT(token).get("userId"));
		Integer roleId = (Integer) JwtValidate.decodeJWT(token).get("roleId");
		Role validRole = validatingRole(roleId, roleName);
		return Optional.ofNullable(userRepository.findByUserIdAndRoleId(userId, validRole.getRoleId()))
				.orElseThrow(() -> new UserNotFoundException(Constant.USER_NOT_FOUND_EXCEPTION_MESSAGE));

	}

	private Role validatingRole(Integer roleId, String roleName) {
		return Optional.ofNullable(roleRepository.getRoleById(roleId))
				.filter(role -> role.getRole().equalsIgnoreCase(roleName))
				.orElseThrow(() -> new UserAuthorizationException(Constant.UNAUTHORIZED_EXCEPTION_MESSAGE));
	}
}
