package com.akstudio.androtrack.model;

import com.akstudio.androtrack.utils.Fields;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class CustomResponse {

    private String message;
    private Object data;
    private Boolean success;
    private Integer code;
	// THIS IS BUG FIX FOR AS14

    @JsonProperty(Fields.MESSAGE)
    public String getMessage() {
        return message;
    }

    @JsonProperty(Fields.MESSAGE)
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty(Fields.DATA)
    public Object getData() {
        return data;
    }

    @JsonProperty(Fields.DATA)
    public void setData(Object data) {
        this.data = data;
    }

    @JsonProperty(Fields.SUCCESS)
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty(Fields.SUCCESS)
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty(Fields.CODE)
    public Integer getCode() {
        return code;
    }

    @JsonProperty(Fields.CODE)
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CustomResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                ", code=" + code +
                '}';
    }
}

