package first.app.controller;

import first.app.entity.Customer;
import first.app.repository.CustomerRepository;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.ArrayList;
import java.util.List;

@Path("/customers")
@Produces("application/json")
@Consumes("application/json")
public class CustomerController {

    private final CustomerRepository customerRepository;
    public CustomerController (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @GET
    public List<Customer> getCustomersByEmail(@RestQuery("email") String email){
        if(StringUtil.isNullOrEmpty(email)){
            return this.customerRepository.listAll();
        }

        List<Customer> customers = new ArrayList<>();
        Customer customer = customerRepository.getByEmail(email);
        customers.add(customer);
        return customers;
    }

    @GET
    @ResponseStatus(200)
    @Path("/{id}")
    public Customer getCustomerById(@RestPath("id") long id){
        Customer customer = this.customerRepository.findById(id);
        if (customer == null) {
            throw new NotFoundException();
        }
        return customer;
    }

    @POST
    @Transactional
    @ResponseStatus(201)
    public Customer addCustomer(Customer customer){
        this.customerRepository.persist(customer);
        return customer;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @ResponseStatus(204)
    public void updateCustomer(@RestPath("id")long id ,Customer customer){

        if(id != customer.getId()){
            throw new BadRequestException();
        }

        Customer entity = this.customerRepository.findById(id);

        if(entity == null){
            throw new NotFoundException();
        }

        entity.setAddress(customer.getAddress());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        this.customerRepository.persist(entity);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deleteCustomerById(@RestPath("id") long id){
        this.customerRepository.deleteById(id);
    }

}
