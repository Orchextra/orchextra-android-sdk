package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.Theme;

import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;

public class ConfigThemeUpdater {

    private final RealmMapper<Theme, ThemeRealm> themeRealmMapper;

    public ConfigThemeUpdater(RealmMapper<Theme, ThemeRealm> themeRealmMapper) {
        this.themeRealmMapper = themeRealmMapper;
    }

    public boolean saveTheme(Realm realm, Theme theme) {
        boolean hasChangedTheme = false;

        if (theme != null){
            ThemeRealm themeRealm = themeRealmMapper.modelToData(theme);
            ThemeRealm oldTheme = realm.where(ThemeRealm.class).findFirst();
            hasChangedTheme = !checkThemeAreEquals(themeRealm, oldTheme);
            if (hasChangedTheme) {
                realm.copyToRealm(themeRealm);
            }
        } else {
            realm.clear(ThemeRealm.class);
        }
        return hasChangedTheme;
    }

    private boolean checkThemeAreEquals(ThemeRealm themeRealm, ThemeRealm oldTheme) {
        if (oldTheme != null) {
            return themeRealm.getPrimaryColor().equals(oldTheme.getPrimaryColor()) &&
                    themeRealm.getSecondaryColor().equals(oldTheme.getSecondaryColor());
        } else {
            return false;
        }
    }
}
