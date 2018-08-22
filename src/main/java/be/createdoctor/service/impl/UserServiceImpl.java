package be.createdoctor.service.impl;

import be.createdoctor.entities.CredentialEntity;
import be.createdoctor.entities.EHealthKeystoreCredentialEntity;
import be.createdoctor.entities.UserEntity;
import be.createdoctor.jparepo.CredentialRepository;
import be.createdoctor.jparepo.EHealthKeystoreCredentialRepository;
import be.createdoctor.jparepo.UserRepository;
import be.createdoctor.mapper.UserMapper;
import be.createdoctor.model.User;
import be.createdoctor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EHealthKeystoreCredentialRepository eHealthKeystoreCredentialRepository;
    private final CredentialRepository credentialRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EHealthKeystoreCredentialRepository eHealthKeystoreCredentialRepository, CredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.eHealthKeystoreCredentialRepository = eHealthKeystoreCredentialRepository;
        this.credentialRepository = credentialRepository;
    }

    @Override
    public User findbySsin(String ssin) {
        UserEntity userEntity = userRepository.findBySsin(ssin);
        return userEntity != null ? UserMapper.fromUserEntity(userEntity) : null;
    }

    @Override
    @Transactional
    public boolean deleteUser(String ssin) {
        UserEntity userEntity = userRepository.findBySsin(ssin);
        if (userEntity != null) {
            CredentialEntity credentialEntity = credentialRepository.findByUserEntity(userEntity);
            if (credentialEntity != null) {
                EHealthKeystoreCredentialEntity eHealthKeystoreCredentialEntity = eHealthKeystoreCredentialRepository.findOne(credentialEntity.getId());
                if (eHealthKeystoreCredentialEntity != null) {
                    eHealthKeystoreCredentialRepository.delete(eHealthKeystoreCredentialEntity);
                    credentialRepository.delete(credentialEntity);
                    userRepository.delete(userEntity.getId());
                    return true;
                } else {
                    credentialRepository.delete(credentialEntity);
                    userRepository.delete(userEntity.getId());
                    return true;
                }
            } else {
                userRepository.delete(userEntity.getId());
                return true;
            }
        }
        return false;
    }
}
