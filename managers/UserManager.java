package com.semantics.thrillio.managers;

import com.semantics.thrillio.dao.UserDao;
import com.semantics.thrillio.entities.User;

public class UserManager {

	// To prevent instantiating by clients we are using a
	// private constructor

	private static UserManager instance = new UserManager();
	private static UserDao dao = new UserDao();

	private UserManager() {
	}

	public static UserManager getInstance() {
		return instance;
	}
	
	public User[] getUsers() {
		return dao.getUsers();
	}

	public User createUser(long id, String email, String password, String firstName, String lastName, int gender,
			String userType) {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setUserType(userType);

		return user;
	}
}
