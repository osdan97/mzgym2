package com.idat.mzgym.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterResponseUuid {
    private String uuid;
    private String email;
    private String name;
    private String lastname;

}
