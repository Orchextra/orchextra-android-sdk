package gigigo.com.orchextra.data.datasources.builders;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

public class ApiSdkAuthDataBuilder {

    public static final String VALUE = "Test Value";
    public static final int EXPIRES_IN = 3000;
    public static final String PROJECT_ID = "1234";

    private String value = VALUE;

    private Integer expiresIn = EXPIRES_IN;

    private String projectId = PROJECT_ID;

    public static ApiSdkAuthDataBuilder Builder() {
        return new ApiSdkAuthDataBuilder();
    }

    public ApiSdkAuthData build() {
        ApiSdkAuthData apiSdkAuthData = new ApiSdkAuthData();

        apiSdkAuthData.setValue(value);
        apiSdkAuthData.setProjectId(projectId);
        apiSdkAuthData.setExpiresIn(expiresIn);

        return apiSdkAuthData;
    }
}

