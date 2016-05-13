package com.example.don.myapplication.backend.data;

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

/**
 * Created by McFarland on 5/13/16.
 */
public class EEDataStore {

    private static final Logger mLogger = Logger
            .getLogger(EEDataStore.class.getName());

    // make static so it's always available
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();
}
