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

import android.app.Application;
import cz.cvut.jirutjak.fastimport.droid.daos.FileStorageGenericDAO;
import cz.cvut.jirutjak.fastimport.droid.daos.GenericDAO;
import cz.cvut.jirutjak.fastimport.droid.oauth2.AccessTokenProvider;
import cz.cvut.jirutjak.fastimport.droid.oauth2.DefaultAccessTokenProvider;
import cz.cvut.jirutjak.fastimport.droid.oauth2.OAuth2ClientContext;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class AppContext extends Application {
    
    private OAuth2ClientContext oauth2ClientContext;
    private AccessTokenProvider accessTokenProvider;
    private GenericDAO genericDAO;

    
    public OAuth2ClientContext getOAuth2ClientContext() {
        if (oauth2ClientContext == null) {
            oauth2ClientContext = new OAuth2ClientContext(this);
        }
        return oauth2ClientContext;
    }
    
    public AccessTokenProvider getAccessTokenProvider() {
        if (accessTokenProvider == null) {
            accessTokenProvider = new DefaultAccessTokenProvider(getOAuth2ClientContext());
        }
        return accessTokenProvider;
    }

    public GenericDAO getDAO() {
        if (genericDAO == null) {
            genericDAO = new FileStorageGenericDAO(this);
        }
        return genericDAO;
    }
    
}
