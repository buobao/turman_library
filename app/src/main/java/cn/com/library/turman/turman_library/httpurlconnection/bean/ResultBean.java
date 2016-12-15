package cn.com.library.turman.turman_library.httpurlconnection.bean;

import android.widget.BaseAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by diaoqf on 2016/12/15.
 */

public class ResultBean<T extends BaseEntity> implements Serializable {
    private int ResultNo;
    private int total;

    private List<T> Result;

    public int getResultNo() {
        return ResultNo;
    }

    public void setResultNo(int resultNo) {
        ResultNo = resultNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResult() {
        return Result;
    }

    public void setResult(List<T> result) {
        Result = result;
    }
}
