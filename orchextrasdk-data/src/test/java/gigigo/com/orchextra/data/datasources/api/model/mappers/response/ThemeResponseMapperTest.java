package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.vo.Theme;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiTheme;

import static org.junit.Assert.assertEquals;

public class ThemeResponseMapperTest {

    @Test
    public void testDataToModel() throws Exception {
        ApiTheme apiTheme = new ApiTheme();
        apiTheme.setPrimaryColor("#FFF");
        apiTheme.setSecondaryColor("#000");

        ThemeExternalClassToModelMapper mapper = new ThemeExternalClassToModelMapper();
        Theme theme = mapper.externalClassToModel(apiTheme);

        assertEquals("#FFF", theme.getPrimaryColor());
        assertEquals("#000", theme.getSecondaryColor());
    }
}