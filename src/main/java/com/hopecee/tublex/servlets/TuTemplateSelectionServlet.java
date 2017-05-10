/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.tublex.servlets;

//import com.hopecee.tushop.events.ExceptionEventBadToken;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hope
 */
@Named
@WebServlet(name = "tuTemplateSelectionServlet", urlPatterns = {"/tuTemplateSelectionServlet"})
public class TuTemplateSelectionServlet extends HttpServlet {

    private static final long serialVersionUID = -1152423911478295319L;

    public static final String JSON = "application/json";
    public static final String LANGUAGE = "en";
    private final String BAD_TOKEN = "BadToken";
    //@Inject
    //private Event<ExceptionEventBadToken> exceptionEventBadToken;
    //private Locale locale;
    private String host = "host";
    private String defualtTemplate = "/WEB-INF/templates/defualt/template.xhtml";

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/**
        String token = "";
        try {
            token = req.getAttribute("CheckRequestVerificationToken").toString();
        } catch (NullPointerException e) {
            Logger.getLogger(TuLocaleManagerServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        if (token.equals(BAD_TOKEN)) {
            //getBadToken(req, resp);
            exceptionEventBadToken.fire(new ExceptionEventBadToken(req, resp));
        } else {
            //  String RequestVerificationToken = req.getParameter("RequestVerificationToken");
            System.out.println("DDDDDDDDD = ,,,,,,,,,,,,language,,,,,,,,,,,,");
            String method = req.getParameter("method");

            Enumeration e = req.getParameterNames();
            while (e.hasMoreElements()) {
                String attr = (String) e.nextElement();
                System.out.println(" attr = " + attr);
                Object value = req.getParameter(attr);
                System.out.println(" value = " + value);
            }

            /* try {* /
            if ("hostManager".equals(method)) {
                reDirect(req, resp);

            }

        }
        */
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //getHost(req, resp);
    }

    public void reDirect(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * String language = LANGUAGE;
         *
         * try { if (req.getParameter("language") != null) { language =
         * req.getParameter("language"); control = language; } } catch
         * (Exception ex) {
         * Logger.getLogger(TuLocaleManagerServlet.class.getName()).log(Level.SEVERE,
         * null, ex); } locale = new Locale(language); resp.setLocale(locale);
         *
         * // resp.sendRedirect("index");//TODO Why this is not working?
         * resp.sendRedirect("/"); //return "indexredirect?faces-redirect=true";
         */
    }

    public String getTemplate() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse resp = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        //String t = request.getRemoteAddr();
        //System.out.println("getRemoteAddr ==== : " + t);
        //String host = "host";
        String template = defualtTemplate;
        try {
            if (req.getServerName() != null) {
                host = req.getServerName();
                System.out.println("template ==////xxcs//// : " + req.getServerName());
        
                template = "/WEB-INF/templates/tublex/template.xhtml";
            }

        } catch (Exception ex) {
            Logger.getLogger(TuTemplateSelectionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("template ==////xxs//// : " + req.getServerName());
        return template;

    }

}
