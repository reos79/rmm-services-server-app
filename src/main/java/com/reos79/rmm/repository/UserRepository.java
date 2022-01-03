package com.reos79.rmm.repository;

import org.springframework.data.repository.CrudRepository;

import com.reos79.rmm.model.User;

/**
 * The user data access.
 * @author reos79
 *
 */
public interface UserRepository extends CrudRepository<User, String>{

}
