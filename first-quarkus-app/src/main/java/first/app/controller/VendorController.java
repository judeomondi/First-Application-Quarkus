package first.app.controller;

import first.app.entity.Customer;
import first.app.entity.Vendor;
import first.app.repository.CustomerRepository;
import first.app.repository.VendorRepository;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.ArrayList;
import java.util.List;

@Path("/vendors")
@Produces("application/json")
@Consumes("application/json")
public class VendorController {
    private final VendorRepository vendorRepository;
    public VendorController (VendorRepository vendorRepository){
        this.vendorRepository = vendorRepository;
    }

    @GET
    public List<Vendor> getVendorByEmail(@RestQuery("email") String email){
        if(StringUtil.isNullOrEmpty(email)){
            return this.vendorRepository.listAll();
        }

        List<Vendor> vendors = new ArrayList<>();
        Vendor vendor = vendorRepository.getByVendorEmail(email);
        vendors.add(vendor);
        return vendors;
    }

    @GET
    @ResponseStatus(200)
    @Path("/{id}")
    public Vendor getVendorById(@RestPath("id") long id){
        Vendor vendor = this.vendorRepository.findById(id);
        if (vendor == null) {
            throw new NotFoundException();
        }
        return vendor;
    }

    @POST
    @Transactional
    @ResponseStatus(201)
    public Vendor addVendor(Vendor vendor){
        this.vendorRepository.persist(vendor);
        return vendor;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @ResponseStatus(204)
    public void updateVendor(@RestPath("id")long id ,Vendor vendor){

        if(id != vendor.getId()){
            throw new BadRequestException();
        }

        Vendor entity = this.vendorRepository.findById(id);

        if(entity == null){
            throw new NotFoundException();
        }

        entity.setAddress(vendor.getAddress());
        entity.setEmail(vendor.getEmail());
        entity.setPhone(vendor.getPhone());
        entity.setName(vendor.getName());
        entity.setContact(vendor.getContact());
        this.vendorRepository.persist(entity);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteVendorById(@RestPath("id") long id){
        this.vendorRepository.deleteById(id);
    }
}
