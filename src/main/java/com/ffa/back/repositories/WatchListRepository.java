package com.ffa.back.repositories;


import com.ffa.back.models.WatchList;
import com.ffa.back.models.WatchListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, WatchListId> {
}