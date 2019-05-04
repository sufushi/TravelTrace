package com.rdc.project.traveltrace.view.swipe_away_dialog.dialog_factory;

import android.text.InputType;
import android.widget.EditText;

public class InputPasswordDialog extends InputTextDialog {

    public InputPasswordDialog(String tips, String hint, String originalText, OnInputTextListener onInputTextListener) {
        super(tips, hint, originalText, onInputTextListener);
    }

    @Override
    protected void setInputType(EditText editText) {
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
