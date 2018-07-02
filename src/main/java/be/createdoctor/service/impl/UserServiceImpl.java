package be.createdoctor.service.impl;

import be.createdoctor.jparepo.UserRepository;
import be.createdoctor.mapper.UserMapper;
import be.createdoctor.model.User;
import be.createdoctor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findbySsin(String ssin) {
        return UserMapper.fromUserEntity(userRepository.findBySsin(ssin));
    }
}
