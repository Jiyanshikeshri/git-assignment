package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;
import com.example.restaurant_order_portal.service.AddressService;
import com.example.restaurant_order_portal.validation.AddressValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Controller for Address APIs
 */
@RestController
@RequestMapping(AppConstants.BASE_ADDRESS_URL)
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Save new address
     */
    @PostMapping(AppConstants.CREATE_ADDRESS)
    public AddressResponseDTO saveAddress(@RequestBody AddressRequestDTO addressRequestDTO,
                                          Authentication authentication) {

        AddressValidator.validate(addressRequestDTO);

        return addressService.saveAddress(addressRequestDTO, authentication.getName());
    }

    /**
     * Get all addresses of logged-in user
     */
    @GetMapping(AppConstants.GET_USER_ADDRESSES)
    public List<AddressResponseDTO> getUserAddresses(Authentication authentication) {

            return addressService.getUserAddresses(authentication.getName());
    }

    /**
     * Update address
     */
    @PutMapping(AppConstants.UPDATE_ADDRESS)
    public AddressResponseDTO updateAddress(@PathVariable Long addressId,
                                 @RequestBody AddressRequestDTO addressRequestDTO,
                                 Authentication authentication) {

        AddressValidator.validate(addressRequestDTO);

        return addressService.updateAddress(addressId, addressRequestDTO, authentication.getName());
    }

    /**
     * Delete Address
     */
    @DeleteMapping(AppConstants.DELETE_ADDRESS)
    public String deleteAddress(@PathVariable Long addressId,
                                Authentication authentication) {

        addressService.deleteAddress(addressId, authentication.getName());

        return "Address deleted successfully";
    }
}
