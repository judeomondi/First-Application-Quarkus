package first.app.repository;

import first.app.entity.Vendor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {
    public Vendor getByVendorEmail(String email){
        return find("email", email).firstResult();
    }

    public Vendor getByVendorName(String name){
        return find("lower(name)", name.toLowerCase()).firstResult();
    }
}
