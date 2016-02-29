package chho.ingredientstodishes;

import chho.ingredientstodishes.data.RecipeData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Gary on 2/29/2016.
 */
public interface Food2ForkAPIService {

    @GET("services/rest")
    Call<RecipeData> getSearchedRecpies (
      @Query("")
    );


}
