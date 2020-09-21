package hust.group20.se.Mapper;

import hust.group20.se.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE userNickName=#{userNickName}")
    User signInCheck(String userNickName);

    @Insert("INSERT INTO user (userNickName,userEmail,userSex,userPassword) VALUES (#{userNickName},#{userEmail},2,#{userPassword})")
    Integer addUser(User user);

    @Select("SELECT * FROM user WHERE userNickName=#{userNickName}")
    User signUpCheck(String userNickName);

    @Update("UPDATE user SET user.userSex=#{userSex},user.UserEmail=#{userEmail} WHERE user.userNickName=#{userNickName}")
    Integer updateUserInfo(String userNickName,Integer userSex,String userEmail);

    @Update("UPDATE user SET user.userPassword=#{userPassword} WHERE user.userNickName=#{userNickName}")
    Integer updateUserPassword(String userNickName,String userPassword);

}
