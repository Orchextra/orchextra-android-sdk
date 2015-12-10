package gigigo.com.orchextra.data.datasources.datasources.api.service;

import com.gigigo.ggglib.network.converters.ErrorConverter;
import com.gigigo.ggglib.network.converters.RetrofitErrorConverter;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import retrofit.Retrofit;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 1/12/15.
 */
public class DefatultErrorConverterImpl<ErrorResponse> implements ErrorConverter<ErrorResponse, ResponseBody> {

  RetrofitErrorConverter retrofitErrorConverter;

  public DefatultErrorConverterImpl(Retrofit retrofit, Class<ErrorResponse> errorResponse) {
    this.retrofitErrorConverter = new RetrofitErrorConverter( retrofit, errorResponse);
  }

  @Override public ErrorResponse convert(ResponseBody value) throws IOException {
    return (ErrorResponse) retrofitErrorConverter.convert(value);
  }
}
