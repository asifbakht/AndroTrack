package com.akstudio.androtrack.model;

import com.akstudio.androtrack.utils.Fields;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asifbakht on 7/18/2016.
 */
@DatabaseTable(tableName = Fields.TABLE_LOCATION)
public class Location {

    @DatabaseField(columnName = Fields.LATITUDE, canBeNull = false)
    private String latitude;

    @DatabaseField(columnName = Fields.LONGITUDE, canBeNull = false)
    private String longitude;

    @DatabaseField(columnName = Fields.DATE_TIME, canBeNull = false)
    private String dateTime;

    @DatabaseField(columnName = Fields.DEVICE_ID, canBeNull = false)
    private String deviceId;

    @DatabaseField(columnName = Fields.OBJECT_ID, generatedId = true)
    private int objectId;

    @DatabaseField(columnName = Fields.DIRTY, canBeNull = false)
    private Boolean dirty;


    @JsonProperty(Fields.LATITUDE)
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty(Fields.LATITUDE)
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty(Fields.LONGITUDE)
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty(Fields.LONGITUDE)
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty(Fields.DATE_TIME)
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty(Fields.DATE_TIME)
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty(Fields.DEVICE_ID)
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty(Fields.DEVICE_ID)
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty(Fields.OBJECT_ID)
    public int getObjectId() {
        return objectId;
    }

    @JsonProperty(Fields.OBJECT_ID)
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @JsonProperty(Fields.DIRTY)
    public Boolean getDirty() {
        return dirty;
    }

    @JsonProperty(Fields.DIRTY)
    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }
}
