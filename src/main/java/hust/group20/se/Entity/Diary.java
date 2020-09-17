package hust.group20.se.Entity;

import java.sql.Timestamp;

public class Diary {
    private Integer diaryID;
    private String diaryName;
    private String body;
    private String keyword;
    private Timestamp createTime;
    private String color;
    private Integer userID;


    public Diary(Integer diaryID, String diaryName, String body, String keyword, Timestamp createTime, String color, Integer userID) {
        this.diaryID = diaryID;
        this.diaryName = diaryName;
        this.body = body;
        this.keyword = keyword;
        this.createTime = createTime;
        this.color = color;
        this.userID = userID;
    }

    public Diary(){
        this.diaryID = new Integer(1);
        this.diaryName = "日记";
        this.body = "内容";
        this.keyword = "关键字";
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.color = "颜色";
        this.userID = new Integer(1);
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getDiaryID() {
        return diaryID;
    }

    public void setDiaryID(Integer diaryID) {
        this.diaryID = diaryID;
    }

    public String getDiaryName() {
        return diaryName;
    }

    public void setDiaryName(String diaryName) {
        this.diaryName = diaryName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
