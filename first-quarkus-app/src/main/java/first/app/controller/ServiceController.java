package first.app.controller;

import first.app.entity.Service;
import first.app.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/services")
@Produces("application/json")
@Consumes("application/json")
public class ServiceController {

    private final ServiceRepository serviceRepository;
    public ServiceController (ServiceRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    @GET
    public List<Service> getServices(){
        return this.serviceRepository.listAll();
    }

    @GET
    @Path("/{id}")
    @ResponseStatus(200)
    public Service getServiceById(@RestPath("id") long id){
        Service service = this.serviceRepository.findById(id);
        if (service == null) {
            throw new NotFoundException();
        }
        return service;
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @ResponseStatus(204)
    public void updateService(@RestPath("id") long id, Service service){
        if (id != service.getId()) {
            throw new BadRequestException();
        }

        Service entity = this.serviceRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.setName(service.getName());
        entity.setPrice(service.getPrice());
    }

    @DELETE
    @Transactional
    @ResponseStatus(205)
    public void deleteService(@RestPath("id") long id){
        this.serviceRepository.deleteById(id);
    }
}
