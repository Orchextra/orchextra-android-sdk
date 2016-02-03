package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Theme;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ThemeRealmMapper implements RealmMapper<Theme, ThemeRealm> {

  @Override public ThemeRealm modelToData(Theme theme) {
    ThemeRealm themeRealm = new ThemeRealm();
    themeRealm.setPrimaryColor(theme.getPrimaryColor());
    themeRealm.setSecondaryColor(theme.getSecondaryColor());
    return themeRealm;
  }

  @Override public Theme dataToModel(ThemeRealm themeRealm) {
    Theme theme = new Theme();

    if (themeRealm != null) {
      theme.setPrimaryColor(themeRealm.getPrimaryColor());
      theme.setSecondaryColor(themeRealm.getSecondaryColor());
    }

    return theme;
  }
}
