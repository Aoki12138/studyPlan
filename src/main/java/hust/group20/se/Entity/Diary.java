package hust.group20.se.Entity;

import java.sql.Timestamp;

public class Diary {
    private Integer diaryID;
    private String diaryName;
    private String body;
    private String keyword;
    private Timestamp createTime;
    private Integer userID;



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
