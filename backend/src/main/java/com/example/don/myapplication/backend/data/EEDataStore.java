package com.example.don.myapplication.backend.data;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.annotation.Index;
import com.sun.corba.se.spi.activation.Server;

/**
 * Created by McFarland on 5/13/16.
 */
public class EEDataStore {

    private static final Logger mLogger = Logger
            .getLogger(EEDataStore.class.getName());

    // make static so it's always available
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    private static Key getKey() {
        return KeyFactory.createKey(ServerEE.EE_PARENT_ENTITY_NAME,
                ServerEE.EE_PARENT_KEY_NAME);
    }

    public static boolean add(ServerEE entry) {

        Key parentKey = getKey();

        Entity entity = new Entity(entry.EE_ENTITY_NAME, entry.mID, parentKey);
        entity.setProperty(entry.FIELD_NAME_ID, entry.mID);
        entity.setProperty(entry.FIELD_NAME_INPUT_TYPE, entry.mInputType);
        entity.setProperty(entry.FIELD_NAME_ACTIVITY_TYPE, entry.mActivityType);
        entity.setProperty(entry.FIELD_NAME_DURATION, entry.mDuration);
        entity.setProperty(entry.FIELD_NAME_DISTANCE, entry.mDistance);
        entity.setProperty(entry.FIELD_NAME_AVG_SPEED, entry.mAvgSpeed);
        entity.setProperty(entry.FIELD_NAME_CALORIES, entry.mCalories);
        entity.setProperty(entry.FIELD_NAME_CLIMB, entry.mClimb);
        entity.setProperty(entry.FIELD_NAME_HEART_RATE, entry.mHeartRate);
        entity.setProperty(entry.FIELD_NAME_COMMENT, entry.mComment);

        mDatastore.put(entity);

        return true;
    }

    public static ServerEE getEEByID(Long ID, Transaction txn) {
        Entity result = null;
        try {
            result = mDatastore.get(KeyFactory.createKey(getKey(),
                    ServerEE.EE_ENTITY_NAME, ID));
        } catch (Exception ex) {

        }

        return getEEFromEntity(result);
    }

    public static ArrayList<ServerEE> getAllEntries() {
        ArrayList<ServerEE> entries = new ArrayList<ServerEE>();

        // create query with no filter to get all entries
        Query query = new Query(ServerEE.EE_ENTITY_NAME);
        query.setFilter(null);
        query.setAncestor(getKey());

        // add all entries to results list
        for (Entity entity : mDatastore.prepare(query).asIterable()) {
            ServerEE entry = getEEFromEntity(entity);
            if (entity != null) {
                entries.add(entry);
            }
        }

        return entries;
    }

    // returns true if entry was deleted, false otherwise
    public static boolean deleteEntry(String strID) {

        try {
            long ID = Long.parseLong(strID);

            Filter filter = new FilterPredicate(ServerEE.FIELD_NAME_ID,
                    FilterOperator.EQUAL, ID);

            Query query = new Query(ServerEE.EE_ENTITY_NAME);
            query.setFilter(filter);

            Entity result = mDatastore.prepare(query).asSingleEntity();
            if (result != null) {
                mDatastore.delete(result.getKey());
                return true;
            }

        } catch (NumberFormatException e) {
            mLogger.log(Level.INFO, "unable to parse string ID: " + strID);
        }

        return false;
    }

    public static void deleteAllEntries() {

        // create query with no filter to get all entries
        Query query = new Query(ServerEE.EE_ENTITY_NAME);
        query.setFilter(null);
        query.setAncestor(getKey());

        // delete all entries
        for (Entity entity : mDatastore.prepare(query).asIterable()) {
            if (entity != null) {
                mDatastore.delete(entity.getKey());
            }
        }

    }

    private static ServerEE getEEFromEntity(Entity entity) {
        if (entity == null) {
            return null;
        }

        return new ServerEE(
                (Long) entity.getProperty(ServerEE.FIELD_NAME_ID),
                (String) entity.getProperty(ServerEE.FIELD_NAME_INPUT_TYPE),
                (String) entity.getProperty(ServerEE.FIELD_NAME_ACTIVITY_TYPE),
                (String) entity.getProperty(ServerEE.FIELD_NAME_DATE_TIME),
                (String) entity.getProperty(ServerEE.FIELD_NAME_DURATION),
                (String) entity.getProperty(ServerEE.FIELD_NAME_DISTANCE),
                (String) entity.getProperty(ServerEE.FIELD_NAME_AVG_SPEED),
                (String) entity.getProperty(ServerEE.FIELD_NAME_CALORIES),
                (String) entity.getProperty(ServerEE.FIELD_NAME_CLIMB),
                (String) entity.getProperty(ServerEE.FIELD_NAME_HEART_RATE),
                (String) entity.getProperty(ServerEE.FIELD_NAME_COMMENT));
    }
}
