package com.akstudio.androtrack.model;

import com.akstudio.androtrack.utils.Fields;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asifbakht on 7/18/2016.
 */
@DatabaseTable(tableName = Fields.TABLE_CALL)
public class Call {

    public Call() {
	// THIS IS BUG FIX FOR AS13
	// THIS IS ALSO BUG FIX FOR AS14

    }


    public Call(String type, String duration, String phoneNumber, String dateTime, String name, int objectId) {
        this.type = type;
        this.duration = duration;
        this.phoneNumber = phoneNumber;
        this.dateTime = dateTime;
        this.name = name;
        this.objectId = objectId;
    }

    @DatabaseField(columnName = Fields.OBJECT_ID, generatedId = true)
    private int objectId;

    @DatabaseField(columnName = Fields.TYPE, canBeNull = false)
    private String type;


    @DatabaseField(columnName = Fields.PHONE_NUMBER, canBeNull = false)
    private String phoneNumber;

    @DatabaseField(columnName = Fields.NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = Fields.DURATION, canBeNull = false)
    private String duration;

    @DatabaseField(columnName = Fields.DATE_TIME, canBeNull = false)
    private String dateTime;


    @DatabaseField(columnName = Fields.DIRTY, canBeNull = false)
    private Boolean dirty;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(Fields.PHONE_NUMBER)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty(Fields.PHONE_NUMBER)
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty(Fields.DATE_TIME)
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty(Fields.DATE_TIME)
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    @JsonProperty(Fields.NAME)
    public String getName() {
        return name;
    }

    @JsonProperty(Fields.NAME)
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(Fields.DURATION)
    public String getDuration() {
        return duration;
    }

    @JsonProperty(Fields.DURATION)
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
