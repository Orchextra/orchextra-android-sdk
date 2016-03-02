package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.vo.Theme;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiTheme;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class ThemeExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiTheme, Theme> {

  @Override public Theme externalClassToModel(ApiTheme apiTheme) {
    Theme theme = new Theme();

    theme.setPrimaryColor(apiTheme.getPrimaryColor());
    theme.setSecondaryColor(apiTheme.getSecondaryColor());

    return theme;
  }
}
