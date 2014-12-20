package com.szu.main.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lgp on 2014/7/29.
 */
public class ListItemData implements Serializable{
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
