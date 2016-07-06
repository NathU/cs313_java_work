/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs313.jspforum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;
import org.json.simple.parser.*;


/**
 *
 * @author nathanulmer
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected boolean processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            return false;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rd = "/invalidLogin.jsp";
        String err = null;
        if ( (request.getParameter("username")).equals("") || (request.getParameter("username").equals("")) ) {
            err = "Missing username or password!";
        } else {
            JSONParser parser = new JSONParser();
            boolean userFound = false;
            String line;
            String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR"); 
            String filename = (dataDirectory.equals("") || dataDirectory == null)? "/accounts.txt":dataDirectory + "accounts.txt" ;
            try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
                while((!userFound) && ((line = br.readLine()) != null) ) {
                    try {
                        Object obj = parser.parse(line);
                        JSONObject userInfo = (JSONObject)obj;
                        userFound = ( (userInfo.get("username")).equals(request.getParameter("username")) &&
                                (userInfo.get("password")).equals(request.getParameter("password")) );
                    } catch(ParseException pe) {
                        //bad file format!!!
                        pe.printStackTrace();
                    }
                }
            }
            if (!userFound) {
                err = "Incorrect username or password!";
            } else {
                rd = "/newPost.jsp";
                request.getSession().setAttribute("username", request.getParameter("username"));
            }
        }
        request.setAttribute("error", err);
        request.getRequestDispatcher(rd).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
