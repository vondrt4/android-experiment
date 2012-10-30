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

package cz.cvut.jirutjak.fastimport.droid.oauth2.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class HookedWebView extends WebView {
    
    private static final String TAG = HookedWebView.class.getSimpleName();
    
    private static final String USERSCRIPT_WRAP = "javascript:alert('MAGIC'+%s);";
    private static final ViewGroup.LayoutParams DEFAULT_LAYOUT_SIZE 
            = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    private static final NullListeners DEFAULT_LISTENER = new NullListeners();
    
    private OnReceivedPageTitleListener receivedPageTitleListener = DEFAULT_LISTENER;
    private OnPageFinishedListener pageFinishedListener = DEFAULT_LISTENER;
    private OnPageStartedListener pageStartedListener = DEFAULT_LISTENER;
    private OnUserscriptFinishedListener userscriptFinishedListener = DEFAULT_LISTENER;
    private OnProgressChangedListener progressChangedListener = DEFAULT_LISTENER;
    
    private String userscript;

    
    
    public HookedWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        configure();
    }
    public HookedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        configure();
    }
    public HookedWebView(Context context) {
        super(context);
        setLayoutParams(DEFAULT_LAYOUT_SIZE);
        configure();
    }
    
    private void configure() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSaveFormData(false);
        getSettings().setSavePassword(false);
        
        super.setWebChromeClient(new MyWebChromeClient());
        super.setWebViewClient(new MyWebViewClient());
    }
    

    public void setOnPageFinishedListener(OnPageFinishedListener pageFinishedListener) {
        this.pageFinishedListener = pageFinishedListener;
    }

    public void setOnPageStartedListener(OnPageStartedListener pageStartedListener) {
        this.pageStartedListener = pageStartedListener;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener progressChangedListener) {
        this.progressChangedListener = progressChangedListener;
    }

    public void setOnReceivedPageTitleListener(OnReceivedPageTitleListener receivedPageTitleListener) {
        this.receivedPageTitleListener = receivedPageTitleListener;
    }

    public void setOnUserscriptFinishedListener(OnUserscriptFinishedListener userscriptFinishedListener) {
        this.userscriptFinishedListener = userscriptFinishedListener;
    }
    
    public String getUserscript() {
        return userscript;
    }

    //exp: document.documentElement.innerHTML
    public void setUserscript(String userscript) {
        this.userscript = userscript;
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    
    
    //--------------- NESTED CLASSES ---------------//
    
    protected class MyWebChromeClient extends WebChromeClient {
        
        @Override
        public void onProgressChanged(WebView view, int progress) {
            progressChangedListener.onProgressChanged(progress, view.getUrl());
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.d(TAG, "Received title: " + title);
            receivedPageTitleListener.onReceivedPageTitle(title, view.getUrl());
            
            super.onReceivedTitle(view, title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult jsResult) {
            if (message.startsWith("MAGIC")) {
                String result = message.substring(5);
                
                Log.d(TAG, "Received result of userscript: " + result);
                userscriptFinishedListener.onUserscriptFinished(result, url);

                jsResult.confirm();
                return true;

            } else {
                return super.onJsAlert(view, url, message, jsResult);
            }
        }
    }
    
    protected class MyWebViewClient extends WebViewClient {
        
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Loading page: " + url);
            pageStartedListener.onPageStarted(url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "Finished page: " + url);
            pageFinishedListener.onPageFinished(url);

            if (userscript != null) {
                String script = String.format(USERSCRIPT_WRAP, userscript);

                Log.d(TAG, "Loading script: " + script);
                view.loadUrl(script);
            }
        }
    }
    
    protected static class NullListeners implements OnPageFinishedListener, OnPageStartedListener, 
            OnReceivedPageTitleListener, OnProgressChangedListener, OnUserscriptFinishedListener{
        public void onPageFinished(String url) { /* do nothing */}
        public void onPageStarted(String url) { /* do nothing */}
        public void onReceivedPageTitle(String pageTitle, String url) { /* do nothing */}
        public void onProgressChanged(int progress, String url) { /* do nothing */}
        public void onUserscriptFinished(String returnValue, String url) { /* do nothing */}
    }
    
    
    //--------------- NESTED INTERFACES ---------------//
    
    public interface OnPageFinishedListener {
        void onPageFinished(String url);
    }
    
    public interface OnPageStartedListener {
        void onPageStarted(String url);
    }
    
    public interface OnReceivedPageTitleListener {
        void onReceivedPageTitle(String pageTitle, String url);
    }
    
    public interface OnProgressChangedListener {
        void onProgressChanged(int progress, String url);
    }
    
    public interface OnUserscriptFinishedListener {
        void onUserscriptFinished(String returnValue, String url);
    }
    
}
