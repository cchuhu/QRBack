package huhu.com.qrback.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import huhu.com.qrback.Net.AddMeetConnection;
import huhu.com.qrback.Net.GetPointConnection;
import huhu.com.qrback.R;

/**
 * 添加新会议界面
 */
public class NewMeetActivity extends Activity {
    private EditText edt_name, edt_begintime, edt_endtime, edt_content;
    private ImageButton btn_back;
    private Button btn_sure;
    private ListView lv_point;
    //可用签到点列表
    private ArrayList<String> avaliPointList = new ArrayList<>();
    //适配器
    private ArrayAdapter<String> adapter;
    //选中的签到点列表
    private ArrayList<String> choselist = new ArrayList<>();
    private String name, content, stime, etime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meet);

        initViews();
        getAvailablePoint();


    }

    private void initViews() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_begintime = (EditText) findViewById(R.id.edt_begintime);
        edt_endtime = (EditText) findViewById(R.id.tv_endtime);
        edt_content = (EditText) findViewById(R.id.edt_content);
        btn_sure = (Button) findViewById(R.id.btn_sure);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        lv_point = (ListView) findViewById(R.id.lv_signpoint);
        lv_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackgroundColor(Color.GRAY);
                //将选中的会议添加到列表，选中之后不可取消
                choselist.add(avaliPointList.get(i));
            }
        });


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(NewMeetActivity.this, MeetActivity.class);
                startActivity(intent);
                NewMeetActivity.this.finish();

            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddMeet();
            }
        });

    }


    //获取可用签到点列表
    private void getAvailablePoint() {
        new GetPointConnection(new GetPointConnection.GetSuccess() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        avaliPointList.add(obj.get("spoint").toString());
                        adapter.add(obj.get("spoint").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lv_point.setAdapter(adapter);
            }
        }, new GetPointConnection.GetFailed() {
            @Override
            public void onFailed() {

            }
        });

    }

    //上传会议信息
    private void AddMeet() {
        name = edt_name.getText().toString();
        content = edt_content.getText().toString();
        stime = edt_begintime.getText().toString();
        etime = edt_endtime.getText().toString();
        if (name.equals("") || content.equals("") || stime.equals("") || etime.equals("") || choselist.size() == 0) {
            Toast.makeText(this, "请完善会议信息！", Toast.LENGTH_SHORT).show();
        } else {
            new AddMeetConnection(name, content, stime, etime, choselist, new AddMeetConnection.AddSuccess() {
                @Override
                public void onSuccess(final String result) {

                    btn_sure.setText("添加新成员");
                    btn_sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //带着新创建的mid跳转
                            Intent i = new Intent();
                            i.putExtra("mid", result);
                            i.setClass(NewMeetActivity.this, NewMemberActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
            }, new AddMeetConnection.AddFailed() {
                @Override
                public void onFailed() {

                }
            });
        }
    }
}
