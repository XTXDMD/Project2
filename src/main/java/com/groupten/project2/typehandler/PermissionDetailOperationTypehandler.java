package com.groupten.project2.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupten.project2.bean.PermissionDetail;
import com.groupten.project2.bean.PermissionDetailOperation;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(PermissionDetailOperation[].class)//查询结果对应的类型
@MappedJdbcTypes(JdbcType.VARCHAR) //在数据库中对应的类型
//public class UserDetailTypehandler implements TypeHandler{
public class PermissionDetailOperationTypehandler extends BaseTypeHandler<PermissionDetailOperation[]> {

    ObjectMapper objectMapper = new ObjectMapper();
    //输入映射的过程 UserDetail → String

    private PermissionDetailOperation[] transfer(String value) {
        if (value != null && !"".equals(value)) {
            PermissionDetailOperation[] permissionDetailOperations = new PermissionDetailOperation[1024];
            try {
                permissionDetailOperations = objectMapper.readValue(value, PermissionDetailOperation[].class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return permissionDetailOperations;
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PermissionDetailOperation[] permissionDetailOperations, JdbcType jdbcType) throws SQLException {
        String value = objectMapper.writeValueAsString(permissionDetailOperations);
        preparedStatement.setString(i,value);
    }

    @Override
    public PermissionDetailOperation[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return transfer(value);
    }

    @Override
    public PermissionDetailOperation[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return transfer(value);
    }

    @Override
    public PermissionDetailOperation[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return transfer(value);
    }
}
