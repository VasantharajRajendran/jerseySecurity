/**
 * Created by philip a senger on October 22, 2015
 */
package org.idnsrules.engine.userauth;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message, String realm) {
        super(message);
        this.realm = realm;
    }

    private String realm = null;

    public String getRealm() {
        return this.realm;
    }

}
