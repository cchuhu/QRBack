package huhu.com.qrback.Net;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/7/16.
 * 会议详细
 */
public class MeetInfoConnection {
    public MeetInfoConnection(String mid, final GetSuccess loginSuccess, final GetFailed loginFailed) {
        new NetConnection(Config.URL_DETAIL, HttpMethod.POST, new NetConnection.SuccessCallback() {
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

    }

    /**
     * 成功接口
     */
    public interface GetSuccess {
        void onSuccess(String result);
    }

    /**
     * 失败接口
     */
    public interface GetFailed {
        void onFailed();
    }

}
