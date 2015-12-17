package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.Theme;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiTheme;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class ThemeResponseMapper implements ResponseMapper<Theme, ApiTheme>{

  @Override public Theme dataToModel(ApiTheme apiTheme) {
    Theme theme = new Theme();

    theme.setPrimaryColor(apiTheme.getPrimaryColor());
    theme.setSecondaryColor(apiTheme.getSecondaryColor());

    return theme;
  }

}
