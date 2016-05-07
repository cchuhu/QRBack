package huhu.com.qrback.Util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Huhu on 8/7/15.
 * 通用适配器的抽象类
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected Context mContext;

    public CommonAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //抽象方法不能有方法体
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);


}
