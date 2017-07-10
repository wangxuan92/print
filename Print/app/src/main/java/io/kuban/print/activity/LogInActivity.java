package io.kuban.print.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.kuban.print.CustomerApplication;
import io.kuban.print.R;
import io.kuban.print.base.ActivityManager;
import io.kuban.print.model.CheckPrinterModel;
import io.kuban.print.util.ErrorUtil;
import io.kuban.print.util.ToastUtils;
import io.kuban.print.util.UserManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录界面
 * <p/>
 * Created by wangxuan on 16/10/28.
 */
public class LogInActivity extends BaseCompatActivity {
    @BindView(R.id.phone_num)
    EditText phone_num;

    private String printerId;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = UserManager.getUrl();
        String type = getIntent().getStringExtra(ActivityManager.TYPE);
        CustomerApplication.Isexit = false;
        if (!TextUtils.isEmpty(type) && type.equals("SetUpTheActivity")) {

        } else {
            if (!TextUtils.isEmpty(url)) {
                ActivityManager.startWebActivity(LogInActivity.this, new Intent(), url);
                finish();
            }
        }
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
    }

    /**
     * 检验打印机编号是否正确
     */
    public void getCheckPrinter(final String printer_id) {
        Call<CheckPrinterModel> createSessionCall = getKubanApi().getCheckPrinter(printer_id);
        createSessionCall.enqueue(new Callback<CheckPrinterModel>() {
            @Override
            public void onResponse(Call<CheckPrinterModel> call, Response<CheckPrinterModel> response) {
                if (null != response.body()) {
                    CheckPrinterModel cm = response.body();
                    UserManager.getRemove();
                    UserManager.setUrl(cm.url);
                    ActivityManager.startWebActivity(LogInActivity.this, new Intent(), cm.url);
                    finish();
                    if (null != CustomerApplication.webActivity) {
                        CustomerApplication.webActivity.finish();
                    }
                    dismissProgressDialog();
                    return;
                } else {
                    ErrorUtil.handleError(LogInActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<CheckPrinterModel> call, Throwable t) {
                Log.e("查询该设备是否已存在===========", call + "失败" + t);
                ErrorUtil.handleError(activity, t);
            }
        });
    }

    @OnClick(R.id.login_button)
    public void login(View view) {
        if (!isNetworkConnected()) {
            ToastUtils.showShort(this, "网络异常请检查网络");
        }
        printerId = phone_num.getText().toString().trim();
        if (!TextUtils.isEmpty(printerId)) {
            showProgressDialog();
            getCheckPrinter(printerId);
        } else {
            ToastUtils.customShort(LogInActivity.this, "请输入打印机编号");
        }
    }

    @OnClick(R.id.exit_img)
    public void exit(View view) {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}