package com.example.restaurant_order_portal.controller;

import com.example.restaurant_order_portal.constants.AppConstants;
import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;
import com.example.restaurant_order_portal.entity.Address;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.repository.AddressRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Controller for Address APIs
 */
@RestController
@RequestMapping(AppConstants.BASE_ADDRESS_URL)
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository,
                             UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save new address
     */
    @PostMapping(AppConstants.CREATE_ADDRESS)
    public AddressResponseDTO saveAddress(@RequestBody AddressRequestDTO dto,
                                          Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setUser(user);
        address.setStreetAddress(dto.getStreetAddress());
        address.setCity(dto.getCity());
        address.setPincode(dto.getPincode());

        Address savedAddress = addressRepository.save(address);

        return mapToDTO(savedAddress);
    }

    /**
     * Get all addresses of logged-in user
     */
    @GetMapping(AppConstants.GET_USER_ADDRESSES)
    public List<AddressResponseDTO> getUserAddresses(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Update address
     */
    @PutMapping(AppConstants.UPDATE_ADDRESS)
    public AddressResponseDTO updateAddress(@PathVariable Long addressId,
                                 @RequestBody AddressRequestDTO dto,
                                 Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update this address");
        }

        address.setStreetAddress(dto.getStreetAddress());
        address.setCity(dto.getCity());
        address.setPincode(dto.getPincode());
        Address updatedAddress = addressRepository.save(address);

        return mapToDTO(updatedAddress);
    }

    /**
     * Delete Address
     */
    @DeleteMapping(AppConstants.DELETE_ADDRESS)
    public String deleteAddress(@PathVariable Long addressId,
                                Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this address");
        }

        addressRepository.delete(address);

        return "Address deleted successfully";
    }
    /**
     *Entity to DTO
     */
    private AddressResponseDTO mapToDTO(Address address) {

        AddressResponseDTO dto = new AddressResponseDTO();

        dto.setId(address.getId());
        dto.setStreetAddress(address.getStreetAddress());
        dto.setCity(address.getCity());
        dto.setPincode(address.getPincode());

        return dto;
    }
}
