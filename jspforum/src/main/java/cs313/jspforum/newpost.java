package cs313.jspforum;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;


@WebServlet(name = "newpost", urlPatterns = {"/newpost"})
public class newpost extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet newpost</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet newpost at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // We're gonna save the new post, then redirect to loadposts
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ( (request.getParameter("text")).equals("") ) {
            request.getRequestDispatcher("./newPost.jsp").forward(request, response);
        } else {
            Date date = new Date( );
            SimpleDateFormat dateFormat = new SimpleDateFormat ("MM/dd/yyyy 'at' hh:mm");
            String dateString = dateFormat.format(date);
            String message = request.getParameter("text");
            String username = (String)(request.getSession(false)).getAttribute("username");
            String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR"); 
            String filename = (dataDirectory.equals("") || dataDirectory == null)? "/user_posts.txt":dataDirectory + "user_posts.txt" ;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                JSONObject obj = new JSONObject();
                obj.put("username", username);
                obj.put("date", dateString);
                obj.put("post", message);
                
                writer.write("\n" + obj);
                writer.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            response.sendRedirect("loadposts");
            
        }
        
        
        
        //processRequest(request, response);
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
