package com.mrattcs.instagramapi.service;

import com.mrattcs.instagramapi.dto.UserDto;
import com.mrattcs.instagramapi.exceptions.UserException;
import com.mrattcs.instagramapi.modal.User;
import com.mrattcs.instagramapi.repo.UserRepository;
import com.mrattcs.instagramapi.security.JwtTokenClaims;
import com.mrattcs.instagramapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementer implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImplementer(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExisting = userRepository.findByEmail(user.getEmail());

        if (isEmailExisting.isPresent()) {
            throw new UserException("Email is already existing");
        }

        Optional<User> isUsernameExisting = userRepository.findByUsername(user.getUsername());

        if (isUsernameExisting.isPresent()) {
            throw new UserException("Username is already existing");
        }

        if (user.getEmail() == null || user.getPassword() == null || user.getUsername() == null || user.getName() == null) {
            throw new UserException("All fields are required");
        }

        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        newUser.setName(user.getName());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer userId) throws UserException {

        Optional<User> isIdExisting = userRepository.findById(userId);

        if (isIdExisting.isPresent()) {
            return isIdExisting.get();
        }
        throw new UserException("User with ID " + userId + " does not exist.");
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        token = token.substring(7);

        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);

        String email = jwtTokenClaims.getUsername();

        Optional<User> oUser = userRepository.findByEmail(email);

        if (oUser.isPresent()) {
            return oUser.get();
        }
        throw new UserException("invalid token...");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> oUser = userRepository.findByUsername(username);

        if (oUser.isPresent()) {
            return oUser.get();
        }
        throw new UserException("User with username :" + username + " does not exist");
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        try {
            User reqUser = findUserById(reqUserId);
            User followUser = findUserById(followUserId);

            UserDto follower = new UserDto();

            follower.setEmail(reqUser.getEmail());
            follower.setId(reqUser.getId());
            follower.setName(reqUser.getName());
            follower.setUserimage(reqUser.getImage());
            follower.setUsername(reqUser.getUsername());

            UserDto following = new UserDto();

            following.setEmail(followUser.getEmail());
            following.setId(followUser.getId());
            following.setUserimage(followUser.getImage());
            following.setName(followUser.getName());
            following.setUsername(followUser.getUsername());

            reqUser.getFollowing().add(following);
            followUser.getFollower().add(follower);

            userRepository.save(followUser);
            userRepository.save(reqUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException("Invalid Action");
        }
        return "You have successfully followed " + userRepository.findById(followUserId).get().getUsername();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followUserId) throws UserException {
            User reqUser = findUserById(reqUserId);
            User followUser = findUserById(followUserId);

            UserDto follower = new UserDto();

            follower.setEmail(reqUser.getEmail());
            follower.setId(reqUser.getId());
            follower.setName(reqUser.getName());
            follower.setUserimage(reqUser.getImage());
            follower.setUsername(reqUser.getUsername());

            UserDto following = new UserDto();

            following.setEmail(followUser.getEmail());
            following.setId(followUser.getId());
            following.setUserimage(followUser.getImage());
            following.setName(followUser.getName());
            following.setUsername(followUser.getUsername());

            reqUser.getFollowing().remove(following);
            followUser.getFollower().remove(follower);

            userRepository.save(followUser);
            userRepository.save(reqUser);
        return "You have successfully unfollowed " + followUser.getUsername();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        List<User> users = userRepository.findAllUsersByUserIds(userIds);

        return users;
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if (users.isEmpty()) {
            throw new UserException("User not found");
        }
        return users;
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getBio() != null) {
            existingUser.setBio(updatedUser.getBio());
        }
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getMobile() != null) {
            existingUser.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getGender() != null) {
            existingUser.setGender(updatedUser.getGender());
        }
        if (updatedUser.getWebsite() != null) {
            existingUser.setWebsite(updatedUser.getWebsite());
        }
        if (updatedUser.getImage() != null) {
            existingUser.setImage(updatedUser.getImage());
        }
        if (updatedUser.getId().equals(existingUser.getId())) {
            return userRepository.save(existingUser);
        }
        throw new UserException("You can't update this user");
    }
}
