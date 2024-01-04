package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService  {

    User registerUser(User user) throws UserException;
    User findUserById(Integer userId) throws UserException;
    User findUserProfile(String token) throws UserException;
    User findUserByUsername(String username) throws UserException;
    String followUser(Integer reqUserId, Integer followUserId) throws UserException;

    String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException;

    List<User> findUserByIds(List<Integer> userIds) throws UserException;

    List<User> searchUser(String query) throws UserException;

    User updateUserDetails(User updatedUser, User existingUser) throws UserException;


}
