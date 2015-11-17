package com.weatherapp;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Klemen on 17.11.2015.
 */
public class KeyboardKeyManager {

    private IKeyboardListener iKeyboardListener;

    public KeyboardKeyManager(IKeyboardListener iKeyboardListener) {
        this.iKeyboardListener = iKeyboardListener;
    }

    /**
     * Set keyboard done key listener
     *
     * @param editText edit text object
     */
    public void setKeyboardDoneKeyListener(EditText editText) {

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    iKeyboardListener.keyDonePressed();
                }
                return false;
            }
        });
    }
}
