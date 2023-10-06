package monitorizare;

import ejb.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class MonitorThread implements Runnable {
    private volatile static MonitorThread instance = null;
    public boolean shouldStop = false;
    public int state = 0;
    public int minVarsta = 0;
    public int maxVarsta = 150;
    public int minMedie = 0;
    public int maxMedie = 10;
    public StudentEntity studentEronat = null;
    public String cauzaEroare;

    public MonitorThread() {

    }

    //Singleton database
    public static MonitorThread getInstance() {
        if (instance == null)
            instance = new MonitorThread();
        return instance;
    }

    public synchronized void run() {
        while (state == 0) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
            EntityManager em = factory.createEntityManager();
            System.out.println("Thread-ul de monitorizare ruleaza...\n");

            TypedQuery<StudentEntity> query = em.createQuery("select student from StudentEntity student", StudentEntity.class);
            List<StudentEntity> results = query.getResultList();
            for (StudentEntity student : results) {
                if (student.getVarsta() > maxVarsta || student.getVarsta() < minVarsta) {
                    shouldStop = true;
                    String valoare = Integer.toString(student.getVarsta());
                    cauzaEroare = "Constrangerea de varsta nu este respectata:" +
                            valoare + " nu se afla in intervalul [" + minVarsta + ", " + maxVarsta + "] !!";
                    studentEronat = student;

                } else if (student.getMedie() > maxMedie || student.getMedie() < minMedie) {
                    shouldStop = true;
                    String valoare = Integer.toString(student.getMedie());
                    cauzaEroare = "Constrangerea de medie nu este respectata:" +
                            valoare + " nu se afla in intervalul [" + minMedie + ", " + maxMedie + "] !!";
                    studentEronat = student;
                }
            }
            em.close();
            factory.close();
        }
    }
}
