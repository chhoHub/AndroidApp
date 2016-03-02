package chho.ingredientstodishes;

import chho.ingredientstodishes.data.RecipeIngredients;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gary on 3/1/2016.
 */
public interface Food2ForkAPIServiceRecipeIngreds {

    @GET("api/get")
    Call<RecipeIngredients> getRecipeIngreds(
            @Query("key") String api_key,
            @Query("rId") String recipe_id
    );
}
