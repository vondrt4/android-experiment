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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import cz.cvut.jirutjak.fastimport.droid.R;

/**
 * Specialized dialog widget to embed webview.
 * 
 * @author Jakub Jirutka <jakub@jirutka.cz>
 */
public class WebViewDialog extends Dialog {
    
    private final WebView webview;
    private ProgressBar progressBar;
    private TextView titleView;
    private TextView addressView;
    
    
    public WebViewDialog(Context context, WebView webView) {
        super(context);
        this.webview = webView;
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webdialog);
        
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        titleView = (TextView) findViewById(R.id.title);
        addressView = (TextView) findViewById(R.id.address);
        
        FrameLayout content = (FrameLayout) findViewById(R.id.content);
        content.addView(webview);

        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    }

    
    
    public void setAddress(String url) {
        addressView.setText(url);
    }
    
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        
//        int visibility = progress > 99 ? View.INVISIBLE : View.VISIBLE;
//        progressBar.setVisibility(visibility);
    }
    
    @Override
    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        titleView.setText(titleId);
    }

    public WebView getWebview() {
        return webview;
    }
    
}
