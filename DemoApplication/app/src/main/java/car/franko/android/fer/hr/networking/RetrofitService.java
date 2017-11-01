package car.franko.android.fer.hr.networking;

import android.media.Image;

import car.franko.android.fer.hr.modules.ImageResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Franko on 17/06/24.
 */

public interface RetrofitService {

    @GET("/ba3s/1498244613165.txt")
    Call<ImageResponse> getImage();

}
