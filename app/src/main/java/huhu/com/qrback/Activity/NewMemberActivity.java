package huhu.com.qrback.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import huhu.com.qrback.Net.AddMemberConnection;
import huhu.com.qrback.R;
import zxing.EncodingHandler;

/**
 * 添加新成员
 */
public class NewMemberActivity extends Activity {
    private EditText edt_name, edt_job, edt_phone;
    private Button btn_sendMessage;
    private ImageView img_qrcode;
    private String mid, name, job, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);
        Intent intent = getIntent();
        mid = intent.getExtras().get("mid").toString();

        try {
            initViews();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化视图资源
     */
    private void initViews() throws UnsupportedEncodingException, WriterException {
        edt_name = (EditText) findViewById(R.id.edt_membername);
        edt_job = (EditText) findViewById(R.id.edt_job);
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        btn_sendMessage = (Button) findViewById(R.id.btn_sendmessage);
        img_qrcode = (ImageView) findViewById(R.id.img_qrcode);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //生成二维码并发送给该成员
                    getQRcode(edt_name.getText().toString().trim());
                    //将该成员写入数据库
                    addNewMember();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * 生成二维码的方法
     */
    private void getQRcode(String name) throws UnsupportedEncodingException, WriterException {
        String contentString = String.valueOf(name);
        String contentString2 = URLEncoder.encode(contentString.toString(), "utf-8");
        if (!contentString.equals("")) {
            // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（200*200）
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString2, 200);
            img_qrcode.setImageBitmap(qrCodeBitmap);
        } else {
            Toast.makeText(NewMemberActivity.this, "写入字符串为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewMember() {
        name = edt_name.getText().toString();
        job = edt_job.getText().toString();
        phone = edt_phone.getText().toString();
        if (name.equals("") || job.equals("") || phone.equals("")) {
            Toast.makeText(NewMemberActivity.this, "请完善人员信息", Toast.LENGTH_SHORT).show();
        } else {
            new AddMemberConnection(mid, name, job, phone, new AddMemberConnection.AddSuccess() {
                @Override
                public void onSuccess(String result) {
                    switch (result) {
                        case "-1":
                            break;
                        case "1":
                            Toast.makeText(NewMemberActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            edt_name.setText("");
                            edt_phone.setText("");
                            edt_job.setText("");
                            break;
                        case "2":
                            break;
                    }

                }
            }, new AddMemberConnection.AddFailed() {
                @Override
                public void onFailed() {

                }
            });

        }
    }

}
