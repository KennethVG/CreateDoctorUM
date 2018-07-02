package be.createdoctor.mapper;

import be.createdoctor.entities.UserEntity;
import be.createdoctor.model.User;

public class UserMapper {

    public static User fromUserEntity(UserEntity userEntity){
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmailAddress());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setSsin(userEntity.getSsin());
        user.setPhone(userEntity.getPhone());
        return user;
    }
}