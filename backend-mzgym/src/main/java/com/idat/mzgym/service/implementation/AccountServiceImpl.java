package com.idat.mzgym.service.implementation;


import com.idat.mzgym.dto.*;
import com.idat.mzgym.model.Account;
import com.idat.mzgym.model.Customers;
import com.idat.mzgym.repository.AccountRepository;
import com.idat.mzgym.repository.CustomerRepository;
import com.idat.mzgym.repository.UserRepository;

import com.idat.mzgym.service.AccountService;

import com.idat.mzgym.util.enums.Role;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;




import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;



    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomerRegistration createCustomer(Customers customers){
        CustomerRegistration customerRegistration = new CustomerRegistration();
        int anoActual = LocalDate.now().getYear();
        String numeracion = obtenerNumeracionAutomatica();

        String email = customers.getEmail();
        customerRegistration.setEmail(email);
        String password=customers.getPassword();

        customerRegistration.setPassword(password);
        String name = customers.getName();
        String lastName = customers.getLastName();
        String fullName = name + " " + lastName;
        customerRegistration.setFullName(fullName);
        String verificationCode = RandomString.make(64);
        customerRegistration.setVerificationCode(verificationCode);


        Customers saveCustomer = new Customers(email, password);
        saveCustomer.setEmail(email);
        saveCustomer.setPassword(password);
        saveCustomer.setName(name);
        saveCustomer.setLastName(lastName);
        saveCustomer.setMembershipState("ON_HOLD");
        String customerNumber = anoActual + "-" + numeracion;
        saveCustomer.setRol(Role.USER);
        saveCustomer.setNumber(customerNumber);
        saveCustomer.setVerificationCode(verificationCode);
        String uuid=saveCustomer.getAccountUuid();

        customerRegistration.setToken("*");


        customerRepository.save(saveCustomer);

        return customerRegistration;
    }



    @Override
    public CustomerUpdate updateCustomer(String id,CustomerUpdate customerUpdate){
        if(!customerRepository.existsById(id)){
            throw new IllegalStateException("Customer does not exists");
        }
        Customers existCustomer=customerRepository.findByAccountUuid(id).get();
        existCustomer.setName(customerUpdate.getName());
        existCustomer.setLastName(customerUpdate.getLastName());
        existCustomer.setDocumentType(customerUpdate.getDocumentType());
        existCustomer.setDocumentNumber(customerUpdate.getDocumentNumber());
        existCustomer.setAddress(customerUpdate.getAddress());

        CustomerUpdate updateCustomer =new CustomerUpdate();
        updateCustomer.setName(customerUpdate.getName());
        updateCustomer.setLastName(customerUpdate.getLastName());
        updateCustomer.setDocumentType(customerUpdate.getDocumentType());
        updateCustomer.setDocumentNumber(customerUpdate.getDocumentNumber());
        updateCustomer.setAddress(customerUpdate.getAddress());

        if(!(customerUpdate.getEmail()==null || customerUpdate.getEmail().isEmpty())){
            existCustomer.setEmail(customerUpdate.getEmail());
            updateCustomer.setEmail(customerUpdate.getEmail());

        }
        if(!(customerUpdate.getPassword()==null || customerUpdate.getPassword().isEmpty())){
            existCustomer.setPassword(customerUpdate.getPassword());
            updateCustomer.setPassword(customerUpdate.getPassword());
        }
        if(!(customerUpdate.getMembershipState()==null || customerUpdate.getMembershipState().isEmpty())){
            existCustomer.setMembershipState(customerUpdate.getMembershipState());
            updateCustomer.setMembershipState(customerUpdate.getMembershipState());
        }
        updateCustomer.setEmail(existCustomer.getEmail());
        updateCustomer.setPassword(existCustomer.getPassword());
        updateCustomer.setMembershipState(existCustomer.getMembershipState());


        customerRepository.save(existCustomer);

        return updateCustomer;
    }

    @Override
    public CustomerRegisterResponseUuid createCustomer3(Customers customers){
        CustomerRegistration customerRegistration = new CustomerRegistration();
        int anoActual = LocalDate.now().getYear();
        String numeracion = obtenerNumeracionAutomatica();

        String email = customers.getEmail();
        customerRegistration.setEmail(email);
        String password=customers.getPassword();

        customerRegistration.setPassword(password);
        String name = customers.getName();
        String lastName = customers.getLastName();
        String fullName = name + " " + lastName;
        customerRegistration.setFullName(fullName);
        String verificationCode = RandomString.make(64);
        customerRegistration.setVerificationCode(verificationCode);



        Customers saveCustomer = new Customers(email, password);
        saveCustomer.setEmail(email);
        saveCustomer.setPassword(password);
        saveCustomer.setName(name);
        saveCustomer.setLastName(lastName);
        saveCustomer.setMembershipState("ON_HOLD");
        String customerNumber = anoActual + "-" + numeracion;
        saveCustomer.setRol(Role.USER);
        saveCustomer.setNumber(customerNumber);
        saveCustomer.setVerificationCode(verificationCode);
        String uuid=saveCustomer.getAccountUuid();
        customerRegistration.setToken("*");

        CustomerRegisterResponseUuid dto=new CustomerRegisterResponseUuid();
        dto.setUuid(uuid);
        dto.setEmail(email);
        dto.setName(name);
        dto.setLastname(lastName);



        customerRepository.save(saveCustomer);

        return dto;
    }
    @Override
    public Optional<Account> findByEmail(String email) {
        try {
            if (email == null || email.isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            return accountRepository.findByEmail(email);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error finding account by email: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error finding account by email", e);
        }
    }


    private String obtenerNumeracionAutomatica() {
        String maxNumber = accountRepository.findByNumber();
        if (maxNumber == null || maxNumber.isEmpty()) {
            return "1";
        } else {
            int separatorIndex = maxNumber.indexOf("-");
            if (separatorIndex != -1 && separatorIndex + 1 < maxNumber.length()) {
                String numeracion = maxNumber.substring(separatorIndex + 1);
                int number = Integer.parseInt(numeracion.trim());
                number++;
                return String.valueOf(number);
            } else {
                return "1";
            }
        }
    }

    @Override
    public Optional<Customers> findByUuid(String uuid) {
        try {
            if (uuid == null || uuid.isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            return customerRepository.findByAccountUuid(uuid);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error finding account by email: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error finding account by email", e);
        }
    }
}

