package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.Theme;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ThemeRealmMapper implements Mapper<Theme, ThemeRealm> {

  @Override public ThemeRealm modelToExternalClass(Theme theme) {
    ThemeRealm themeRealm = new ThemeRealm();
    themeRealm.setPrimaryColor(theme.getPrimaryColor());
    themeRealm.setSecondaryColor(theme.getSecondaryColor());
    return themeRealm;
  }

  @Override public Theme externalClassToModel(ThemeRealm themeRealm) {
    Theme theme = new Theme();

    if (themeRealm != null) {
      theme.setPrimaryColor(themeRealm.getPrimaryColor());
      theme.setSecondaryColor(themeRealm.getSecondaryColor());
    }

    return theme;
  }
}
