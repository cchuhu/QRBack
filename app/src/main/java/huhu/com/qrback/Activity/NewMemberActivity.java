package huhu.com.qrback.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private Bitmap qrCodeBitmap;

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
                    try {
                        sendMessage("15165151661");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            qrCodeBitmap = EncodingHandler.createQRCode(contentString2, 200);
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
                public void onSuccess(String result) throws IOException {
                    switch (result) {
                        case "-1":
                            break;
                        case "1":
                            Toast.makeText(NewMemberActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            edt_name.setText("");
                            edt_phone.setText("");
                            edt_job.setText("");
                           //调用彩信发送界面
                            //sendMessage(phone);

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

    private void sendMessage(String phone) throws IOException {

        String url = saveBitmap(phone, qrCodeBitmap);
        Log.e("url", url);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        intent.putExtra("subject", "彩信主题");
        intent.putExtra("sms_body", "body");
        intent.putExtra("address", phone);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
        intent.setType("image/*");
        startActivity(intent);
    }

    /**
     * 保存图片到本地的方法
     */
    public String saveBitmap(String phone, Bitmap bitmap) throws IOException {
        String SavePath = getSDCardPath();
        File path = new File(SavePath);
        //文件
        String filepath = SavePath + "/qr.png";
        File file = new File(filepath);
        if (!path.exists()) {
            path.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        if (null != fos) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return filepath;
    }

    private String getSDCardPath() {
        File sdcardDir = null;
        //判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }
}
