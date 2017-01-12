package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by nubor on 11/01/2017.
 */
public class ApiEddyStoneRequest {

    @Expose
    @SerializedName("temperature")
    private double temperature;

    @Expose
    @SerializedName("battery")
    private long battery;

    @Expose
    @SerializedName("uptime")
    private long uptime;

    @Expose
    @SerializedName("pducount")
    private long pducount;

    @Expose
    @SerializedName("tlmversion")
    private long tlmversion;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getBattery() {
        return battery;
    }

    public void setBattery(long battery) {
        this.battery = battery;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public long getPducount() {
        return pducount;
    }

    public void setPducount(long pducount) {
        this.pducount = pducount;
    }

    public long getTlmversion() {
        return tlmversion;
    }

    public void setTlmversion(long tlmversion) {
        this.tlmversion = tlmversion;
    }
}
