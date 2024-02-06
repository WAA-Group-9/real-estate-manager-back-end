package org.waagroup9.realestatemanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.waagroup9.realestatemanagement.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
}
