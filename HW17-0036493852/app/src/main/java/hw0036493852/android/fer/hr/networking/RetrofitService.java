package hw0036493852.android.fer.hr.networking;

import hw0036493852.android.fer.hr.modules.FormResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Simple service to use Retrofit library with {@link FormResponse}
 */

public interface RetrofitService {

    /**
     * Returns a {@link Call} object able to return a {@link FormResponse} from a given URL
     *
     * @param relativePath Relative path to the JSON document
     * @return Call<FormResponse> object
     */
    @GET("/{relative_path}")
    Call<FormResponse> getForm(@Path("relative_path") String relativePath);

}
