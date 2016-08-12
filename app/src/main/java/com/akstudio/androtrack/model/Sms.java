package com.akstudio.androtrack.model;

import com.akstudio.androtrack.utils.Fields;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asifbakht on 7/18/2016.
 */
@DatabaseTable(tableName = Fields.TABLE_SMS)
public class Sms {

    public Sms() {

    }

    public Sms(String type, String body, String phoneNumber, String dateTime, String name, int objectId) {
        this.type = type;
        this.body = body;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.dateTime = dateTime;
        this.objectId = objectId;
        this.dirty = false;
    }

    @DatabaseField(columnName = Fields.TYPE, canBeNull = false)
    private String type;

    @DatabaseField(columnName = Fields.BODY, canBeNull = false)
    private String body;

    @DatabaseField(columnName = Fields.PHONE_NUMBER, canBeNull = false)
    private String phoneNumber;

    @DatabaseField(columnName = Fields.NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = Fields.DATE_TIME, canBeNull = false)
    private String dateTime;

    @DatabaseField(columnName = Fields.OBJECT_ID, generatedId = true)
    private int objectId;

    @DatabaseField(columnName = Fields.DIRTY, canBeNull = false)
    private Boolean dirty;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @JsonProperty()
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
