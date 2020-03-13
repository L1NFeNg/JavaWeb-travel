package cn.itcast.travel.domain;

import java.io.Serializable;

/**
 * 分类导航栏实体类
 */
public class Category implements Serializable {

    private int cid;        // 分类导航id
    private String cname;   // 分类导航名称

    public Category() {
    }

    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }
}
