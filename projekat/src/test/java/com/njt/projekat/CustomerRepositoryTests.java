package com.njt.projekat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.njt.projekat.dao.UserRepository;
import com.njt.projekat.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository customerRepository;
    
    @Test
    public void testCreateCustomer() {
    	User customer = new User();
    	customer.setFirstName("Katarina");
    	customer.setLastName("Beslic");
    	customer.setEmail("katarinabeslic96@gmail.com");
    	customer.setPhoneNumber("+381691996901");
    	customer.setUsername("katarina");
    	customer.setPassword("k123");
    	
    	User savedCustomer = customerRepository.save(customer);
    	User existCustomer = entityManager.find(User.class, savedCustomer.getId());
    	
    	assertEquals(customer.getEmail(), existCustomer.getEmail());
    }
}
