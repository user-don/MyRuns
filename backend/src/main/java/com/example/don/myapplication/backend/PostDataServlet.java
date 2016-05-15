package com.example.don.myapplication.backend;

import com.example.don.myapplication.backend.data.EEDataStore;
import com.example.don.myapplication.backend.data.ServerEE;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Gson gson = new Gson();

        /* clear out current DataStore */
        EEDataStore.deleteAllEntries();

        /* parse through resp for json ServeEE objects */
        String data_string = req.getParameter("DATA");  // defined in start fragment
        if (data_string != null) {
            ArrayList<String> entries = gson.fromJson(data_string, ArrayList.class);

            for (String entry : entries) {
                EEDataStore.add(gson.fromJson(entry, ServerEE.class));
            }
        }

        /* redirect client to refreshed entry page */
        resp.sendRedirect("/viewEntries.do");
    }
}