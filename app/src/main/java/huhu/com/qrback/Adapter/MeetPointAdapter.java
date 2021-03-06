package huhu.com.qrback.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import huhu.com.qrback.R;
import huhu.com.qrback.Util.CommonAdapter;
import huhu.com.qrback.Util.PointsBean;
import huhu.com.qrback.Util.ViewHolder;

/**
 * Created by Huhu on 5/7/16.
 */
public class MeetPointAdapter extends CommonAdapter {
    public MeetPointAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, R.layout.meetitem, position);
        //自己定义的Bean
        PointsBean bean = (PointsBean) mDatas.get(position);
        //使用holder设置数据
        ((TextView) holder.getView(R.id.tv_mid)).setText(bean.getSpoint());
        ((TextView) holder.getView(R.id.tv_mname)).setText(bean.getSnum());
        return holder.getmConvertView();
    }
}
