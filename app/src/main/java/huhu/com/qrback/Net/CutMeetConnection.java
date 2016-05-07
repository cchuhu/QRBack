package huhu.com.qrback.Net;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/7/16.
 */
public class CutMeetConnection {
    public CutMeetConnection(String mid, final CutSuccess loginSuccess, final CutFailed loginFailed) {
        new NetConnection(Config.URL_CUT, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                loginSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                loginFailed.onFailed();

            }
        }, "mid", mid);
        //如何上传list列表？

    }

    /**
     * 成功接口
     */
    public interface CutSuccess {
        void onSuccess(String result);
    }

    /**
     * 失败接口
     */
    public interface CutFailed {
        void onFailed();
    }

}
