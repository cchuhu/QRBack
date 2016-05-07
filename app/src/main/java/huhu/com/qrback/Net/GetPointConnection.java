package huhu.com.qrback.Net;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/7/16.
 * 获取空闲签到点的信息
 */
public class GetPointConnection {
    public GetPointConnection(final GetSuccess getSuccess, final GetFailed getFailed) {
        new NetConnection(Config.URL_GETSIGN, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                getSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                getFailed.onFailed();

            }
        });

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
