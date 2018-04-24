package com.yidao.threekmo.db_bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2017\8\28 0028.
 */

@Entity
public class SearchHistroyBean {
    @Id
    private Long id;
    @Property(nameInDb = "HISTROY")
    private String histroy;
    @Generated(hash = 404619857)
    public SearchHistroyBean(Long id, String histroy) {
        this.id = id;
        this.histroy = histroy;
    }
    @Generated(hash = 1113156717)
    public SearchHistroyBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHistroy() {
        return this.histroy;
    }
    public void setHistroy(String histroy) {
        this.histroy = histroy;
    }
}
