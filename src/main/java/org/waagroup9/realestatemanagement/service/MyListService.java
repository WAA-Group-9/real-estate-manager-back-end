package org.waagroup9.realestatemanagement.service;

import org.waagroup9.realestatemanagement.model.entity.MyList;

import java.util.List;

public interface MyListService {
    MyList saveMyList(MyList myList);
    MyList getMyListById(Long id);
    List<MyList> getAllMyLists();
    void deleteMyList(Long id);
}