package com.mu.mothersunited;

import com.mu.mothersunited.model.Comment;
import com.mu.mothersunited.model.PushToken;
import com.mu.mothersunited.model.Question;
import com.mu.mothersunited.model.Vote;
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
    void vote(@Body Vote vote, Callback<Question> callback);

    @POST("/comments/new")
    void createComment(@Body Comment comment, Callback<Comment> callback);

    @GET("/comments")
    void getComments(@Query("questionId") String questionId, Callback<List<Comment>> callback);

    @POST("/push/new")
    void addPushToken(@Body PushToken pushToken, Callback<List<Comment>> callback);

}
