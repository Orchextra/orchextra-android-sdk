package gigigo.com.orchextra.data.datasources.api.interceptors;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/11/15.
 */
public class Headers implements Interceptor {

    private final String X_APP_SDK = "X-app-sdk";
    private final String ACCEPT_LANGUAGE = "Accept-Language";
    private final String CONTENT_TYPE = "Content-Type";
    private final String CONTENT_TYPE_JSON = "application/json";

    private final String xAppSdk;
    private final String acceptLanguage;
    private final String contentType;

    public Headers(String xAppSdk, String acceptLanguage) {
        this.xAppSdk = xAppSdk;
        this.acceptLanguage = acceptLanguage;
        this.contentType = CONTENT_TYPE_JSON;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Add Headers to request and build it again
        Request request = original.newBuilder()
                .header(X_APP_SDK, xAppSdk)
                .header(ACCEPT_LANGUAGE, acceptLanguage)
                .header(CONTENT_TYPE, contentType)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
