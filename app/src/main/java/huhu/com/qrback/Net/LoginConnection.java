package huhu.com.qrback.Net;

import huhu.com.qrback.Config.Config;

/**
 * Created by Huhu on 5/6/16.
 * 管理系统登陆的接口
 */

public class LoginConnection {

    public LoginConnection(String rname, String rpass, final LoginSuccess loginSuccess, final LoginFailed loginFailed) {
        new NetConnection(Config.URL_LOGIN, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                loginSuccess.onSuccess(result);
            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                loginFailed.onFailed();

            }
        }, "rname", rname, "rpass", rpass) {
        };

    }

    /**
     * 成功接口
     */
    public interface LoginSuccess {
        void onSuccess(String result);
    }

    /**
     * 失败接口
     */
    public interface LoginFailed {
        void onFailed();
    }

}



