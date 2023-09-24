package first.app.repository;

import first.app.entity.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.List;

@ApplicationScoped
public class ServiceRepository {

    private final EntityManager em;

    public ServiceRepository(EntityManager em) {
        this.em = em;
    }

    public List<Service> getServices (){
        return this.em.createQuery("select service from Service service",
                Service.class).getResultList();
    }
}
