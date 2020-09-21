package hust.group20.se.ServiceImpl;

import hust.group20.se.Entity.User;
import hust.group20.se.Mapper.UserMapper;
import hust.group20.se.Service.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User signInCheck(String userNickName) {
        return userMapper.signInCheck(userNickName);
    }

    @Override
    public Integer addUser(User user) {

        return userMapper.addUser(user);
    }

    @Override
    public User signUpCheck(String userNickName) {
        return userMapper.signUpCheck(userNickName);
    }

    @Override
    public Integer updateUserInfo(String userNickName,Integer userSex,String userEmail){
        return userMapper.updateUserInfo(userNickName,userSex,userEmail);
    }

    @Override
    public Integer updateUserPassword(String userNickName, String userPassword) {
        return userMapper.updateUserPassword(userNickName,userPassword);
    }
}
