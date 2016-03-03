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

package gigigo.com.orchextra.data.datasources.api.service;

import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.converters.RetrofitErrorConverter;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import retrofit.Retrofit;


public class DefatultErrorConverterImpl<ErrorResponse>
    implements ErrorConverter<ErrorResponse, ResponseBody> {

  RetrofitErrorConverter retrofitErrorConverter;

  public DefatultErrorConverterImpl(Retrofit retrofit, Class<ErrorResponse> errorResponse) {
    this.retrofitErrorConverter = new RetrofitErrorConverter(retrofit, errorResponse);
  }

  @Override public ErrorResponse convert(ResponseBody value) throws IOException {
    return (ErrorResponse) retrofitErrorConverter.convert(value);
  }
}
