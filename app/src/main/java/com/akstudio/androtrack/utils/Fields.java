package com.akstudio.androtrack.utils;

/**
 * Created by Asifbakht on 7/20/2016.
 */

public class Fields {

    public static final String BASE_API_URL = "http://desksort.ae:8001/DesksortDev/";
    public static final String MESSAGE = "message";
    public static final String CODE = "code";
    public static final String DATA = "data";
    public static final String SUCCESS = "success";
    public static final String GET = "/get";

    public static final String DATA_UPLOAD = "com.akstudio.track.PAY_LOAD";
    public static final String LOCATION_TRACK = "com.akstudio.track.LOCATION_TRACK";
    public static final String GARBAGE_TRASH = "com.akstudio.track.GARBAGE_TRASH";


    private final String DATABASE_URL = "jdbc:h2:mem:account";
    //private final ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);


    public static final String MSG_ID_TAG = "_id";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String ADDRESS = "address";
    public static final String SENT = "Sent";
    public static final String RECEIVE = "Received";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String OBJECT_ID = "object_id";
    public static final String DEVICE_ID = "device_id";
    public static final String DATE = "date";
    public static final String PROTOCOL = "protocol";
    public static final String DATE_TIME = "date_time";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String URL_TITLE = "url_title";
    public static final String URL_LINK = "url_link";
    public static final String DIRTY = "dirty";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String DURATION = "duration";
    public static final String API_URL = "api";

    public static final String TABLE_USER = "user";
    public static final String TABLE_SMS = "sms_logs";
    public static final String TABLE_CALL = "call_logs";
    public static final String TABLE_LOCATION = "location_logs";


    public static final String LAUNCH_FIRST = "first_launch";
    public static final String LAUNCH_COMPLETE = "launch_complete";
    public static final String IS_LOGIN = "is_login";
    public static final String SESSION = "session";
    public static final String TRACK_SMS = "track_sms";
    public static final String TRACK_CALL = "track_call";
    public static final String TRACK_LOCATION = "track_location";
    public static final String REMEMBER_USER = "remember_user";
    public static final String SYNC_DATE = "sync_date";


    public static final String OUTGOING = "OUTGOING";
    public static final String INCOMING = "INCOMING";
    public static final String MISSED = "MISSED";
    public static final String CANCELLED = "CANCELLED";


    public static final String ERROR_EMAIL_PASSWORD = "Email or password missing";
    public static final String ERROR_PASSWORD_MISMATCH = "Password not matching";
    public static final String ERROR_EMAIL = "Enter correct email address";
    public static final String ERROR_REGISTRATION = "Unable to register please try later";
    public static final String ERROR_MISSING_INFORMATION = "Some information is missing";
}
