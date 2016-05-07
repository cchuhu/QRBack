package huhu.com.qrback.Net;

/**
 * Created by Huhu on 5/7/16.
 */
public class GetMeetConnection {

    public GetMeetConnection(String url, final GetSuccess loginSuccess, final GetFailed loginFailed) {
        new NetConnection(url, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                loginSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                loginFailed.onFailed();

            }
        }) {
        };

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
