package huhu.com.qrback.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import huhu.com.qrback.Net.LoginConnection;
import huhu.com.qrback.R;


/**
 * 登陆界面的Activity
 */
public class LoginActivity extends Activity {
    //账号密码输入框
    private EditText edt_account, edt_password;
    //登陆按钮
    private Button btn_login;
    private String name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化布局资源以及添加监听器
     */
    private void init() {
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_account.getText().toString();
                pass = edt_password.getText().toString();
                if (name.equals("") || pass.equals("")) {

                } else {
                    new LoginConnection(name, pass, new LoginConnection.LoginSuccess() {

                        @Override
                        public void onSuccess(String result) {
                            switch (result) {
                                case "1":
                                    Intent i = new Intent(LoginActivity.this, MeetActivity.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                case "-1":
                                    Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                                    break;
                                case "2":
                                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }, new LoginConnection.LoginFailed() {
                        @Override
                        public void onFailed() {
                            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

}

