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

import com.googlecode.androidannotations.annotations.App;
import com.googlecode.androidannotations.annotations.EBean;

import cz.cvut.jirutjak.fastimport.droid.AppConfig;
import cz.cvut.jirutjak.fastimport.droid.AppContext;
import android.app.Activity;
import android.util.Log;

/**
 *
 * @author Tomas Vondra <vondrto6@fel.cvut.cz>
 */
public class CVUTAuthorizationDialog extends WebviewAuthorizationDialog {
    
    private static final String TAG = CVUTAuthorizationDialog.class.getSimpleName();
    private static final String FINAL_PAGE_URL = "http://www.example.cz/";
    private static final String SUCCESS_URL = "access_token=";
    private static final String ERROR_TITLE = "error=";  

    
    protected AppContext context;
    private OAuth2ClientContext oauthContext;

    public CVUTAuthorizationDialog(AppContext context, Activity activityContext) {
    	super(activityContext);
    	this.context = context;
    	oauthContext = context.getOAuth2ClientContext();
    }
    
    
    private OAuth2AccessToken newToken;
    
    @Override
    public void handleReceivedPageTitle(String pageTitle, String url) {        
        if (url.startsWith(FINAL_PAGE_URL)) {
            
            if (url.contains(SUCCESS_URL)) {
                String accessToken = url.split("=", 4)[1].split("&",2)[0];
                String tokenType = url.split("=", 4)[2].split("&",2)[0];
                Integer expiresIn = Integer.parseInt(url.split("=", 4)[3].split("&",2)[0]);
                
                Log.d(TAG, "Authorization success. accessToken: " + accessToken + " tokenType: " + tokenType + " expiresIn: " + expiresIn);
                newToken = new OAuth2AccessToken(accessToken, expiresIn, tokenType);
                oauthContext.addAccessToken(AppConfig.clipboard_auth, newToken);
                
                listener.onAuthorizationSuccess(null);
//error has different URL, therefore this never happens and user remains in browser.
            } else if (pageTitle.contains(ERROR_TITLE)) {
                String error = pageTitle.split("=", 2)[1];
                
                Log.w(TAG, "Authorization failed: " + error);
                listener.onAuthorizationFailed(error);
                
            } else {
                Log.w(TAG, "Unknown error");
                listener.onAuthorizationFailed("unknown error");
            }
            dismissAuthorizationDialog();
        }
    }

    @Override
    protected void handleUserscriptFinished(String returnValue, String url) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
}
