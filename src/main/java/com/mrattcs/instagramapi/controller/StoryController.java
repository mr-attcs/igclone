package com.mrattcs.instagramapi.controller;

import com.mrattcs.instagramapi.exceptions.StoryException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Story;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.service.StoryService;
import com.mrattcs.instagramapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final UserService userService;
    private final StoryService storyService;

    public StoryController(UserService userService, StoryService storyService) {
        this.userService = userService;
        this.storyService = storyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Story> createStory(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        Story createdStory = storyService.createStory(story, user.getId());

        return new ResponseEntity<>(createdStory, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Story>> findALlStoriesByUserId(@PathVariable Integer userId) throws UserException, StoryException {
        return new ResponseEntity<>(storyService.findStoryByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{storyId}")
    public ResponseEntity<?> deleteStoryHandler(@PathVariable Integer storyId, @RequestHeader("Authorization") String token) throws UserException, StoryException {
        User user = userService.findUserProfile(token);

        String message = storyService.deleteStory(storyId, user.getId());

        return ResponseEntity.ok(message);
    }

}
