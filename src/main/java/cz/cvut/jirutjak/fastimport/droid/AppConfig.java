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

package cz.cvut.jirutjak.fastimport.droid;

import cz.cvut.jirutjak.fastimport.droid.oauth2.OAuth2ResourceDetails;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class AppConfig {
    
    public static final String clipboard_base_uri = "https://tomcat1.gr1d.net/fastimport";
    
    public static final OAuth2ResourceDetails clipboard_auth = new OAuth2ResourceDetails("google")
            .setAccessTokenEndpoint("https://accounts.google.com/o/oauth2/token")
            .setAuthorizationEndpoint("https://accounts.google.com/o/oauth2/auth")
            .setClientId("872227663132-6j3lptlarftri6q0gbfe7rl0p3rb6qj2.apps.googleusercontent.com")
            .setClientSecret("ddNv64XOYGBl22RU2rKpbJYf")
            .addScope("https://www.googleapis.com/auth/userinfo.profile")
            .addScope("https://www.googleapis.com/auth/userinfo.email");
    
}
