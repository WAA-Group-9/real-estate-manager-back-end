package org.waagroup9.realestatemanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.waagroup9.realestatemanagement.model.entity.MyList;
import org.waagroup9.realestatemanagement.repository.MyListRepository;
import org.waagroup9.realestatemanagement.service.MyListService;

import java.util.List;

@Service
public class MyListServiceImpl implements MyListService {

    private final MyListRepository myListRepository;

    @Autowired
    public MyListServiceImpl(MyListRepository myListRepository) {
        this.myListRepository = myListRepository;
    }

    @Override
    public MyList saveMyList(MyList myList) {
        return myListRepository.save(myList);
    }

    @Override
    public MyList getMyListById(Long id) {
        return myListRepository.findById(id).orElse(null);
    }

    @Override
    public List<MyList> getAllMyLists() {
        return myListRepository.findAll();
    }

    @Override
    public void deleteMyList(Long id) {
        myListRepository.deleteById(id);
    }
}