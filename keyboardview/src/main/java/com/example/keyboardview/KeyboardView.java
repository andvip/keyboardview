package com.example.keyboardview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by maw on 2017/10/23.
 * 自定义数字键盘
 */

public class KeyboardView extends PopupWindow implements View.OnClickListener {

    // 用于保存PopupWindow的宽度
    private int width;
    // 用于保存PopupWindow的高度
    private int height;

    private Context mContext;
    private View contentView;

    private EditText mEditText;
    Editable editable;

    private ViewGroup mRootView;
    protected FrameLayout.LayoutParams mKeyboardContainerLayoutParams;

    public KeyboardView(Context context) {
        this.mContext = context;
        this.initPopupWindow(context);


    }

    public void setEditText(EditText editText) {
        mEditText = editText;
        editable = mEditText.getText();
    }

    public EditText getEditText() {
        return mEditText;
    }

    private void initPopupWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contentView = inflater.inflate(R.layout.keyboard_view, null);
        this.setContentView(contentView);
        // 绘制
        this.setHeightAndWidth();

        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.KeyoBoardAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable background = new ColorDrawable(0x4f000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(background);
        // 对按钮设置监听
        listenExceptLayout(contentView);
        // 长按删除操作
        contentView.findViewById(R.id.tvDel).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editable.clear();
                return false;
            }
        });
        contentView.findViewById(R.id.llDel).setOnClickListener(this);


    }

    /**
     * 弹出键盘
     */
    public void show() {
        mRootView = (ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById
                (android.R.id.content);
        showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        int start = mEditText.getSelectionStart();
        int i = view.getId();
        if (i == R.id.tv100) {
            editable.clear();
            editable.insert(0, "100");

        } else if (i == R.id.tv500) {
            editable.clear();
            editable.insert(0, "500");

        } else if (i == R.id.tv1000) {
            editable.clear();
            editable.insert(0, "1000");

        } else if (i == R.id.tvDel || i == R.id.llDel) {
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }

        } else if (i == R.id.tvDone) {
            mActionDoneClickListener.onActionDone(editable);
            dismiss();

        } else if (i == R.id.tv00 || i == R.id.tv000) {
            if (TextUtils.isEmpty(editable)) {
                return;
            } else {
                char a = editable.charAt(0);
                if (a != 0) {
                    editable.insert(start, ((TextView) view).getText());
                }
            }

        } else {
            editable.insert(start, ((TextView) view).getText());

        }
    }


    /**
     * 并且初始化popupWindowView的尺寸
     */
    private void setHeightAndWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        int screenHeight = outMetrics.heightPixels;
        this.width = screenWidth;
        this.height = (int) (screenHeight * 0.3);
        // 设置弹出窗体的宽
        this.setWidth(width);
        // 设置弹出窗体的高
        this.setHeight(height);
    }

    /**
     * @param root 监听所有rootView的子控件
     */
    private void listenExceptLayout(View root) {
        if (root instanceof ViewGroup) {//Android的布局继承自ViewGroup
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                listenExceptLayout(parent.getChildAt(i));
            }
        } else {//如果不是一个布局，加入监听
            root.setClickable(true);//设为可点击
            root.setOnClickListener(this);
        }
    }


    //可以在主界面处理“确定”按钮操作
    public ActionDoneClickListener mActionDoneClickListener;

    public interface ActionDoneClickListener {
        void onActionDone(CharSequence charSequence);
    }

    public void setActionDoneClickListener(ActionDoneClickListener actionDoneClickListener) {
        mActionDoneClickListener = actionDoneClickListener;
    }


}
