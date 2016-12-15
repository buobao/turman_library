package cn.com.library.turman.turman_library.httpurlconnection;

/**
 * Created by diaoqf on 2016/12/15.
 */

public interface HttpCallbackListener {
    void onFinish(byte[] response);
    void onError(Exception e);
}
