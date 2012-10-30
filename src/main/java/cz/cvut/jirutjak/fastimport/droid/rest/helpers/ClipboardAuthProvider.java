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

package cz.cvut.jirutjak.fastimport.droid.rest.helpers;

import android.app.Activity;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.EBean;
import cz.cvut.jirutjak.fastimport.droid.AppConfig;
import cz.cvut.jirutjak.fastimport.droid.AppContext;
import cz.cvut.jirutjak.fastimport.droid.oauth2.*;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
@EBean
public class ClipboardAuthProvider implements AccessTokenProvider {
    
    private static final String TAG = ClipboardAuthProvider.class.getSimpleName();
    
    @App
    protected AppContext context;
    
    
    public void authorizeClient(Activity activityContext) {
        WebviewAuthorizationDialog dialog = new GoogleAuthorizationDialog(activityContext);
        
        dialog.setListener(new WebviewAuthorizationDialog.Listener() {
            
            public void onAuthorizationSuccess(String authorizationCode) {
                context.getOAuth2ClientContext().addAuthorizationCode(AppConfig.clipboard_auth, authorizationCode);
                Toast.makeText(context, "Authorization completed", Toast.LENGTH_LONG).show();
            }
            
            public void onAuthorizationFailed(String message) {
                Toast.makeText(context, "Authorization failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
        
        dialog.showAuthorizationDialog(AppConfig.clipboard_auth.buildAuthorizationRedirectUrl());
    }
    

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ResourceDetails resource) throws AuthorizationRequiredException {
        AccessTokenProvider tokenProvider = context.getAccessTokenProvider();
        
        try {
            return tokenProvider.obtainAccessToken(resource);
            
        } catch (AuthorizationRequiredException ex) {
            Toast.makeText(context, "Authorization required", Toast.LENGTH_LONG).show();
            throw ex;
        }
    }
}
