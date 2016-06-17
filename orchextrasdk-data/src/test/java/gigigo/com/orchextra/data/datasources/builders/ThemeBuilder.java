package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.model.vo.Theme;
@Deprecated
public class ThemeBuilder {

    public static final String PRIMARY_COLOR = "#FFF";
    public static final String SECONDARY_COLOR = "#000";

    private String primaryColor = PRIMARY_COLOR;
    private String secondaryColor = SECONDARY_COLOR;
    @Deprecated
    public static ThemeBuilder Builder() {
        return new ThemeBuilder();
    }
    @Deprecated
    public Theme builder() {
        Theme theme = new Theme();
        theme.setPrimaryColor(primaryColor);
        theme.setSecondaryColor(secondaryColor);

        return theme;
    }
}
