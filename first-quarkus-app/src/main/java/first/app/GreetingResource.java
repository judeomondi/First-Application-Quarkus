package first.app;

import first.app.entity.Service;
import first.app.repository.ServiceRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.quarkus.logging.Log;

import java.util.List;

@Path("/hello")
public class GreetingResource {

    private final ServiceRepository serviceRepository;
    public GreetingResource(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;

    }

    @ConfigProperty(name = "developer.name")
    String name;

    @ConfigProperty(name = "fizz", defaultValue = "fizz")
    String fizz;

    @ConfigProperty(name = "buzz", defaultValue = "buzz")
    String buzz;

    @ConfigProperty(name = "fizzbuzz", defaultValue = "fizzbuzz")
    String fizzbuzz;

    @ConfigProperty(name = "max.Number", defaultValue = "27")
    int maxNumber;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info(name + "welcome back");

        for(int number=0; number<=maxNumber; number++){
            String result = "";
            if(number % 5 == 0 && number % 3 == 0){
                result = number + " " + fizzbuzz;
            } else if(number % 3 == 0){
                result = number + " " + fizz;
            } else if(number % 5 == 0){
                result = number + " " + buzz;
            }
            System.out.println(result);
        }

        List<Service> services = serviceRepository.getServices();
        services.forEach(System.out::println);

        return "Hello from RESTEasy Reactive" + name;
    }



}
