package com.rodtan.interview.service;

import com.rodtan.interview.model.UserNotFoundException;
import com.rodtan.interview.model.User;
import com.rodtan.interview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("invalid id"));
    }

    @Override
    public User addUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User currentUser = getUser(user.getId());
        if (currentUser != null) {
            // found user, merge the 2 records
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                user.setEmail(currentUser.getEmail());
            }
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(currentUser.getName());
            }
        } else {
            throw new UserNotFoundException("user not found");
        }
        addUser(user);

        return user;
    }

    @Override
    public void deleteUser(int id) {
        User user = getUser(id);
        if (user != null) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("user not found");
        }

    }
}
