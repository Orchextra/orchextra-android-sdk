package gigigo.com.orchextra.data.datasources.builders;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeacon;

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

    public ApiBeacon build() {
        ApiBeacon apiBeacon = new ApiBeacon();
        apiBeacon.setActive(true);
        apiBeacon.setMajor(MAJOR);
        apiBeacon.setMinor(MINOR);
        apiBeacon.setUuid(UUID);

        apiBeacon.setCode(CODE);
        apiBeacon.setId(ID);
        apiBeacon.setName(NAME);
        apiBeacon.setNotifyOnEntry(true);
        apiBeacon.setNotifyOnExit(true);
        apiBeacon.setStayTime(STAY_TIME);

        List<String> tags = new ArrayList<>();
        tags.add(BEACON_TAG_NAME);
        apiBeacon.setTags(tags);

        apiBeacon.setType(TYPE);
        apiBeacon.setCreatedAt(CREATED_AT);
        apiBeacon.setUpdatedAt(UPDATED_AT);

        return apiBeacon;
    }
}
