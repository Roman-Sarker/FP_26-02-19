package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("\n");
      out.write("    <!-- Mirrored from brandio.io/envato/iofrm/html/login9.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 27 Aug 2018 10:22:02 GMT -->\n");
      out.write("    <head>\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("        <title>Finger Authentication</title> \n");
      out.write("        <link rel=\"shortcut icon\" href=\"LoginAsset/ico/favicon.jpg\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"LoginAsset/css/bootstrap.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"LoginAsset/css/fontawesome-all.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"LoginAsset/css/iofrm-style.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"LoginAsset/css/iofrm-theme9.css\">\n");
      out.write("        <script type=\"text/javascript\" src=\"LoginAsset/js/jquery.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"LoginAsset/js/popper.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"LoginAsset/js/bootstrap.min.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"LoginAsset/js/main.js\"></script>\n");
      out.write("         \n");
      out.write("    </head>\n");
      out.write("    <body style=\"margin: 0; height: 100%; overflow: hidden\">\n");
      out.write("        ");

            String loginFlag = (String) session.getAttribute("LOGIN");
            if (loginFlag != null) {
                String redirectURL = "home.jsp";
                response.sendRedirect(redirectURL);
            }
            String contextPath = request.getContextPath();
        
      out.write("\n");
      out.write("        \n");
      out.write("        <div class=\"form-body\" class=\"container-fluid\">\n");
      out.write("            <div class=\"row\">\n");
      out.write("                <div class=\"img-holder\">\n");
      out.write("                    <div class=\"bg\"></div>\n");
      out.write("                    <div class=\"info-holder\">\n");
      out.write("                        <h3>Finger Authentication Server</h3> \n");
      out.write("                        <img src=\"LoginAsset/images/finger.png\" alt=\"\">\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("                <div class=\"form-holder\">\n");
      out.write("                    <div class=\"form-content\">\n");
      out.write("                        <div class=\"form-items\">\n");
      out.write("                            <div style=\"margin-bottom: 40px;\"> <!--class=\"website-logo-inside\"> -->\n");
      out.write("                                 <a href=\"index.jsp\">\n");
      out.write("                                    <div class=\"logo\">\n");
      out.write("                                        <img class=\"logo-size\" src=\"LoginAsset/images/agent.png\" alt=\"\">\n");
      out.write("                                    </div>\n");
      out.write("                                </a>\n");
      out.write("                            </div>\n");
      out.write("                            ");

                                String error_msg = (String) request.getParameter("eMessage");
                                if (error_msg != null) { 
      out.write("\n");
      out.write("                            <div class=\"alert alert-danger\">\n");
      out.write("                                ");
      out.print(error_msg);
      out.write(" \n");
      out.write("                            </div>\n");
      out.write("                            ");

                                }
                            
      out.write("\n");
      out.write("\n");
      out.write("                            <form role=\"form\" action=\"");
      out.print(contextPath);
      out.write("/LoginServlet\" method=\"post\">\t\n");
      out.write("                                <input class=\"form-control\" type=\"text\" name=\"username\" placeholder=\"E-mail Address\" style=\"color: #000000;\" required>\n");
      out.write("                                <input class=\"form-control\" type=\"password\" name=\"password\" placeholder=\"Password\" style=\"color: #000000;\" required>\n");
      out.write("                                <div class=\"form-button\">\n");
      out.write("                                    <button id=\"submit\" type=\"submit\" class=\"ibtn\">Login</button>\n");
      out.write("                                </div>\n");
      out.write("                            </form>\n");
      out.write("\n");
      out.write("                        </div>\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("    <!-- Mirrored from brandio.io/envato/iofrm/html/login9.html by HTTrack Website Copier/3.x [XR&CO'2014], Mon, 27 Aug 2018 10:22:03 GMT -->\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
