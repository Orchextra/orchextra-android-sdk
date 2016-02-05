package gigigo.com.orchextra.data.datasources.builders;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeaconRegion;

public class ApiBeaconBuilder {

    public static final int MAJOR = 30;
    public static final int MINOR = 11;
    public static final String UUID = "12345";
    public static final String CODE = "Code";
    public static final String ID = "001";
    public static final String NAME = "iBeaconName";
    public static final int STAY_TIME = 3000;
    public static final String TYPE = "beacon";
    public static final String BEACON_TAG_NAME = "iBeaconTag";
    public static final String CREATED_AT = "2015-10-21T11:46:13Z";
    public static final String UPDATED_AT = "2015-10-21T18:41:39Z";

    private int minor = MINOR;
    private int major = MAJOR;
    private String uuid = UUID;
    private boolean active = true;
    private String id = ID;
    private String code = CODE;
    private String name = NAME;
    private List<String> tags;
    private String type = TYPE;
    private String createdAt = CREATED_AT;
    private String updatedAt = UPDATED_AT;
    private boolean notifyOnExit = true;
    private boolean notifyOnEntry = true;
    private int stayTime = STAY_TIME;

    public static ApiBeaconBuilder Builder() {
        return new ApiBeaconBuilder();
    }

    public ApiBeaconRegion build() {
        ApiBeaconRegion apiBeaconRegion = new ApiBeaconRegion();
        apiBeaconRegion.setActive(true);
        apiBeaconRegion.setMajor(MAJOR);
        apiBeaconRegion.setMinor(MINOR);
        apiBeaconRegion.setUuid(UUID);

        apiBeaconRegion.setCode(CODE);
        apiBeaconRegion.setId(ID);
        apiBeaconRegion.setName(NAME);
        apiBeaconRegion.setNotifyOnEntry(true);
        apiBeaconRegion.setNotifyOnExit(true);
        apiBeaconRegion.setStayTime(STAY_TIME);

        List<String> tags = new ArrayList<>();
        tags.add(BEACON_TAG_NAME);
        apiBeaconRegion.setTags(tags);

        apiBeaconRegion.setType(TYPE);
        apiBeaconRegion.setCreatedAt(CREATED_AT);
        apiBeaconRegion.setUpdatedAt(UPDATED_AT);

        return apiBeaconRegion;
    }
}
