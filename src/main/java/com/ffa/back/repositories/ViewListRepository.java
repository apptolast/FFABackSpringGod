package com.ffa.back.repositories;


import com.ffa.back.models.ViewList;
import com.ffa.back.models.ViewListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewListRepository extends JpaRepository<ViewList, ViewListId> {
}