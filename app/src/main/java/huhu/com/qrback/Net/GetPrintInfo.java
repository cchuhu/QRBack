package huhu.com.qrback.Net;

/**
 * Created by antdlx on 2016/5/7.
 */
public class GetPrintInfo {
    public GetPrintInfo(String url, String mid,final GetSuccess success, final GetFailed fail) {
        new NetConnection(url, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                success.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                fail.onFailed();

            }
        },"mid",mid) ;
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
