package hust.group20.se.config;

import hust.group20.se.Entity.User;
import hust.group20.se.Service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //权限暂时没写
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userNickName=(String)authenticationToken.getPrincipal();
        String userPassword = new String((char[]) authenticationToken.getCredentials());

        System.out.println("用户" + userNickName + "认证-----ShiroRealm.doGetAuthenticationInfo");

        // 通过用户名到数据库查询用户信息
        User user = userService.signInCheck(userNickName);

        if (user == null) {
            throw new UnknownAccountException("用户名不存在！");
        }
        if (!userPassword.equals(user.getUserPassword())) {
            throw new IncorrectCredentialsException("密码错误！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, userPassword, getName());
        return info;
    }
}
