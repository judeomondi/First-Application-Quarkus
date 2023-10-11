package first.app.repository;

import first.app.entity.Customer;
import first.app.entity.Vendor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Customer getByEmail(String email){
        return find("email", email).firstResult();
    }

}
