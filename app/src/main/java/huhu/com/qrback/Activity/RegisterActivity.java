package huhu.com.qrback.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import huhu.com.qrback.Config.Config;
import huhu.com.qrback.Net.Register;
import huhu.com.qrback.R;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private EditText et_rname,et_rpass;
    String rname,rpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_rname = (EditText) findViewById(R.id.et_rname);
        et_rpass = (EditText) findViewById(R.id.et_rpass);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rname = et_rname.getText().toString();
                rpass = et_rpass.getText().toString();

                if ((!rpass.isEmpty()) && (!rname.isEmpty())){
                    new Register(Config.URL_REGISTER,rname,rpass, new Register.GetSuccess() {
                        @Override
                        public void onSuccess(String s) {
                            if (s.equals("1")){
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(i);
                                RegisterActivity.this.finish();
                            }else {
                                if (s.equals("2")){
                                    Toast.makeText(RegisterActivity.this,"该用户名已被注册",Toast.LENGTH_LONG).show();
                                }else {
                                    if (s.equals("3")){
                                        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(RegisterActivity.this,"服务器错误",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    }, new Register.GetFailed() {
                        @Override
                        public void onFailed() {

                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"用户名和密码不得为空",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
