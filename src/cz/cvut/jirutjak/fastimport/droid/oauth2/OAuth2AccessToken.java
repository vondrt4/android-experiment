/*
 * Copyright (c) 2012 Jakub Jirutka <jakub@jirutka.cz>
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package cz.cvut.jirutjak.fastimport.droid.oauth2;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>, Tomas Vondra <vondrto6@fel.cvut.cz>
 */
public class OAuth2AccessToken implements Serializable {
    
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String tokenType;
    
    private final Date created;

    
    public OAuth2AccessToken() {
        created = new Date();
    }
    
    public OAuth2AccessToken(String accessToken, Integer expiresIn, String tokenType) {
        created = new Date();
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
    }
    
    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }
    
    public boolean isExpired() {        
        Date now = new Date();
        long passed = now.getTime() - created.getTime();
        
        return expiresIn == null ? true : passed > expiresIn * 100;
    }

    
    @Override
    public String toString() {
        return "Token{" + "accessToken=" + accessToken + ", expiresIn=" + expiresIn 
                + ", refreshToken=" + refreshToken + ", tokenType=" + tokenType + '}';
    }
    
}
