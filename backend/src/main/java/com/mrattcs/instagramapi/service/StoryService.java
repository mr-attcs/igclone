package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.exceptions.StoryException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Story;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoryService {

    Story createStory(Story story, Integer userId) throws UserException;

    List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;

    String deleteStory(Integer storyId, Integer userId) throws StoryException, UserException;

    Story findStoryById(Integer storyId) throws StoryException;

}
