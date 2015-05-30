package com.mu.mothersunited.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class Question {
    @SerializedName(value="_id")
    public String questionId;

    public long time;

    public String creatorId;

    public String creatorName;

    public int creatorAge;

    public String title;

    public List<User> upVotes;

    public List<User> downVotes;

    public int pregnancyMonth;

    public List<String> visibleFacebookIds;

    public Question()
    {
        upVotes = new ArrayList<>();
        downVotes = new ArrayList<>();
    }

}
