package com.luwei.itemdecorationdemo.stick;

/**
 * Created by Mr_Zeng
 *
 * @date 2018/12/4
 */
public class StickBean {

    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_HEADER_2 = 3;

    int type;
    String content;

    public StickBean(){}

    public StickBean(String content,int type){
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
