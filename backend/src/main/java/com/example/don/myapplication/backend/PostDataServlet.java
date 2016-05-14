package com.example.don.myapplication.backend;

import com.example.don.myapplication.backend.data.EEDataStore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by McFarland on 5/14/16.
 */
public class PostDataServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        /* shouldn't handle get requests */
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /* clear out current DataStore */
        EEDataStore.deleteAllEntries();

        /* TODO -- parse through JSON data and update data store */

        /* redirect client to refreshed entry page */
        resp.sendRedirect("/viewEntries.do");
    }
}