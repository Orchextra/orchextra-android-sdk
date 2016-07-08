package gigigo.com.orchextra.data.datasources.builders;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiRegion;

public class ApiRegionBuilder {

    public static final int MAJOR = 30;
    public static final int MINOR = 11;
    public static final String UUID = "12345";
    public static final String CODE = "Code";
    public static final String BEACON_TAG_NAME = "iBeaconTag";
    public static final String CREATED_AT = "2015-10-21T11:46:13Z";
    public static final String UPDATED_AT = "2015-10-21T18:41:39Z";

    //todo clear never used
    private int minor = MINOR;
    private int major = MAJOR;
    private String uuid = UUID;
    private boolean active = true;
    private String code = CODE;
    @Deprecated
    private List<String> tags;
    private String createdAt = CREATED_AT;
    private String updatedAt = UPDATED_AT;
    private boolean notifyOnExit = true;
    private boolean notifyOnEntry = true;

    public static ApiRegionBuilder Builder() {
        return new ApiRegionBuilder();
    }

    public ApiRegion build() {
        ApiRegion apiRegion = new ApiRegion();
        apiRegion.setActive(true);
        apiRegion.setMajor(MAJOR);
        apiRegion.setMinor(MINOR);
        apiRegion.setUuid(UUID);

        apiRegion.setCode(CODE);
        apiRegion.setNotifyOnEntry(true);
        apiRegion.setNotifyOnExit(true);

        List<String> tags = new ArrayList<>();
        tags.add(BEACON_TAG_NAME);
        apiRegion.setTags(tags);

        apiRegion.setCreatedAt(CREATED_AT);
        apiRegion.setUpdatedAt(UPDATED_AT);

        return apiRegion;
    }
}
