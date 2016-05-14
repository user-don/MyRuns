package com.example.don.myapplication.backend;

import com.example.don.myapplication.backend.data.EEDataStore;
import com.example.don.myapplication.backend.data.ServerEE;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

/**
 * Created by McFarland on 5/13/16.
 */
public class ViewEntriesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        /* get all entries from datastore -> add them to the result_jsp page */

        ArrayList<ServerEE> result = EEDataStore.getAllEntries();
        req.setAttribute("result", result);
        getServletContext().getRequestDispatcher("/query_result.jsp").forward(req, resp);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}