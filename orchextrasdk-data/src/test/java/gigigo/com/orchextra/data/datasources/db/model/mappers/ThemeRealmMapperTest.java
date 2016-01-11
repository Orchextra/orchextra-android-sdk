package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Theme;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.ThemeBuilder;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;

import static org.junit.Assert.assertEquals;

public class ThemeRealmMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
        Theme theme = ThemeBuilder.Builder().builder();

        ThemeRealmMapper mapper = new ThemeRealmMapper();
        ThemeRealm themeRealm = mapper.modelToData(theme);

        assertEquals(ThemeBuilder.PRIMARY_COLOR, themeRealm.getPrimaryColor());
        assertEquals(ThemeBuilder.SECONDARY_COLOR, themeRealm.getSecondaryColor());
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        ThemeRealm themeRealm = new ThemeRealm();
        themeRealm.setPrimaryColor(ThemeBuilder.PRIMARY_COLOR);
        themeRealm.setSecondaryColor(ThemeBuilder.SECONDARY_COLOR);

        ThemeRealmMapper mapper = new ThemeRealmMapper();
        Theme theme = mapper.dataToModel(themeRealm);

        assertEquals(ThemeBuilder.PRIMARY_COLOR, theme.getPrimaryColor());
        assertEquals(ThemeBuilder.SECONDARY_COLOR, theme.getSecondaryColor());
    }
}