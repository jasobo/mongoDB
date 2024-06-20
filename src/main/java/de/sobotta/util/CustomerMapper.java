package de.sobotta.util;

import de.sobotta.DTO.CustomerDTO;
import de.sobotta.Pojo.Customer;
import de.sobotta.Response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CustomerMapper {
    de.sobotta.util.CustomerMapper INSTANCE = Mappers.getMapper(de.sobotta.util.CustomerMapper.class);

    Customer toPojo(CustomerDTO customerDTO);

    CustomerDTO pojoToCustomerDTO(Customer customer);

    @Mapping(target = "addressResponse.street", source = "address.street")
    @Mapping(target = "addressResponse.houseNumber", source = "address.houseNumber")
    @Mapping(target = "addressResponse.zip", source = "address.zip")
    @Mapping(target = "addressResponse.city", source = "address.city")
    @Mapping(target = "addressResponse.country", source = "address.country")
    CustomerResponse forResponse(Customer customer);
}

