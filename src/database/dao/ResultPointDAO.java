package database.dao;

import database.dao.models.ResultPoint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

public class ResultPointDAO implements ResultPointDAOable {

    private EntityManager entityManager;

    public ResultPointDAO(){
        EntityManagerFactory entityManagerFactory = EntityManagerUtility.getEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public ResultPoint insertPoint(ResultPoint point) {
        entityManager.getTransaction().begin();
        entityManager.persist(point);
        entityManager.getTransaction().commit();
        return point;
    }

    @Override
    public ResultPoint findResultPoint(String id) {
        return entityManager.find(ResultPoint.class, id);
    }

    @Override
    public List<ResultPoint> findAllPoints() {
        TypedQuery<ResultPoint> query = entityManager.createQuery("Select e from ResultPoint e", ResultPoint.class);
        return query.getResultList();
    }

    @Override
    public void removePoint(String id) {
        ResultPoint point = findResultPoint(id);
        if (point != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(point);
            entityManager.getTransaction().commit();
        }
    }
}
