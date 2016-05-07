package huhu.com.qrback.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import huhu.com.qrback.Activity.MeetDetailActivity;
import huhu.com.qrback.Adapter.MeetListAdapter;
import huhu.com.qrback.Config.Config;
import huhu.com.qrback.Net.GetMeetConnection;
import huhu.com.qrback.Net.MeetInfoConnection;
import huhu.com.qrback.R;
import huhu.com.qrback.Util.MeetBean;

/**
 * 当前会议记录的fragment
 * Created by Huhu on 5/5/16.
 */
public class CurrentFragment extends Fragment {
    //当前会议列表
    private ArrayList<MeetBean> list = new ArrayList<>();
    private ListView lv_current;
    private String mid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current, null);
        lv_current = (ListView) view.findViewById(R.id.lv_current);
        //获取目前的会议列表
        new GetMeetConnection(Config.URL_CURRENT, new GetMeetConnection.GetSuccess() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        MeetBean bean = new MeetBean();
                        bean.setMname(jsonObject.get("mname").toString());
                        bean.setMid(jsonObject.get("mid").toString());
                        list.add(bean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lv_current.setAdapter(new MeetListAdapter(getActivity(), list));
                //为列表添加监听器

            }
        }, new GetMeetConnection.GetFailed() {
            @Override
            public void onFailed() {

            }
        });
        //点击列表项下载数据
        lv_current.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mid = list.get(i).getMid();
                new MeetInfoConnection(mid, new MeetInfoConnection.GetSuccess() {
                    @Override
                    public void onSuccess(String result) {
                        switch (result) {
                            case "-1":
                                Toast.makeText(getActivity(), "服务器错误", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Intent intent = new Intent();
                                intent.putExtra("result", result);
                                intent.putExtra("type", "current");
                                intent.putExtra("mid", mid);
                                intent.setClass(getContext(), MeetDetailActivity.class);
                                startActivity(intent);
                        }

                    }
                }, new MeetInfoConnection.GetFailed() {
                    @Override
                    public void onFailed() {

                    }
                });


            }
        });
        return view;
    }

}
