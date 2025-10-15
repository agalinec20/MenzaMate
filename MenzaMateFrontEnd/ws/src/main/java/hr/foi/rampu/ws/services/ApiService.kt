package hr.foi.rampu.ws.services


import models.Answer
import models.AverageRatingResponse
import models.FavoriteMenuRequest
import models.FavoriteMenuResponse
import models.Menu
import models.MenuHistoryRequest
import models.MenuHistoryResponse
import models.Question
import models.Rating
import models.SubmitRatingRequest
import models.Survey
import models.SurveyAnswer
import models.TopMenus
import models.UpdateUserRequest
import models.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") userId: Int): UserResponse

    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") userId: Int,
        @Body request: UpdateUserRequest
    )

    @GET("menu/all")
    suspend fun getAllMenus(): List<Menu>

    @GET("FavoriteMenu/{userId}")
    suspend fun getFavoritesForUser(@Path("userId") userId: Int): List<FavoriteMenuResponse>

    @POST("favoritemenu")
    suspend fun addFavoriteMenu(@Body request: FavoriteMenuRequest)

    @DELETE("favoritemenu/{userId}/{menuId}")
    suspend fun removeFavoriteMenu(
        @Path("userId") userId: Int,
        @Path("menuId") menuId: Int
    )

    @GET("survey")
    suspend fun getAllSurveys(): List<Survey>

    @GET("survey/available/{userId}")
    suspend fun getAvailableSurveysForUser(@Path("userId") userId: Int): List<Survey>

    @POST("survey/questions/{questionId}/answers")
    suspend fun submitAnswer(@Path("questionId") questionId: Int, @Body answer: SurveyAnswer)

    @POST("api/Survey/{surveyId}/questions")
    suspend fun addQuestionToSurvey(@Path("surveyId") surveyId: Int,@Body question: Question)

    @GET("survey/answers/{userId}")
    suspend fun getAnswersByUserId(@Path("userId") userId: Int): List<Answer>

    @POST("survey")
    suspend fun createSurvey(@Body survey: Survey)

    @DELETE("survey/{surveyId}")
    suspend fun deleteSurvey(@Path("surveyId") surveyId: Int)

    @POST("rating")
    suspend fun submitRating(@Body rating: SubmitRatingRequest)

    @GET("rating/{menuId}")
    suspend fun getRatingsForMenu(@Path("menuId") menuId: Int): List<Rating>

    @GET("Rating/average/{menuId}")
    suspend fun getAverageRating(@Path("menuId") menuId: Int): AverageRatingResponse

    @POST("MenuHistory")
    suspend fun addMenuHistory(@Body request: MenuHistoryRequest)

    @GET("MenuHistory/{userId}")
    suspend fun getMenuHistoryForUser(@Path("userId") userId: Int): List<MenuHistoryResponse>

    @GET("Rating/top/{count}")
    suspend fun getTopMenus(@Path("count") count: Int): List<TopMenus>

    @GET("survey/{surveyId}/answers")
    suspend fun getAnswersBySurveyId(@Path("surveyId") surveyId: Int): List<Answer>

    @GET("survey/user/{userId}")
    suspend fun getSurveysByUserId(@Path("userId") userId: Int): List<Survey>

    @GET("menu/distinct")
    suspend fun getDistinctMenus(): List<Menu>


}
