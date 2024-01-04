package com.mrattcs.instagramapi.repo;

import com.mrattcs.instagramapi.modal.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer> {

    @Query(value = "select s.* from stories s where s.user_id= :userId", nativeQuery = true)
    List<Story> findAllStoriesByUserId(Integer userId);

}
