package cn.com.library.turman.turman_library.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.library.turman.turman_library.R;
import cn.com.library.turman.turman_library.httpurlconnection.HttpCallbackListener;
import cn.com.library.turman.turman_library.httpurlconnection.HttpUtil;
import cn.com.library.turman.turman_library.httpurlconnection.bean.ActiveEntity;
import cn.com.library.turman.turman_library.httpurlconnection.bean.ResultBean;

public class MainActivity extends AppCompatActivity {

    private List<String> datas;
    private ArrayAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        ListView listView = (ListView) findViewById(R.id.list_view);
        datas = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);

        /**
         * 请求数据
         */
        HttpUtil.Get("https://apish.centanet.com/v3/zfapi/json/reply/PreferentialRequest",
                new HashMap(){{
                    put("pageIndex","1");
                    put("pageCount","20");
                }},
                new HttpCallbackListener() {
            @Override
            public void onFinish(byte[] response) {
                String s = new String(response);
                Gson gson = new Gson();
                ResultBean<ActiveEntity> resultBean = gson.fromJson(s, new TypeToken<ResultBean<ActiveEntity>>(){}.getType());
                if (resultBean.getResult() != null && resultBean.getResult().size()> 0) {
                    for (ActiveEntity entity : resultBean.getResult()) {
                        datas.add(entity.getTitle());
                    }
                    handler.sendEmptyMessage(0x01);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }
}
































