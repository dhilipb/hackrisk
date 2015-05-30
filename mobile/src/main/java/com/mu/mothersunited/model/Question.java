package com.mu.mothersunited.model;

import java.util.List;

public final class Question
{
    String questionId;
    String askerId;
    String title;
    List<User> upVotes;
    List<User> downVotes;
    long createdTime;
}
