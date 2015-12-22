package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.entities.Theme;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiTheme;

import static org.junit.Assert.assertEquals;

public class ThemeResponseMapperTest {

    @Test
    public void testDataToModel() throws Exception {
        ApiTheme apiTheme = new ApiTheme();
        apiTheme.setPrimaryColor("#FFF");
        apiTheme.setSecondaryColor("#000");

        ThemeResponseMapper mapper = new ThemeResponseMapper();
        Theme theme = mapper.dataToModel(apiTheme);

        assertEquals("#FFF", theme.getPrimaryColor());
        assertEquals("#000", theme.getSecondaryColor());
    }
}