package huhu.com.qrback.Net;

import java.util.ArrayList;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/7/16.
 */
public class AddMeetConnection {
    public AddMeetConnection(String mname, String mcontent, String mstarttime, String mendtime, ArrayList<String> points, final AddSuccess loginSuccess, final AddFailed loginFailed) {

        new NetConnection(Config.URL_ADDMEET, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                loginSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                loginFailed.onFailed();

            }
        }, points, "mname", mname, "mcontent", mcontent, "mstarttime", mstarttime, "mendtime", mendtime);

    }

    /**
     * 成功接口
     */
    public interface AddSuccess {
        void onSuccess(String result);
    }

    /**
     * 失败接口
     */
    public interface AddFailed {
        void onFailed();
    }

}
