package car.franko.android.fer.hr.modules;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Franko on 17/06/24.
 */

public class ImageResponse implements Serializable {

    @SerializedName("url_location")
    String url = "https://www.google.com";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
