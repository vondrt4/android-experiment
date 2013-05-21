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

import android.app.Activity;
import android.util.Log;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class GoogleAuthorizationDialog extends WebviewAuthorizationDialog {
    
    private static final String TAG = GoogleAuthorizationDialog.class.getSimpleName();
    private static final String FINAL_PAGE_URL = "https://accounts.google.com/o/oauth2/approval";
    private static final String SUCCESS_TITLE = "code=";
    private static final String ERROR_TITLE = "error=";

    
    
    public GoogleAuthorizationDialog(Activity context) {
        super(context);
    }


    @Override
    public void handleReceivedPageTitle(String pageTitle, String url) {        
        if (url.startsWith(FINAL_PAGE_URL)) {
            
            if (pageTitle.contains(SUCCESS_TITLE)) {
                String authCode = pageTitle.split("=", 2)[1];
                
                Log.d(TAG, "Authorization success: " + authCode);
                listener.onAuthorizationSuccess(authCode);

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
