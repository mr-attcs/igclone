package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.dto.UserDto;
import com.mrattcs.instagramapi.exceptions.StoryException;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.Story;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StoryServiceImplementer implements StoryService {

    private final StoryRepository storyRepository;
    private final UserService userService;

    public StoryServiceImplementer(StoryRepository storyRepository, UserService userService) {
        this.storyRepository = storyRepository;
        this.userService = userService;
    }

    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user = userService.findUserById(userId);

        UserDto uDto = new UserDto();
        uDto.setEmail(user.getEmail());
        uDto.setId(user.getId());
        uDto.setName(user.getName());
        uDto.setUserimage(uDto.getUserimage());
        uDto.setUsername(user.getUsername());

        story.setUser(uDto);

        story.setTimestamp(LocalDateTime.now());

        user.getStories().add(story);

        return storyRepository.save(story);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user = userService.findUserById(userId);

        List<Story> stories = storyRepository.findAllStoriesByUserId(user.getId());
        if (stories.isEmpty()) {
            throw new StoryException("This user doesn't have any stories");
        }
        return stories;
    }

    @Override
    public String deleteStory(Integer storyId, Integer userId) throws StoryException, UserException {
        Story story = findStoryById(storyId);

        User existingUser = userService.findUserById(userId);
        User updatedUser = existingUser;

        if (story.getUser().getId().equals(existingUser.getId())) {
            updatedUser.getStories().remove(story);
            userService.updateUserDetails(updatedUser, existingUser);
            storyRepository.deleteById(storyId);
            return "Story deleted successfully";

        }
        throw new StoryException("You can't delete other user's stories.");
    }

    @Override
    public Story findStoryById(Integer storyId) throws StoryException {
        Optional<Story> oStory = storyRepository.findById(storyId);

        if (oStory.isPresent()) {
            return oStory.get();
        }

        throw new StoryException("Story with ID " + storyId + " does not exist");
    }
}


