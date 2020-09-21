package hust.group20.se.Service;

import hust.group20.se.Entity.User;

public interface UserService {
    User signInCheck(String userNickName);
    Integer addUser(User user);
    User signUpCheck(String userNickName);

    Integer updateUserInfo(String userNickName,Integer userSex,String userEmail);

    Integer updateUserPassword(String userNickName,String userPassword);

}
