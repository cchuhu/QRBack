package huhu.com.qrback.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import huhu.com.qrback.Adapter.MeetPointAdapter;
import huhu.com.qrback.Net.CutMeetConnection;
import huhu.com.qrback.R;
import huhu.com.qrback.Util.PointsBean;

public class MeetDetailActivity extends Activity {
    private TextView tv_name, tv_content, tv_starttime, tv_endtime, tv_status;
    private ListView lv_notfound, lv_pointdetail;
    private Button btn_cut;
    private ArrayList<PointsBean> list_points = new ArrayList<>();
    private ArrayList<String> list_people = new ArrayList<>();
    private ArrayAdapter arrayAdapter;
    private String mid;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);
        initViews();
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化视图
    private void initViews() {
        tv_name = (TextView) findViewById(R.id.tv_getname);
        tv_content = (TextView) findViewById(R.id.tv_getcontent);
        tv_starttime = (TextView) findViewById(R.id.tv_getbegintime);
        tv_endtime = (TextView) findViewById(R.id.tv_getendtime);
        tv_status = (TextView) findViewById(R.id.tv_getmeetstatus);
        lv_notfound = (ListView) findViewById(R.id.lv_notfound);
        lv_pointdetail = (ListView) findViewById(R.id.lv_signpoint);
        btn_cut = (Button) findViewById(R.id.btn_cutmeet);
        arrayAdapter = new ArrayAdapter(MeetDetailActivity.this, android.R.layout.simple_expandable_list_item_1);
    }

    //初始化数据
    private void initData() throws JSONException {
        Intent intent = getIntent();
        String type = intent.getExtras().get("type").toString();
        String result = intent.getExtras().get("result").toString();
        //区分会议类型
        if (type.equals("current")) {
            mid = intent.getExtras().get("mid").toString();
            lv_notfound.setVisibility(View.GONE);
            JSONArray array = new JSONArray(result);
            //获取会议详细信息
            JSONObject meetdetail = array.getJSONObject(0);
            //获取会议签到表信息
            JSONObject meetpoints = array.getJSONObject(1);
            //设置会议详细数据
            tv_name.setText(meetdetail.get("mname").toString());
            tv_content.setText(meetdetail.get("mcontent").toString());
            if (meetdetail.get("mstatus").toString().equals("1")) {
                tv_status.setText("正在进行中");
            } else {
                tv_status.setText("已结束");
            }

            tv_starttime.setText(meetdetail.get("mstarttime").toString());
            tv_endtime.setText(meetdetail.get("mendtime").toString());
            //设置列表
            JSONArray jsonArray = meetpoints.getJSONArray("spoints");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                PointsBean pointsBean = new PointsBean();
                pointsBean.setSpoint(obj.get("spoint").toString());
                pointsBean.setSnum(obj.get("snum").toString());
                list_points.add(pointsBean);
            }
            lv_pointdetail.setAdapter(new MeetPointAdapter(MeetDetailActivity.this, list_points));
            //设置结束会议的监听
            btn_cut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new CutMeetConnection(mid, new CutMeetConnection.CutSuccess() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.equals("1")) {
                                Toast.makeText(MeetDetailActivity.this, "结束成功", Toast.LENGTH_SHORT).show();
                                MeetDetailActivity.this.finish();
                            }
                        }
                    }, new CutMeetConnection.CutFailed() {
                        @Override
                        public void onFailed() {

                        }
                    });
                }
            });


        } else {
            btn_cut.setText("打印会议信息");
            JSONArray array = new JSONArray(result);
            //获取会议详细信息
            JSONObject meetdetail = array.getJSONObject(0);
            //获取会议签到表信息
            JSONObject meetpoints = array.getJSONObject(1);
            //获取缺席人列表
            JSONObject notfound = array.getJSONObject(2);
            //设置会议详细数据
            tv_name.setText(meetdetail.get("mname").toString());
            tv_content.setText(meetdetail.get("mcontent").toString());
            tv_status.setText("已结束");
            tv_starttime.setText(meetdetail.get("mstarttime").toString());
            tv_endtime.setText(meetdetail.get("mendtime").toString());
            //设置列表
            JSONArray jsonArray = meetpoints.getJSONArray("spoints");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                PointsBean pointsBean = new PointsBean();
                pointsBean.setSpoint(obj.get("spoint").toString());
                pointsBean.setSnum(obj.get("snum").toString());
                list_points.add(pointsBean);
            }
            lv_pointdetail.setAdapter(new MeetPointAdapter(MeetDetailActivity.this, list_points));
            //设置缺席人列表
            JSONArray notfind = notfound.getJSONArray("pnames");
            for (int i = 0; i < notfind.length(); i++) {
                JSONObject obj = notfind.getJSONObject(i);
                arrayAdapter.add(obj.getString("pname"));
            }
            lv_notfound.setAdapter(arrayAdapter);


        }


    }
}
