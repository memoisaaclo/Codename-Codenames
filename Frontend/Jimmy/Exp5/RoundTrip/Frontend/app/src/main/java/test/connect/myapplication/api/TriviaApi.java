package test.connect.myapplication.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import test.connect.myapplication.model.Trivia;

public interface TriviaApi {

    @GET("trivia/all")
    Call<List<Trivia>> GetAllTrivia();

    @POST("trivia/post/{q}/{a}")
    Call<Trivia> PostTriviaByPath(@Path("q") String question, @Path("a") String answer);

    @POST("trivia/post")
    Call<Trivia> PostTriviaByBody(@Body Trivia newTrivia);
}
