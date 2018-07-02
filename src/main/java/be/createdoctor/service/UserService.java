package be.createdoctor.service;

import be.createdoctor.model.User;

public interface UserService {

    User findbySsin(String ssin);
}
