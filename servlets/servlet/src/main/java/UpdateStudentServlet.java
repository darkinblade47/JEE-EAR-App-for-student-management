import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // pregatire EntityManager
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();
        StringBuilder responseText = new StringBuilder();

        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));
        int id = Integer.parseInt(request.getParameter("id"));
        int medie = Integer.parseInt(request.getParameter("medie"));

        TypedQuery<StudentEntity> query = em.createQuery("update StudentEntity s set s.nume=:cnume, s.prenume=:cprenume,s.varsta=:cvarsta,s.medie=:cmedie where s.id=:cid", StudentEntity.class);
        query.setParameter("cnume", nume).setParameter("cprenume", prenume).setParameter("cvarsta", varsta).setParameter("cid", id).setParameter("cmedie", medie);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            query.executeUpdate();
            transaction.commit();
            responseText.append("<h3>!!!<i>Actualizare executata cu succes</i>!!!</h3>");
            responseText.append("<h2>Lista actualizata studenti</h2>");
        } catch (TransactionRequiredException ex) {
            ex.printStackTrace();
            responseText.append("<h3>!!!<i>Actualizare ESUATA</i>!!!</h3>");
            responseText.append("<h2>Lista studenti</h2>");
        }

        responseText.append("<table border='1'><thead><tr><th>ID</th><th>Nume</th><th>Prenume</th><th>Varsta</th><th>Medie</th></thead>");
        responseText.append("<tbody>");

        // preluare date studenti din baza de date
        query = em.createQuery("select student from StudentEntity student", StudentEntity.class);
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