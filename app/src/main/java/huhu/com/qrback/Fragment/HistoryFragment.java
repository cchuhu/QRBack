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
 * 历史记录的fragment
 * Created by Huhu on 5/5/16.
 */
public class HistoryFragment extends Fragment {
    private ArrayList<MeetBean> list = new ArrayList<>();
    private ListView lv_history;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        lv_history = (ListView) view.findViewById(R.id.lv_history);
        //获取数据
        new GetMeetConnection(Config.URL_HISTORY, new GetMeetConnection.GetSuccess() {
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
                lv_history.setAdapter(new MeetListAdapter(getActivity(), list));

            }
        }, new GetMeetConnection.GetFailed() {
            @Override
            public void onFailed() {

            }
        });
//点击列表项下载数据
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new MeetInfoConnection(list.get(i).getMid(), new MeetInfoConnection.GetSuccess() {
                    @Override
                    public void onSuccess(String result) {
                        switch (result) {
                            case "-1":
                                Toast.makeText(getActivity(), "服务器错误", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Intent intent = new Intent();
                                intent.putExtra("result", result);
                                intent.putExtra("type", "history");
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
