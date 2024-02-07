package org.waagroup9.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.waagroup9.realestatemanagement.model.entity.MyList;

public interface MyListRepository extends JpaRepository<MyList, Long> {
}