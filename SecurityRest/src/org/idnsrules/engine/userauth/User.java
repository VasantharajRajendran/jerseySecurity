/**
 * Created by philip a senger on October 22, 2015
 */
package org.idnsrules.engine.userauth;

public class User {

    public String username;
    public String roles;
    public String hashedPassword;

    public User(String username, String roles, String hashedPassword) {
        this.username = username;
        this.roles = roles;
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
