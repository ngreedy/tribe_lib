package com.gs.buluo.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gs.buluo.common.R;


public class CustomAlertDialog extends Dialog {


    public CustomAlertDialog(Context context) {
        super(context, R.style.sheet_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;  //自定义视图
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private boolean cancelable = true;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public void setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 获取自定义视图
         */
        public View getContentView() {
            return contentView;
        }

        public void setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
        }

        public void setNegativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = positiveButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = negativeButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public CustomAlertDialog create() {

            final CustomAlertDialog dialog = new CustomAlertDialog(context);

            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);

            dialog.setCancelable(cancelable);

            if (positiveButtonText == null && negativeButtonText == null) {
                View buttonGroup = layout.findViewById(R.id.my_dialog_button);
                buttonGroup.setVisibility(View.GONE);
            }else if (positiveButtonText==null||negativeButtonText==null){
                layout.findViewById(R.id.vertical_line).setVisibility(View.GONE);
            }

            if (positiveButtonText != null) {
                TextView positiveBtn = (TextView) layout.findViewById(R.id.positiveButton);
                positiveBtn.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                            dialog.dismiss();
                        }
                    });
                } else {
                    positiveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }

            if (negativeButtonText != null) {
                TextView negativeBtn = (TextView) layout.findViewById(R.id.negativeButton);
                negativeBtn.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                            dialog.dismiss();
                        }
                    });
                } else {
                    negativeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }


            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else {
                layout.findViewById(R.id.message).setVisibility(View.GONE);
            }

            if (contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(
                        contentView);
            }

            if (title != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            } else {
                layout.findViewById(R.id.title).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
