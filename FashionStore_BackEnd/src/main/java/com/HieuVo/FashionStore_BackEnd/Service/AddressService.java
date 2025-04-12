package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.AddressDTO;
import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.AdderssRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AddressService {

    private final AdderssRepository adderssRepository;
    private final UserRepository userRepository;

    public AddressService(AdderssRepository adderssRepository, UserRepository userRepository) {
        this.adderssRepository = adderssRepository;
        this.userRepository = userRepository;
    }


    public List<AddressDTO> getAddressByUser(UserDetails userDetails) {

        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }
        String username = userDetails.getUsername();

        List<Address> adrs = adderssRepository.findAddressByUser_UserName(username);

        List<AddressDTO> addressDTOS = adrs.stream().map(
                address -> {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setAddressId(address.getAddressId());
                    addressDTO.setStreetName(address.getStreetName());
                    addressDTO.setCityName(address.getCityName());
                    addressDTO.setDistrictName(address.getDistrictName());
                    addressDTO.setWardName(address.getWardName());
                    return addressDTO;
                }
        ).collect(Collectors.toList());
        return addressDTOS;
    }

    public String addAddressByUser(UserDetails userDetails, Address address) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }
        String username = userDetails.getUsername();

        User user = this.userRepository.findByUserName(username).get();
        address.setUser(user);
        this.adderssRepository.save(address);
        return "Add address successfully";
    }
}
