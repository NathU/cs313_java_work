/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs313.jspforum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author nathanulmer
 */
@WebServlet(name = "loadposts", urlPatterns = {"/loadposts"})
public class loadposts extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loadposts</title>");            
            out.println("</head>");
            out.println("<body>");
            
            // read user posts from file...
            JSONParser parser = new JSONParser();
            Stack post_list = new Stack();
            String line;
            String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR"); 
            String filename = (dataDirectory.equals("") || dataDirectory == null)? "/user_posts.txt":dataDirectory + "user_posts.txt" ;
            try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
                
                post_list.push("</table>");
                while( (line = br.readLine()) != null ) {
                    try {
                        Object obj = parser.parse(line);
                        JSONObject userPost = (JSONObject)obj;
                        post_list.push("<tr> <td>"+userPost.get("username")+
                                "</td> <td>"+userPost.get("post")+
                                "</td> <td>"+userPost.get("date")+"</td> </tr>");

                    } catch(ParseException pe) {
                        //bad file format!!!
                        pe.printStackTrace();
                    }
                }
                post_list.push("<table> <tr> <th>User</th> <th>Post</th> <th>Date</th> </tr>");
            } catch (IOException e) {
                out.println("<p> failed to read from file \'user_posts.txt\', sorry");
                e.printStackTrace();
            }
            finally {
                while(!post_list.empty()) {
                    out.println(post_list.pop());
                }
                out.println("<br/><hr/><a href=\""+"./newPost.jsp"+"\"> Create a new post </a>");
                out.println("</body>");
                out.println("</html>");
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
