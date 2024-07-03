package com.rodtan.interview.service;

import com.rodtan.interview.model.User;
import com.rodtan.interview.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private User createUser() {
        User user = new User(1, "Rod", "rodtest@example.com");
        return user;
    }

    private List<User> getAllTestUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        return users;
    }

    @Test
    public void canGetAllUsers() throws Exception {
        given(userRepository.findAll()).willReturn(getAllTestUsers());
        List<User> users = userService.getAllUsers();
        assert(users.size() == getAllTestUsers().size());
    }

    @Test
    public void canGetUserById() throws Exception {
        User inputUser = createUser();
        given(userRepository.findById(1)).willReturn(Optional.of(inputUser));
        User user = userService.getUser(1);
        assert(user == inputUser);
    }

    @Test
    public void canAddUser() throws Exception {
        User inputUser = createUser();
        given(userRepository.save(inputUser)).willReturn(inputUser);
        User user = userService.addUser(inputUser);
        assert(user == inputUser);
    }

    @Test
    public void canUpdateUser() throws Exception {
        User inputUser = createUser();
        given(userRepository.findById(1)).willReturn(Optional.of(inputUser));
        given(userRepository.save(inputUser)).willReturn(inputUser);
        User user = userService.updateUser(inputUser);
        assert(user == inputUser);
    }

    @Test
    public void canDeleteUser() throws Exception {
        User inputUser = createUser();
        given(userRepository.findById(1)).willReturn(Optional.of(inputUser));
        userService.deleteUser(inputUser.getId());
        verify(userRepository, times(1)).deleteById(1);
    }
}
