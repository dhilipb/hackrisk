package com.mu.mothersunited;

import com.mu.mothersunited.model.Question;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import java.util.List;

public interface MothersUnitedApi {

    public static final String BASE_URL = "http://mothers-united-server.herokuapp.com";

    @GET("/questions")
    void getQuestions(@Query("facebookId") String facebookId, Callback<List<Question>> callback);

    @POST("/questions/new")
    void createQuestion(@Body Question question, Callback<Question> callback);

    @POST("/questions/vote")
    void vote(@Query("questionId") String questionId, @Query("facebookId") String facebookId, @Query("isUpVote") boolean isUpVote, Callback<Question> callback);

}
