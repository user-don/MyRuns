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

        /* TODO -- send message to client to delete entry with given ID */
        // instantiate Message with List<String> of device IDs
        // message.addData("message", "delete:" + id)
        // sender.send()
        // response result = sender.send(message, MAX_RETRY)
        // then delete from the datastore

        MessagingEndpoint msg = new MessagingEndpoint();
        msg.sendMessage(id);

//        final String API_KEY = System.getProperty("gcm.api.key");
//        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(10).list();
//        Message message = new Message(records);
//
//        Sender sender = new Sender(API_KEY);
//        Message msg = new Message.Builder().addData("message", message).build();
//
//        Message message = new Message(records);
//        Sender sender = new Sender(API_KEY);


        /* redirect client to refreshed entry page */
        resp.sendRedirect("/viewEntries.do");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        doGet(req, resp);
    }
}