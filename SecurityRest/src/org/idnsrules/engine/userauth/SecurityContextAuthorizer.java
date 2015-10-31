/**
 * Created by philip a senger on October 22, 2015
 */

package org.idnsrules.engine.userauth;

import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.idnsrules.jdbc.util.JDBCConnectionUtil;

import java.io.IOException;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecurityContextAuthorizer implements SecurityContext {

	private static Logger logger=Logger.getLogger(SecurityContextAuthorizer.class);
    private User user;
    private Principal principal;
    private List<String > roleFutures;
    private javax.inject.Provider<UriInfo> uriInfo;

    public SecurityContextAuthorizer(final javax.inject.Provider<UriInfo> uriInfo, final User user) {
        this.user = user;
        this.principal = new Principal() {
            public String getName() {
                return user.username;
            }
        };
        if(isUserInRole(user.getRoles())){
        logger.info("UriInfo is:"+uriInfo);
        this.uriInfo = uriInfo;
        }else{
        	logger.info("User doesnot contain roles.");
        }
    }

    public Principal getUserPrincipal() {
        return this.principal;
    }

    public boolean isUserInRole(String role) {
    	String getRollFutures="SELECT"
		 +" sft.future_name"
		 +" FROM sys_futures_t sft"
		+" INNER JOIN sys_role_future_association_t srfat"
		+" ON sft.future_id = srfat.future_id"
		+" INNER JOIN sys_user_roles_t surt"
		+" ON surt.role_id = srfat.role_id"
		+" INNER JOIN sys_user_t sut"
		+" ON sut.role_id =surt.role_id"
		+" WHERE sut.email='"+user.getUsername()+"'";
    	try {
			PreparedStatement prepareStatement =JDBCConnectionUtil.getConnection().prepareStatement(getRollFutures);
			logger.info("Prepare statement is:"+prepareStatement);
			ResultSet resultSet=prepareStatement.executeQuery();
			roleFutures=new ArrayList<String>();
			while(resultSet.next()){
				roleFutures.add(resultSet.getString("future_name"));
			}
		} catch (Exception e) {
			logger.error("Error occurs while trying to get Role's features.",e);
		}
    	Set roles = new HashSet<String>(roleFutures);
    	logger.info("User role' futures are:"+roles);
    	if (user.getRoles() != null) {
                    Collection<String> strings = Arrays.asList(user.getRoles());
            roles.addAll(strings);
        }
        return roles.contains(((role == null) ? "" : role));
    }
    

    public boolean isSecure() {
        return "https".equals(uriInfo.get().getRequestUri().getScheme());
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
