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

    public Theme saveTheme(Realm realm, Theme theme) {
        boolean hasChangedTheme = false;

        if (theme != null){
            hasChangedTheme = checkIfChangedTheme(realm, theme);
        } else {
            realm.clear(ThemeRealm.class);
        }
        if (hasChangedTheme) {
            return theme;
        } else {
            return null;
        }
    }

    private boolean checkIfChangedTheme(Realm realm, Theme theme) {
        boolean hasChangedTheme;ThemeRealm themeRealm = themeRealmMapper.modelToExternalClass(theme);

        ThemeRealm oldTheme = realm.where(ThemeRealm.class).findFirst();

        hasChangedTheme = !checkThemeAreEquals(themeRealm, oldTheme);

        if (hasChangedTheme) {
            realm.clear(ThemeRealm.class);
            realm.copyToRealm(themeRealm);
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
