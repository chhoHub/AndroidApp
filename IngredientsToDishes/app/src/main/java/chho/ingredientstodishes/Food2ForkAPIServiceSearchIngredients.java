package chho.ingredientstodishes;

import chho.ingredientstodishes.data.IngredientData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gary on 2/29/2016.
 */
public interface Food2ForkAPIServiceSearchIngredients {
    @GET("api/search")
    Call<IngredientData> getSearchedRecpies(
            @Query("key") String api_key,
            @Query("q") String ingredients, //separated by commas
            @Query("sort") char style, //r for Rating or t for trend
            @Query("page") int pagenum
    );


}
