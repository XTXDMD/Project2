package com.groupten.project2.realm;

import com.groupten.project2.bean.AdminExample;
import com.groupten.project2.bean.User;
import com.groupten.project2.bean.vo.AdminInfoVo;
import com.groupten.project2.bean.vo.PermissionVo;
import com.groupten.project2.mapper.AdminMapper;
import com.groupten.project2.mapper.PermissionMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       /* //通过token获得用户名信息（token中的信息就是你认证的时候传入的信息）
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        //通过name信息获得存在于系统或数据库的真实密码（凭证）信息

        String passwordFormDb = queryPasswordByUsername(username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordFormDb);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, passwordFormDb, this.getName());
        return authenticationInfo;*/
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        AdminInfoVo adminInfoVo = adminMapper.selectAdminByUsername(username);
        String credentail = adminInfoVo == null ? null:adminInfoVo.getPassword();

        /*List<String> passwords = adminMapper.selectPasswordByUsername(username);
        String credentail =  passwords.size() >= 1?passwords.get(0):null;*/

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(adminInfoVo,credentail,this.getName());
        return authenticationInfo;

    }

   /* private String queryPasswordByUsername(String username) {
        if("songge".equals(username)){
            return "123456";
        }else {
            return "654321";
        }
    }*/

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //simpleAuthenticationInfo的第一个参数
        AdminInfoVo primaryPrincipal = (AdminInfoVo) principalCollection.getPrimaryPrincipal();

        //通过用户名查询用户权限
        AdminInfoVo adminRole = adminMapper.selectRolesByUsername(primaryPrincipal.getUsername());
        Integer[] roles = adminRole.getRoleIds();
        List<String> permissions = new ArrayList<>();
        for (Integer role : roles) {
            PermissionVo permissionVo = permissionMapper.selectPermissionByRoleId(role);
            for (String permission : permissionVo.getPermissions()) {
                permissions.add(permission);
            }
        }
        //List<String> permission = queryPermissionByUsername(primaryPrincipal.getUsername());

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;

    }

    private List<String> queryPermissionByUsername(String username) {
        ArrayList<String> perms = new ArrayList<>();
        perms.add("user:insert");
        perms.add("user:delete");
        return perms;
    }
}
