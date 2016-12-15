package cn.com.library.turman.turman_library.httpurlconnection.bean;

/**
 * Created by diaoqf on 2016/12/15.
 */

public class ActiveEntity extends BaseEntity{

    /**
     * id : topic18
     * group : 专题
     * tags : 购房指南
     * url : http://sh.centanet.com/abl_wap/zhuanti/20161214/index.html
     * img : upload/images/2016/12/14173220174.jpg
     * title : 找中原，安心家
     * subtitle : 买房不愁，安心入住！
     */

    private String id;
    private String group;
    private String tags;
    private String url;
    private String img;
    private String title;
    private String subtitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
