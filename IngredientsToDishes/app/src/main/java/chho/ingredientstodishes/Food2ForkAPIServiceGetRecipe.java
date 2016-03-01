package chho.ingredientstodishes;

import chho.ingredientstodishes.data.RecipeData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gary on 2/29/2016.
 */
public interface Food2ForkAPIServiceGetRecipe {
    @GET("api/get")
    Call<RecipeData> getSearchedRecpies (
            @Query("key") String api_key//,
            //@Query("rID") String recipe_id
            );


}
