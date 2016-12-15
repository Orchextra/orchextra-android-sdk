/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.api.interceptors;

import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Headers implements Interceptor {

  private final String X_APP_SDK = "X-orx-version";
  private final String ACCEPT_LANGUAGE = "Accept-Language";
  private final String CONTENT_TYPE = "Content-Type";
  private final String CONTENT_TYPE_JSON = "application/json";

  private final String xAppSdk;
  private final String acceptLanguage;
  private final String contentType;
  private final Session session;

  public Headers(String xAppSdk, String acceptLanguage, Session session) {
    this.xAppSdk = xAppSdk;
    this.acceptLanguage = acceptLanguage;
    this.contentType = CONTENT_TYPE_JSON;
    this.session = session;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request original = chain.request();

    // Add Headers to request and build it again
    Request.Builder builder = original.newBuilder()
        .header(X_APP_SDK, xAppSdk)
        .header(ACCEPT_LANGUAGE, acceptLanguage)
        .header(CONTENT_TYPE, contentType);

    if (session.getAuthToken() != null) {
      builder.header("Authorization", session.getAuthToken());
    }

    Request request = builder.method(original.method(), original.body()).build();

    return chain.proceed(request);
  }
}
