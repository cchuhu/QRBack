package huhu.com.qrback.Net;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/7/16.
 */
public class AddMemberConnection {
    public AddMemberConnection(String mid, String pname, String pjob, String ptel, final AddSuccess addSuccess, final AddFailed addFailed) {

        new NetConnection(Config.URL_ADDMEMBER, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                addSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                addFailed.onFailed();

            }
        }, "mid", mid, "pname", pname, "pjob", pjob, "ptel", ptel);

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
