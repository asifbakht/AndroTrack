package com.akstudio.androtrack.model;

import com.akstudio.androtrack.utils.Fields;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Asifbakht on 7/19/2016.
 */
@DatabaseTable(tableName = Fields.TABLE_USER)
public class User {

    @DatabaseField(columnName = Fields.NAME, canBeNull = false)
    private String name;

    @DatabaseField(columnName = Fields.EMAIL, canBeNull = false)
    private String email;

    @DatabaseField(columnName = Fields.PASSWORD, canBeNull = false)
    private String password;

    @DatabaseField(columnName = Fields.OBJECT_ID, generatedId = true)
    private int objectId;


    @JsonProperty(Fields.NAME)
    public String getName() {
        return name;
    }

    @JsonProperty(Fields.NAME)
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(Fields.EMAIL)
    public String getEmail() {
        return email;
    }

    @JsonProperty(Fields.EMAIL)
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(Fields.PASSWORD)
    public String getPassword() {
        return password;
    }

    @JsonProperty(Fields.PASSWORD)
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty(Fields.OBJECT_ID)
    public int getObjectId() {
        return objectId;
    }

    @JsonProperty(Fields.OBJECT_ID)
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
