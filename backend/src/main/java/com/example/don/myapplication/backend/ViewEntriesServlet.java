package com.example.don.myapplication.backend;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by McFarland on 5/13/16.
 */
public class ViewEntriesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /* get all entries from datastore -> add them to the result_jsp page */


        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        doGet(req, resp);

        // I think we just execute a doGet() b/c we aren't support Post?

//        String name = req.getParameter("name");
//        resp.setContentType("text/plain");
//        if(name == null) {
//            resp.getWriter().println("Please enter a name");
//        }
//        resp.getWriter().println("Hello " + name);
    }

}
