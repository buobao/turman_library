package cn.com.library.turman.turman_library.httpurlconnection;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by diaoqf on 2016/12/15.
 */

public class HttpUtil {

    private static Map<String,String> headers;

    /**
     * 添加请求头
     * @param headers
     */
    public static void addHeader(Map<String, String> headers) {
        if (HttpUtil.headers  == null) {
            HttpUtil.headers = new HashMap<>();
        }
        HttpUtil.headers.putAll(headers);
    }


    /**
     * Get请求
     * @param urlString
     * @param listener
     */
    public static void Get(final String urlString, final HashMap<String,String> params, final HttpCallbackListener listener) {
        new Thread(()->{
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                //根据URL地址创建URL对象
                if (params != null && params.size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(urlString).append("?");
                    for (String key :params.keySet()) {
                        sb.append(key).append("=").append(params.get(key)).append("&");
                    }
                    sb.deleteCharAt(sb.length() - 1 );
                    url = new URL(sb.toString());
                } else {
                    url = new URL(urlString);
                }
                //获取HttpURLConnection对象
                httpURLConnection = (HttpURLConnection) url.openConnection();
                //设置请求方式
                httpURLConnection.setRequestMethod("GET");
                //设置连接超时时间
                httpURLConnection.setConnectTimeout(5000);
                //设置读取超时时间
                httpURLConnection.setReadTimeout(8000);
                // 如果需要设置apikey，如下设置：
//                httpURLConnection.setRequestProperty("apikey", "1b18****13f3****729210d6****8e29");

                //添加请求头
                if (headers != null) {
                    for (String key:headers.keySet()) {
                        httpURLConnection.addRequestProperty(key, headers.get(key));
                    }
                }

                // 响应码为200表示成功，否则失败。
                if (httpURLConnection.getResponseCode() != 200) {
                    Logger.e("请求失败");
                }

                // 获取网络的输入流
                InputStream is = httpURLConnection.getInputStream();
                // 读取输入流中的数据
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = bis.read(bytes)) != -1) {
                    baos.write(bytes, 0, len);
                }
                bis.close();
                is.close();
                // 响应的数据
                byte[] response = baos.toByteArray();

                if (listener != null) {
                    // 回调onFinish()方法
                    listener.onFinish(response);
                }

            }catch (MalformedURLException e) {
                if (listener != null) {
                    // 回调onError()方法
                    listener.onError(e);
                }
            } catch (IOException e) {

                if (listener != null) {
                    // 回调onError()方法
                    listener.onError(e);
                }
            } finally {
                if (httpURLConnection != null) {
                    // 释放资源
                    httpURLConnection.disconnect();
                }
            }
        }).start();
    }

    /**
     * Post请求
     * @param urlString
     * @param listener
     */
    public static void Post(final String urlString, Map<String, String> params, final HttpCallbackListener listener) {
        new Thread(() -> {
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(urlString);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(8000);

                // 设置运行输入
                httpURLConnection.setDoInput(true);
                // 设置运行输出
                httpURLConnection.setDoOutput(true);

                //添加请求头
                if (headers != null) {
                    for (String key:headers.keySet()) {
                        httpURLConnection.addRequestProperty(key, headers.get(key));
                    }
                }

                // 请求的数据
                if (params != null && params.size() > 0) {
                    String data = "";
                    for (String key : params.keySet()) {
                        data += key + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&";
                    }
                    data = data.substring(0,data.length() - 1);

                    // 将请求的数据写入输出流中
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    bos.write(data.getBytes());
                    bos.flush();
                    bos.close();
                    os.close();
                }

                if (httpURLConnection.getResponseCode() == 200) {
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    while ((len = bis.read(bytes)) != -1) {
                        baos.write(bytes, 0, len);
                    }
                    is.close();
                    bis.close();
                    // 响应的数据
                    byte[] response = baos.toByteArray();

                    if (listener != null) {
                        // 回调onFinish()方法
                        listener.onFinish(response);
                    }
                } else {
                    Logger.e("请求失败");
                }
            } catch (MalformedURLException e) {
                if (listener != null) {
                    // 回调onError()方法
                    listener.onError(e);
                }
            } catch (IOException e) {
                if (listener != null) {
                    // 回调onError()方法
                    listener.onError(e);
                }
            } finally {
                if (httpURLConnection != null) {
                    // 最后记得关闭连接
                    httpURLConnection.disconnect();
                }
            }
        }).start();
    }

}






























