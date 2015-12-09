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

  private final String USER_AGENT_HEADER_NAME = "User-Agent";

  private final String userAgent;

  public Headers(String userAgent) {
    this.userAgent = userAgent;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();

    // Add Headers to request and build it again
    Request request = original.newBuilder()
        .header(USER_AGENT_HEADER_NAME, userAgent)
        .method(original.method(), original.body())
        .build();

    return chain.proceed(request);
  }
}
