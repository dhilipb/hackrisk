package com.mu.mothersunited.model;

public class Comment
{
    public String questionId;
    public String userId;
    public long time;
    public String text;

    public Comment(String questionId, String userId, long time, String text)
    {
        this.questionId = questionId;
        this.userId = userId;
        this.time = time;
        this.text = text;
    }

}
