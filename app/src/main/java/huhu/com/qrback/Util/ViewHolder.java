package huhu.com.qrback.Util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Huhu on 8/7/15.
 */
public class ViewHolder {
    //Item里的控件列表
    private SparseArray<View> mViews;
    //位置
    private int mPositon;
    //布局
    private View mConvertView;

    //构造方法
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPositon = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    //入口方法
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {

            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPositon = position;
            Log.e("mPosition", ""+position);
            return holder;
        }
    }

    //获取convertView
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return view
     */
    public <T extends View> T getView(int viewId) {
        //第一次调用是填数据
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
