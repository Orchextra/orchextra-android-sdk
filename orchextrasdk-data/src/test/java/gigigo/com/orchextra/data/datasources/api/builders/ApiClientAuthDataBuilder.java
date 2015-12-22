package gigigo.com.orchextra.data.datasources.api.builders;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiClientAuthData;

public class ApiClientAuthDataBuilder {

    public static final String VALUE = "Test Value";
    public static final int EXPIRES_IN = 3000;
    public static final String EXPIRES_AT ="2013-09-29T18:46:19Z";
    public static final String PROJECT_ID = "1234";
    public static final String USER_ID = "Admin";


    private String value = VALUE;

    private Integer expiresIn = EXPIRES_IN;

    private String expiresAt = EXPIRES_AT;

    private String projectId = PROJECT_ID;

    private String userId = USER_ID;

    public static ApiClientAuthDataBuilder Builder() {
        return new ApiClientAuthDataBuilder();
    }

    public ApiClientAuthDataBuilder setDate(String date) {
        expiresAt = date;
        return this;
    }

    public ApiClientAuthData build() {
        ApiClientAuthData apiClientAuthData = new ApiClientAuthData();

        apiClientAuthData.setValue(value);
        apiClientAuthData.setProjectId(projectId);
        apiClientAuthData.setExpiresIn(expiresIn);
        apiClientAuthData.setUserId(userId);

        return apiClientAuthData;
    }
}
