package com.example.springbatch;

import com.example.springbatch.model.Customer;
import com.example.springbatch.repository.CustomRepository;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
@ClientCacheApplication(name = "SpringBatchApplication")
@EnableEntityDefinedRegions(
        basePackageClasses = Customer.class,
        clientRegionShortcut = ClientRegionShortcut.LOCAL
)
@EnableGemfireRepositories
public class SpringBatchApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringBatchApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomRepository repository) {
       return args -> {
           Customer alice = new Customer("Adult Alice", "Alice");
           Customer bob = new Customer("Baby Bob", "Bob");
           Customer carol = new Customer("Teen Carol", "Carol");

           System.out.println("Before accessing data in Apache Geode...");
           Arrays.asList(alice, bob, carol).forEach(customer -> System.out.println("\t" + customer));
           System.out.println("Saving Alice, Bob and Carol to Pivotal GemFire...");

           repository.save(alice);
           repository.save(bob);
           repository.save(carol);

           System.out.println("Lookup each person by name...");

           Arrays.asList(alice.getLastName(), bob.getLastName(), carol.getLastName())
                   .forEach(lastName-> System.out.println("\t" + repository.findByLastName(lastName)));
           System.out.println("Query adults (over 18):");

       };
    }
}
