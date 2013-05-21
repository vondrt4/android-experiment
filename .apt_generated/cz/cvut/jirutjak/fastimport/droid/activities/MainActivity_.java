//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cz.cvut.jirutjak.fastimport.droid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import cz.cvut.jirutjak.fastimport.droid.R.id;
import cz.cvut.jirutjak.fastimport.droid.R.layout;
import cz.cvut.jirutjak.fastimport.droid.controllers.MainController_;

public final class MainActivity_
    extends MainActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.main);
    }

    private void init_(Bundle savedInstanceState) {
        controller = MainController_.getInstance_(this);
    }

    private void afterSetContentView_() {
        created = ((TextView) findViewById(id.main_created));
        content = ((EditText) findViewById(id.main_content));
        title = ((TextView) findViewById(id.main_title));
        {
            View view = findViewById(id.main_reload);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        reloadClicked();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_copy);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    public void onClick(View view) {
                        copyClicked();
                    }

                }
                );
            }
        }
        ((MainController_) controller).afterSetContentView_();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static MainActivity_.IntentBuilder_ intent(Context context) {
        return new MainActivity_.IntentBuilder_(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(cz.cvut.jirutjak.fastimport.droid.R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        switch (item.getItemId()) {
            case id.main_menu_authorize:
                menuAuthorizeClicked();
                return true;
            case id.main_menu_history:
                menuHistoryClicked();
                return true;
            default:
                return false;
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, MainActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public MainActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

    }

}
