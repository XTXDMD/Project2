package com.groupten.project2.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupten.project2.bean.PermissionDetail;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(PermissionDetail[].class)//查询结果对应的类型
    @MappedJdbcTypes(JdbcType.VARCHAR) //在数据库中对应的类型
//public class UserDetailTypehandler implements TypeHandler{
    public class PermissionDetailTypehandler extends BaseTypeHandler<PermissionDetail[]> {

        ObjectMapper objectMapper = new ObjectMapper();
        //输入映射的过程 UserDetail → String

        private PermissionDetail[] transfer(String value){
            if (value != null && !"".equals(value)){
                PermissionDetail[] permissionDetails = new PermissionDetail[1024];
                try {
                    permissionDetails = objectMapper.readValue(value, PermissionDetail[].class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return permissionDetails;
            }
            return null;
        }

        @SneakyThrows
        @Override
        public void setNonNullParameter(PreparedStatement preparedStatement, int i, PermissionDetail[] permissionDetails, JdbcType jdbcType) throws SQLException {
            String value = objectMapper.writeValueAsString(permissionDetails);
            preparedStatement.setString(i,value);
        }

        @Override
        public PermissionDetail[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
            String value = resultSet.getString(s);
            return transfer(value);
        }

        @Override
        public PermissionDetail[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
            String value = resultSet.getString(i);
            return transfer(value);
        }

        @Override
        public PermissionDetail[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
            String value = callableStatement.getString(i);
            return transfer(value);
        }


    /*@SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ArrayList<PermissionDetail> permissionDetails, JdbcType jdbcType) throws SQLException {
        String value = objectMapper.writeValueAsString(permissionDetails);
        preparedStatement.setString(i,value);
    }

    @Override
    public ArrayList<PermissionDetail> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return transfer(value);
    }

    @Override
    public ArrayList<PermissionDetail> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return transfer(value);
    }

    @Override
    public ArrayList<PermissionDetail> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return transfer(value);
    }*/
}

