package com.idat.mzgym.controller;


import com.idat.mzgym.dto.Mensaje;
import com.idat.mzgym.model.Customers;
import com.idat.mzgym.repository.CustomerRepository;
import com.idat.mzgym.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
@CrossOrigin(origins="*")
public class CustomerController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerRepository customerRepository;



    @GetMapping("/list")
    public ResponseEntity<?>listCustomer(){
        List<Customers> list=customerRepository.findAll();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?>customerFindById(@PathVariable String id){
        Customers customer=customerRepository.findByAccountUuid(id).get();
        if(!customerRepository.existsById(id)){
            return new ResponseEntity(new Mensaje(" Customer does not exist"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customer,HttpStatus.OK);
    }
}
