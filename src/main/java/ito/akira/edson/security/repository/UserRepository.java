package ito.akira.edson.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ito.akira.edson.security.mode.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByEmail(String email);

}
