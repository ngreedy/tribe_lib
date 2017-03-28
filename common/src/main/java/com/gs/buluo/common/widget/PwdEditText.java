package com.gs.buluo.common.widget;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gs.buluo.common.R;

/**
 * Created by hjn on 2016/11/21.
 */
public class PwdEditText extends RelativeLayout {
    private EditText editText;
    private ImageView[] imageViews;//使用一个数组存储密码框
    private StringBuffer stringBuffer = new StringBuffer();//存储密码字符
    private int count = 6;
    private String strPassword;//密码字符串

    public PwdEditText(Context context) {
        this(context, null);
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageViews = new ImageView[6];
        View view = View.inflate(context, R.layout.custom_pwd, this);

        editText = (EditText) findViewById(R.id.item_edittext);
        imageViews[0] = (ImageView) findViewById(R.id.item_password_iv1);
        imageViews[1] = (ImageView) findViewById(R.id.item_password_iv2);
        imageViews[2] = (ImageView) findViewById(R.id.item_password_iv3);
        imageViews[3] = (ImageView) findViewById(R.id.item_password_iv4);
        imageViews[4] = (ImageView) findViewById(R.id.item_password_iv5);
        imageViews[5] = (ImageView) findViewById(R.id.item_password_iv6);
        editText.setCursorVisible(false);//将光标隐藏
        setListener();
    }

    public void showKeyBoard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
            }
        }, 500);
    }

    public void dismissKeyBoard() {
        InputMethodManager m = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //重点   如果字符不为""时才进行操作
                if (!editable.toString().equals("")) {
                    if (stringBuffer.length() > 5) {
                        editText.setText("");
                        return;
                    } else {
                        //将文字添加到StringBuffer中
                        stringBuffer.append(editable);
                        editText.setText("");       //添加后将EditText置空  造成没有文字输入的错局
                        count = stringBuffer.length();//记录stringbuffer的长度
                        strPassword = stringBuffer.toString();
                        if (stringBuffer.length() == 6) {
                            //文字长度位6   则调用完成输入的监听
                            if (inputCompleteListener != null) {
                                inputCompleteListener.inputComplete();
                            }
                        }
                    }

                    for (int i = 0; i < stringBuffer.length(); i++) {
                        imageViews[i].setImageResource(R.drawable.mine_password);
                    }
                }
            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });
    }

    public boolean onKeyDelete() {
        if (count == 0) {
            count = 6;
            return true;
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.delete((count - 1), count);
            count--;
            strPassword = stringBuffer.toString();
            imageViews[stringBuffer.length()].setImageResource(R.drawable.mine_password_empty);
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private InputCompleteListener inputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public interface InputCompleteListener {
        void inputComplete();
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getStrPassword() {
        return strPassword;
    }

    public void setContent(String content) {
        editText.setText(content);
    }

    public void clear() {
        for (int i = 0; i < 6; i++) {
            imageViews[i].setImageResource(R.drawable.mine_password_empty);
        }
        stringBuffer = new StringBuffer();
        strPassword = "";
    }
}
