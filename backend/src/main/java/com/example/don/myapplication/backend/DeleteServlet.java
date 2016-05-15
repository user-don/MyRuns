package com.example.don.myapplication.backend;

import com.example.don.myapplication.backend.data.EEDataStore;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by McFarland on 5/13/16.
 */
public class DeleteServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /* request that the Datastore delete the entry with given ID */
        String id = req.getParameter("id");
        EEDataStore.deleteEntry(id);

        /* TODO -- send message to client to delete entry with given ID */

        /* redirect client to refreshed entry page */
        resp.sendRedirect("/viewEntries.do");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        doGet(req, resp);
    }
}