//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cz.cvut.jirutjak.fastimport.droid.rest.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import cz.cvut.jirutjak.fastimport.droid.AppContext;

public final class ClipboardAuthProvider_
    extends ClipboardAuthProvider
{

    private Context context_;

    private ClipboardAuthProvider_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity = ((Activity) context_);
        return activity.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
            context = ((AppContext) activity.getApplication());
        }
    }

    public static ClipboardAuthProvider_ getInstance_(Context context) {
        return new ClipboardAuthProvider_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
