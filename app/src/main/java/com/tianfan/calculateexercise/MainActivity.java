package com.tianfan.calculateexercise;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public final static String KEY_EXAM = "com.tianfan.calculateexercise.KEY_EXAM";
    EditText edtxt_max;//最大值
    Spinner spr_operationType;//计算类型
    Spinner spr_operationMode;//小数模式
    EditText editxt_count;//出题数量
    Spinner spr_negative;//正负

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtxt_max = findViewById(R.id.edtxt_max);
        spr_operationType = findViewById(R.id.spr_operationType);
        spr_operationMode = findViewById(R.id.spr_operationMode);
        editxt_count = findViewById(R.id.editxt_count);
        spr_negative = findViewById(R.id.spr_negative);

        editxt_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editxt_count.removeTextChangedListener(this);//去掉监听
                CheckCount();
                editxt_count.addTextChangedListener(this);//恢复监听
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //限制最小题量为10
        editxt_count.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //得到焦点时
                    editxt_count.setText(null);
                } else {
                    //失去焦点时
                    String str = editxt_count.getText().toString();
                    if (!str.isEmpty())
                        if (Integer.parseInt(str) < 10)
                            editxt_count.setText("10");

                }
            }
        });
        //最小出题范围限制为10
        edtxt_max.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //得到焦点时
                    if (edtxt_max.getText() instanceof Spannable) {
                        Spannable spanText = (Spannable) edtxt_max.getText();
                        Selection.setSelection(spanText, edtxt_max.getText().length());
                    }
                } else {
                    //失去焦点时
                    String str = edtxt_max.getText().toString();
                    if (!str.isEmpty())
                        if (Integer.parseInt(str) < 10)
                            edtxt_max.setText("10");

                }
            }
        });
    }

    //控制题量最大为100
    private void CheckCount() {
        String str = editxt_count.getText().toString();
        if (str.length() == 3) {
            editxt_count.setText("100");
            //设置光标到末尾
            if (editxt_count.getText() instanceof Spannable) {
                Spannable spanText = (Spannable) editxt_count.getText();
                Selection.setSelection(spanText, editxt_count.getText().length());
            }
        }
    }

    /**
     * 出题按钮点击事件响应方法
     *
     * @param view
     */
    public void Enter(View view) {
        String maxstr = edtxt_max.getText().toString();
        if (maxstr.isEmpty()) {
            int num = new Random().nextInt(999999909) + 10;//10~999999999
            maxstr = String.valueOf(num);
        }
        String countnum = editxt_count.getText().toString();

        //if(countnum.isEmpty()) countnum="30";
        if (countnum == "" || countnum == null) countnum = "30";

        String[] strarr = new String[]{maxstr,
                spr_operationType.getSelectedItem().toString(),
                spr_operationMode.getSelectedItem().toString(),
                countnum,
                spr_negative.getSelectedItem().toString()};
        //Intent传数组
        Bundle bundle = new Bundle();
        //参数：键，值
        bundle.putStringArray(KEY_EXAM, strarr);
        //参数：源，目标
        Intent intent = new Intent(this, ExamActivity.class);
        //传递bubdle
        intent.putExtras(bundle);
        //启动activity
        startActivity(intent);
    }

    //预设置
    public void Preinstall(View view) {
        final String[] items = {"10", "20", "50", "100", "1000", "10000", "10000000", "999999999"};
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("预设值")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((EditText) findViewById(R.id.edtxt_max)).setText(items[i]);
                    }
                })
                .show();
    }

}
