package com.example.don.myapplication.backend;

import com.example.don.myapplication.backend.data.EEDataStore;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.don.myapplication.backend.OfyService.ofy;

/**
 * Servelet for deleting an entry and transmitting the delete message to the app using
 * {@link MessagingEndpoint}
 *
 * Created by McFarland on 5/13/16.
 */
public class DeleteServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /* request that the Datastore delete the entry with given ID */
        String id = req.getParameter("id");
        if (id != null) {
            EEDataStore.deleteEntry(id);
        }

        MessagingEndpoint msg = new MessagingEndpoint();
        msg.sendMessage(id);

        /* redirect client to refreshed entry page */
        resp.sendRedirect("/viewEntries.do");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        doGet(req, resp);
    }
}