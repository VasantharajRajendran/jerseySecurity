/**
 * Created by philip a senger on October 22, 2015
 */
package org.idnsrules.engine.userauth;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.idnsrules.jdbc.util.JDBCConnectionUtil;

public class UserDao {

    private Map<String, User> users;

    public UserDao() throws ClassNotFoundException, SQLException, IOException {
        users = new HashMap<String, User>();
        String getUsers="SELECT" 
		+" sut.email,"
		+"sut.password,"
		+"surt.role_name"
		+" FROM sys_user_t sut"
		+" INNER JOIN sys_user_roles_t surt"
		+" ON sut.role_id = surt.role_id";
        PreparedStatement prepareStatement=JDBCConnectionUtil.getConnection().prepareStatement(getUsers);
        ResultSet resultSet =prepareStatement.executeQuery();
        while(resultSet.next()){
        	User user=new User(resultSet.getString("email"),resultSet.getString("role_name"),resultSet.getString("password"));
        	users.put(user.getUsername(), user);
        }
      }

    public Collection<User> getUsers() {
        return (users.values());
    }

    public User getUser(String username) {
        return (users.get(username));
    }
}
