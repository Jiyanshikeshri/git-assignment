package com.example.restaurant_order_portal.service;
import com.example.restaurant_order_portal.dto.AddressRequestDTO;
import com.example.restaurant_order_portal.dto.AddressResponseDTO;
import com.example.restaurant_order_portal.entity.Address;
import com.example.restaurant_order_portal.entity.User;
import com.example.restaurant_order_portal.exception.BadRequestException;
import com.example.restaurant_order_portal.exception.ResourceNotFoundException;
import com.example.restaurant_order_portal.repository.AddressRepository;
import com.example.restaurant_order_portal.repository.UserRepository;
import com.example.restaurant_order_portal.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private User user;
    private Address address;
    private AddressRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setEmail("test@gmail.com");

        address = new Address();
        ReflectionTestUtils.setField(address, "id", 1L);
        address.setUser(user);
        address.setStreetAddress("Street 1");
        address.setCity("Bhopal");
        address.setPincode("462001");

        requestDTO = new AddressRequestDTO();
        requestDTO.setStreetAddress("Street 1");
        requestDTO.setCity("Bhopal");
        requestDTO.setPincode("462001");
    }

    /**
     * Save address
     */

    @Test
    void saveAddress_success() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        AddressResponseDTO response = addressService.saveAddress(requestDTO, "test@gmail.com");

        assertNotNull(response);
        assertEquals("Bhopal", response.getCity());
    }

    @Test
    void saveAddress_userNotFound() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.saveAddress(requestDTO, "test@gmail.com"));
    }

    /**
     * Get address
     */

    @Test
    void getUserAddresses_success() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findByUserId(1L))
                .thenReturn(List.of(address));

        List<AddressResponseDTO> result = addressService.getUserAddresses("test@gmail.com");

        assertEquals(1, result.size());
    }

    @Test
    void getUserAddresses_userNotFound() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.getUserAddresses("test@gmail.com"));
    }

    /**
     * Update address
     */

    @Test
    void updateAddress_success() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        AddressResponseDTO response = addressService.updateAddress(1L, requestDTO, "test@gmail.com");

        assertEquals("Bhopal", response.getCity());
    }

    @Test
    void updateAddress_notOwner() {
        User anotherUser = new User();
        ReflectionTestUtils.setField(anotherUser, "id", 2L);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(anotherUser));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        assertThrows(BadRequestException.class, () ->
                addressService.updateAddress(1L, requestDTO, "test@gmail.com"));
    }

    @Test
    void updateAddress_addressNotFound() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.updateAddress(1L, requestDTO, "test@gmail.com"));
    }

    /**
     * Delete address
     */

    @Test
    void deleteAddress_success() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        addressService.deleteAddress(1L, "test@gmail.com");

        verify(addressRepository, times(1)).delete(address);
    }

    @Test
    void deleteAddress_notOwner() {
        User anotherUser = new User();
        ReflectionTestUtils.setField(anotherUser, "id", 2L);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(anotherUser));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        assertThrows(BadRequestException.class, () ->
                addressService.deleteAddress(1L, "test@gmail.com"));
    }

    @Test
    void deleteAddress_addressNotFound() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                addressService.deleteAddress(1L, "test@gmail.com"));
    }
}
