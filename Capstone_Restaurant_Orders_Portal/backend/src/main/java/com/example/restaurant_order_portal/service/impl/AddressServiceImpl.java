package com.example.restaurant_order_portal.service.impl;

import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;
import com.example.restaurant_order_portal.entity.Address;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.AddressRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of AddressService.
 *
 * Contains business logic for managing user addresses.
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    /**
     * Constructor-based dependency injection.
     */
    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    /**
     * Saves a new address for the logged-in user.
     */
    @Override
    public AddressResponseDTO saveAddress(AddressRequestDTO addressRequestDTO, String email) {

        log.info("Saving address for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found: {}", email);
                    return new ResourceNotFoundException("User not found");
                });

        Address address = new Address();
        address.setUser(user);
        address.setStreetAddress(addressRequestDTO.getStreetAddress());
        address.setCity(addressRequestDTO.getCity());
        address.setPincode(addressRequestDTO.getPincode());

        Address saved = addressRepository.save(address);

        return mapToDTO(saved);
    }

    /**
     * Retrieves all addresses of the logged-in user.
     */
    @Override
    public List<AddressResponseDTO> getUserAddresses(String email) {

        log.info("Fetching addresses for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return addressRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * Updates an existing address if it belongs to the logged-in user.
     */
    @Override
    public AddressResponseDTO updateAddress(Long addressId, AddressRequestDTO addressRequestDTO, String email) {

        log.info("Updating addressId: {}", addressId);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You cannot update this address");
        }

        address.setStreetAddress(addressRequestDTO.getStreetAddress());
        address.setCity(addressRequestDTO.getCity());
        address.setPincode(addressRequestDTO.getPincode());

        return mapToDTO(addressRepository.save(address));
    }

    /**
     * Deletes an address if it belongs to the logged-in user.
     */
    @Override
    public void deleteAddress(Long addressId, String email) {

        log.info("Deleting addressId: {}", addressId);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You cannot delete this address");
        }

        addressRepository.delete(address);
    }

    /**
     * Converts Address entity to AddressResponseDTO.
     */
    private AddressResponseDTO mapToDTO(Address address) {
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setStreetAddress(address.getStreetAddress());
        addressResponseDTO.setCity(address.getCity());
        addressResponseDTO.setPincode(address.getPincode());
        return addressResponseDTO;
    }
}
