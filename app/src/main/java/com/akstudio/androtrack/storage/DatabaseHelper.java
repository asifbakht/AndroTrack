package com.akstudio.androtrack.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.akstudio.androtrack.model.Call;
import com.akstudio.androtrack.model.Location;
import com.akstudio.androtrack.model.Sms;
import com.akstudio.androtrack.utils.Fields;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.h2.command.dml.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "studentdir.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Call, Integer> callDao;
    private Dao<Sms, Integer> smsDAO;
    private Dao<Location, Integer> locationDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DatabaseHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseHelper(ctx);
        }
        return instance;
    }


    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Call.class);
            TableUtils.createTable(connectionSource, Sms.class);
            TableUtils.createTable(connectionSource, Location.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Call.class, true);
            TableUtils.dropTable(connectionSource, Sms.class, true);
            TableUtils.dropTable(connectionSource, Location.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<Call, Integer> getCallDao() throws SQLException {
        if (callDao == null) {
            callDao = getDao(Call.class);
        }
        return callDao;
    }

    public Dao<Sms, Integer> getSmsDAO() throws SQLException {
        if (smsDAO == null) {
            smsDAO = getDao(Sms.class);
        }
        return smsDAO;
    }

    public Dao<Location, Integer> getLocationDao() throws SQLException {
        if (locationDao == null) {
            locationDao = getDao(Location.class);
        }
        return locationDao;
    }


    public List<Sms> getSmsList() {
        QueryBuilder<Sms, Integer> smsBuilder = null;
        List<Sms> smsList = new ArrayList<>();
        try {
            smsBuilder = this.getSmsDAO().queryBuilder();
            smsBuilder.where().eq(Fields.DIRTY, false);
            smsList = this.getSmsDAO().query(smsBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return smsList;
    }

    public void getSmsListDirty(List<Sms> smsList) {
        UpdateBuilder<Sms, Integer> smsBuilder = null;
        try {
            smsBuilder = this.getSmsDAO().updateBuilder();
            smsBuilder.where().eq(Fields.DIRTY, false);
            smsBuilder.updateColumnValue(Fields.DIRTY, true);
            smsBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Call> getCallList() {
        QueryBuilder<com.akstudio.androtrack.model.Call, Integer> callBuilder = null;
        List<Call> callList = new ArrayList<>();
        try {
            callBuilder = this.getCallDao().queryBuilder();
            callBuilder.where().eq(Fields.DIRTY, false);
            callList = this.getCallDao().query(callBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return callList;
    }


    public List<Location> getLocationList() {
        QueryBuilder<Location, Integer> locationBuilder = null;
        List<Location> locationList = new ArrayList<>();
        try {
            locationBuilder = this.getLocationDao().queryBuilder();
            locationBuilder.where().eq(Fields.DIRTY, false);
            locationList = this.getLocationDao().query(locationBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }


    public void deleteDirty() {
        DeleteBuilder<Call, Integer> callBuilder = null;
        DeleteBuilder<Location, Integer> locationBuilder = null;
        DeleteBuilder<Sms, Integer> smsBuilder = null;
        try {
            callBuilder = this.getCallDao().deleteBuilder();
            callBuilder.where().eq(Fields.DIRTY, true);
            callBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            locationBuilder = this.getLocationDao().deleteBuilder();
            locationBuilder.where().eq(Fields.DIRTY, true);
            locationBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            smsBuilder = this.getSmsDAO().deleteBuilder();
            smsBuilder.where().eq(Fields.DIRTY, true);
            smsBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}