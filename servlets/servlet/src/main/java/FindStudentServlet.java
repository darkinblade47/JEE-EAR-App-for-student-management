import ejb.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FindStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        StringBuilder responseText = new StringBuilder();
        String cautare = request.getParameter("text");
        String criteriu = request.getParameter("criteriu");
        System.out.println("help" + " " + criteriu + " " + cautare);
        responseText.append("<h3>Actualizare:</h2>");
        responseText.append("<form action=\"./update-student\" method=\"post\">\n" +
                " ID: <input type=\"number\" name=\"id\" />\n" +
                "<br />\n" +
                " Nume: <input type=\"text\" name=\"nume\" />\n" +
                "<br />\n" +
                "Prenume: <input type=\"text\" name=\"prenume\" />\n" +
                "<br />\n" +
                "Varsta: <input type=\"number\" name=\"varsta\" />\n" +
                "<br />\n" +
                "Medie: <input type=\"number\" name=\"medie\" />\n" +
                "<br />\n" +
                "<button type=\"submit\" name=\"submit\">Actualizeaza</button>\n" +
                "<button type=\"submit\" formaction=\"./delete-student\">Delete</button>\n" +
                "</form>");

        responseText.append("<h3>Lista studenti</h3>");
        responseText.append("<table border='1'><thead><tr><th>ID</th><th>Nume</th><th>Prenume</th><th>Varsta</th><th>Medie</th></thead>");
        responseText.append("<tbody>");

        // preluare date studenti din baza de date
        TypedQuery<StudentEntity> query;

        //query = em.createQuery("select student from StudentEntity student where :criteriu=:cautare", StudentEntity.class);
        //query.setParameter("criteriu", criteriu).setParameter("cautare",cautare);


        if (criteriu.equals("prenume")) {
            query = em.createQuery("select student from StudentEntity student where student.prenume=:cprenume", StudentEntity.class);
            query.setParameter("cprenume", cautare);
        } else if (criteriu.equals("nume")) {
            query = em.createQuery("select student from StudentEntity student where student.nume=:cnume", StudentEntity.class);
            query.setParameter("cnume", cautare);
        } else {
            query = em.createQuery("select student from StudentEntity student", StudentEntity.class);
        }


        List<StudentEntity> results = query.getResultList();
        for (StudentEntity student : results) {
            // se creeaza cate un rand de tabel HTML pentru fiecare student gasit
            responseText.append("<tr><td>" + student.getId() + "</td><td>" +
                    student.getNume() + "</td><td>" + student.getPrenume() +
                    "</td><td>" + student.getVarsta() + "</td><td>" + student.getMedie() + "</td></tr>");
        }

        responseText.append("</tbody></table><br /><br /><a href='./'>Inapoi la meniul principal</a>");

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());
    }
}