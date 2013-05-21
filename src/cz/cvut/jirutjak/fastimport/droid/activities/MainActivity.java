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

package cz.cvut.jirutjak.fastimport.droid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.googlecode.androidannotations.annotations.*;
import cz.cvut.jirutjak.fastimport.droid.R;
import cz.cvut.jirutjak.fastimport.droid.controllers.MainController;

@EActivity(R.layout.main)
@OptionsMenu(R.menu.main)
public class MainActivity extends Activity {

    @Bean
    protected MainController controller;
    
    @ViewById(R.id.main_content)
    protected EditText content;
    
    @ViewById(R.id.main_title)
    protected TextView title;
    
    @ViewById(R.id.main_created)
    protected TextView created;
    
/*  This is how it is done without AndroidAnnotations  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	
    	Button b = (Button) findViewById(R.id.main_reload);
    	
    	((View) b).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showNoConnection();
			}
		});
    	
    	final TextView tv = (TextView) findViewById(R.id.main_title);
    	
    	findViewById(R.id.main_copy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.setText("prdel");
				
			}
		});
    }
    
*/
    public String getContent() {
        return content.getText().toString();
    }

    public void setContent(String text) {
        if (text != null) content.setText(text);
        else content.setText("");
    }

    public void setTitle(String text) {
        if (text != null) title.setText(text);
        else title.setText(R.string.default_title);
    }
    
    public void setCreated(String date) {
        if (date != null) created.setText(date);
        else created.setText("--");
    }

    public void showNoConnection() {
        Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
    }

    
    @Click(R.id.main_reload)
    protected void reloadClicked() {
        controller.reload();
    }

    @Click(R.id.main_copy)
    protected void copyClicked() {
        controller.copyToClipboard();
    }

    @OptionsItem(R.id.main_menu_history)
    protected void menuHistoryClicked() {
        controller.showHistory();
    }
    
    @OptionsItem(R.id.main_menu_authorize)
    protected void menuAuthorizeClicked() {
        controller.showAuthorizationDialog();
    }
    
}
