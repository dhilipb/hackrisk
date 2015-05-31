package com.mu.mothersunited.model;

public class Vote
{
    String questionId;
    String facebookId;
    boolean isUpVote;

    public Vote(String questionId, String facebookId, boolean isUpVote)
    {
        this.questionId = questionId;
        this.facebookId = facebookId;
        this.isUpVote = isUpVote;
    }
}
