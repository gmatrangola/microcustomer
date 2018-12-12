package com.matrangola.microcustomer.controller;

import com.matrangola.microcustomer.aop.Profile;
import com.matrangola.microcustomer.data.model.Customer;
import com.matrangola.microcustomer.data.repository.CustomerRepository;
import com.matrangola.microcustomer.validation.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/customers", produces = {"application/json"})
public class CustomerController {
    private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("YYYYMMdd");

    @Autowired
    CustomerRepository customerRepository;

    @Profile
    @RequestMapping(path = "/")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


    @Profile
    @RequestMapping(path = "/customer/{id}")
    public Customer get(@PathVariable UUID id) {
        Optional<Customer> cust = customerRepository.findById(id);
        if (cust.isPresent()) return cust.get();
        else return new Customer(); // throw error for 402
    }

    @Profile
    @RequestMapping("/new")
    public Customer newCustomer(@RequestParam String email,
                               @RequestParam String lastName,
                               @RequestParam String firstName,
                                 @RequestParam(name = "birthday", required = false) String birthdayText) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setEmail(email);
        customer.setLastName(lastName);
        customer.setFirstName(firstName);
        if (birthdayText != null) {
            try {
                customer.setBirthday(BIRTHDAY_TEXT_FORMAT.parse(birthdayText));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        customerRepository.save(customer);
        return customer;
    }

    @Profile
    @RequestMapping(path = "/new", method = RequestMethod.PUT)
    public Customer newCustomer(@RequestBody Customer customer) {
        customer.setId(UUID.randomUUID());
        customerRepository.save(customer);
        return customer;
    }

    @Profile
    @RequestMapping(path = "/zipcode/{zipcode}", method = RequestMethod.GET)
    public List<Customer> zipcode(@PathVariable int zipcode) {
        return customerRepository.findCustomersByZipcode(zipcode);
    }

    @Profile
    @RequestMapping(path = "/customer")
    public Customer customerByEmail(@RequestParam String email) throws ResourceException {
        Optional<Customer> result = customerRepository.findCustomerByEmail(email);
        if (result.isPresent()) return result.get();
        else throw new ResourceException(Customer.class, email);
    }

    @Profile
    @RequestMapping(path={"/foo", "/foo/bar", "*.bar", "dove/*,**/data"})
    public String foo() {
        return "foo mapping success";
    }

    @RequestMapping(path = "/whoami")
    public String whoami(Principal principal,
                         @AuthenticationPrincipal UserDetails userDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("Principal.getUserName = ").append(principal.getName()).append("\n");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        sb.append("SecurityContextHolder.getContext().getAuthentication()=")
                .append(auth.getName()).append("\n");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        sb.append(       	"UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()=")
                .append(user.getUsername()).append("\n");
        sb.append("userDetails.getUsername() = ")
                .append(userDetails.getUsername()).append("\n");
        return sb.toString();
    }


}