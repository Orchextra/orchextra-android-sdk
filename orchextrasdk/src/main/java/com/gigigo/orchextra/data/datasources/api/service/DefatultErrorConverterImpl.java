package com.gigigo.orchextra.data.datasources.api.service;


import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.converters.RetrofitErrorConverter;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import retrofit.Retrofit;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 1/12/15.
 */
public class DefatultErrorConverterImpl implements
    ErrorConverter<ApiGenericResponse, ResponseBody> {

  RetrofitErrorConverter retrofitErrorConverter;

  public DefatultErrorConverterImpl(Retrofit retrofit) {
    this.retrofitErrorConverter = new RetrofitErrorConverter( retrofit, ApiGenericResponse.class );
  }

  @Override public ApiGenericResponse convert(ResponseBody value) throws IOException {
    return (ApiGenericResponse)retrofitErrorConverter.convert(value);
  }
}
