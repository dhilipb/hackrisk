package com.mu.mothersunited.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class Question {
    @SerializedName(value="_id")
    public String questionId;

    public long time;

    public boolean isPublic;

    @SerializedName(value="facebookId")
    public String creatorId;

    public String creatorName;

    @SerializedName(value="age")
    public int creatorAge;

    public String title;

    public List<User> upVotes;

    public List<User> downVotes;

    public int pregnancyMonth;

}
