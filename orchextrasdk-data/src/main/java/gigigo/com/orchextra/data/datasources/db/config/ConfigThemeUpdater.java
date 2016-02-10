package gigigo.com.orchextra.data.datasources.db.config;


import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.Theme;

import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import io.realm.Realm;

public class ConfigThemeUpdater {

    private final Mapper<Theme, ThemeRealm> themeRealmMapper;

    public ConfigThemeUpdater(Mapper<Theme, ThemeRealm> themeRealmMapper) {
        this.themeRealmMapper = themeRealmMapper;
    }

    public boolean saveTheme(Realm realm, Theme theme) {
        boolean hasChangedTheme = false;

        if (theme != null){
            ThemeRealm themeRealm = themeRealmMapper.modelToExternalClass(theme);
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
