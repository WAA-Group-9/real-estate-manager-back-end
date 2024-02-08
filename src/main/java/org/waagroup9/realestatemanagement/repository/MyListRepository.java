package org.waagroup9.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waagroup9.realestatemanagement.model.entity.MyList;
import org.waagroup9.realestatemanagement.model.entity.User;

import java.util.List;

public interface MyListRepository extends JpaRepository<MyList, Long> {

    MyList findMyListById(User user);
}