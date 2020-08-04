package com.groupten.project2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.groupten.project2.bean.PermissionDetail;
import com.groupten.project2.bean.SystemPermission;
import com.groupten.project2.bean.vo.PermissionVo;
import com.groupten.project2.mapper.PermissionMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
class Project2ApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
        /*String roles = "[1,2,3]";
        Gson gson = new Gson();
        ArrayList list1 = gson.fromJson(roles,ArrayList.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList list = objectMapper.readValue(roles,ArrayList.class);
        ArrayList RoleList = new ArrayList(Arrays.asList(roles.split(",")));*/
        Resource resource = new ClassPathResource("");
        try {
            System.out.println(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shiroTest(){
        //得到SecurityManger
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:first.ini");
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //获得subject
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        //subject执行认证（login）
        //提供执行认证的用户名密码信息，放入token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("songge", "zhenshuai");
        subject.login(usernamePasswordToken);

        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);
        //通过认证器认证


        //如果认证不通过，则无法授权
        //基于角色授权
        //authorBaseRole(subject);

        //基于权限
        authorBasePermmison(subject);

    }

    private void authorBasePermmison(Subject subject) {
        String insertPermission = "user:insert";
        String deletePermission = "user:delete";
        String updatePermission = "user:update";
        String queryPermission = "user:query";
        boolean[] permitted = subject.isPermitted(insertPermission, deletePermission, updatePermission, queryPermission);
        System.out.println("是否用于增删改查的权限" + Arrays.toString(permitted));

        boolean permitted1 = subject.isPermitted(queryPermission);
        System.out.println("是否用有单个权限" + permitted1);

        boolean permittedAll = subject.isPermittedAll(insertPermission,deletePermission,queryPermission,updatePermission);
        System.out.println("是否拥有全部权限" + permittedAll);
    }

    private void authorBaseRole(Subject subject) {
        boolean role1 = subject.hasRole("role1");
        System.out.println(role1);

        ArrayList<String> roleList = new ArrayList<>();
        roleList.add("role1");
        roleList.add("role2");
        roleList.add("role3");
        boolean[] hasRoles = subject.hasRoles(roleList);
        System.out.println(" " + Arrays.toString(hasRoles));

        boolean b = subject.hasAllRoles(roleList);
        System.out.println(b);
    }

    @Test
    public void customRealmTest(){
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:custom.ini");
        SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
        //获得subject
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("songge","123456"));

        boolean authenticated = subject.isAuthenticated();
        System.out.println(authenticated);

        boolean[] permitted = subject.isPermitted("user:insert", "user:delete");
        System.out.println(Arrays.toString(permitted));

    }

    @Autowired
    PermissionMapper permissionMapper;
    @Test
    public void typehandlerTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PermissionVo permissions = permissionMapper.selectPermissionByRoleId(1);
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setId(1);
        permissionVo.setPermissions(new String[]{
                "admin:storage:list",
                "admin:admin:update",
                "admin:address:list",
                "admin:admin:delete",
                "admin:user:list",
                "admin:storage:create",
                "admin:role:permission:get",
                "admin:role:create",
                "admin:admin:read",
                "admin:storage:read",
                "admin:log:list",
                "admin:stat:order",
                "admin:stat:goods",
                "admin:role:permission:update",
                "admin:role:update",
                "admin:stat:user",
                "admin:goods:list",
                "admin:admin:create",
                "admin:admin:list",
                "admin:role:list",
                "admin:role:delete",
                "admin:storage:delete",
                "admin:storage:update",
                "admin:role:read"
        });

        permissionMapper.updatePermission(permissionVo);

        System.out.println(permissions.toString());
    }

    @Test
    void systemPermissionTest() throws JsonProcessingException {
        List<SystemPermission> systemPermission = permissionMapper.selectSystemPermission();
        for (SystemPermission permission : systemPermission) {
            PermissionDetail[] permissionDetails = new PermissionDetail[1024];
            for (int i = 0; i < permission.getChildren().length; i++) {
                String id = permission.getChildren()[i].getId();
                permissionDetails[i] = permissionMapper.selectOperationsByPId(id);
            }
            permission.setChildren(permissionDetails);
        }
        /*for (int i = 0; i < systemPermission.getChildren().length; i++) {
            String id = systemPermission.getChildren()[i].getId();
            permissionDetails[i] = permissionMapper.selectOperationsByPId(id);
        }
        systemPermission.setChildren(permissionDetails);*/
        System.out.println(systemPermission);
    }
}
