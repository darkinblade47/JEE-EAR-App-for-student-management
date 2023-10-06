import monitorizare.MonitorThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetParametersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int varstaMinima = Integer.parseInt(request.getParameter("minVarsta"));
        int varstaMaxima = Integer.parseInt(request.getParameter("maxVarsta"));
        int medieMinima = Integer.parseInt(request.getParameter("minMedie"));
        int medieMaxima = Integer.parseInt(request.getParameter("maxMedie"));
        MonitorThread.getInstance().maxVarsta = varstaMaxima;
        MonitorThread.getInstance().minVarsta = varstaMinima;
        MonitorThread.getInstance().minMedie = medieMinima;
        MonitorThread.getInstance().maxMedie = medieMaxima;
        request.getRequestDispatcher("./index.jsp").forward(request, response);
    }
}