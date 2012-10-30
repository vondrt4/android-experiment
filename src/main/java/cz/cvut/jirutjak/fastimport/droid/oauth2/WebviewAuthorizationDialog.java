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
import cz.cvut.jirutjak.fastimport.droid.oauth2.helpers.HookedWebView;
import cz.cvut.jirutjak.fastimport.droid.oauth2.helpers.WebViewDialog;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public abstract class WebviewAuthorizationDialog {
        
    protected final WebViewDialog dialog;
    protected final HookedWebView browser;
    
    protected Listener listener;
    
    
    protected abstract void handleUserscriptFinished(String returnValue, String url);

    protected abstract void handleReceivedPageTitle(String pageTitle, String url);
    
    

    public WebviewAuthorizationDialog(Activity context) {
        browser = new HookedWebView(context);
        dialog = new WebViewDialog(context, browser);
        
        browser.setOnProgressChangedListener(new HookedWebView.OnProgressChangedListener() {
            public void onProgressChanged(int progress, String url) {
                dialog.setProgress(progress);
                dialog.setAddress(url);
            }
        });
        browser.setOnReceivedPageTitleListener(new HookedWebView.OnReceivedPageTitleListener() {
            public void onReceivedPageTitle(String pageTitle, String url) {
                dialog.setTitle(pageTitle);
                handleReceivedPageTitle(pageTitle, url);
            }
        });
        browser.setOnUserscriptFinishedListener(new HookedWebView.OnUserscriptFinishedListener() {
            public void onUserscriptFinished(String returnValue, String url) {
                handleUserscriptFinished(returnValue, url);
            }
        });
    }
    

    
    public void showAuthorizationDialog(String authorizationUrl) {
        dialog.show();
        browser.loadUrl(authorizationUrl);
    }

    public void dismissAuthorizationDialog() {
        browser.stopLoading();
        dialog.dismiss();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    
    
    protected void setUserscript(String javascript) {
        browser.setUserscript(javascript);
    }
    
    
    //--------------- NESTED INTERFACES ---------------//
    
    public interface Listener {
        
        void onAuthorizationSuccess(String authorizationCode);
        
        void onAuthorizationFailed(String message);
    }
    
}
