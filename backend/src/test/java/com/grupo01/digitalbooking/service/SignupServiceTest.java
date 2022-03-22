package com.grupo01.digitalbooking.service;

import com.grupo01.digitalbooking.domain.Role;
import com.grupo01.digitalbooking.domain.User;
import com.grupo01.digitalbooking.dto.NewUserDTO;
import com.grupo01.digitalbooking.dto.UserDTO;
import com.grupo01.digitalbooking.repository.RoleRepository;
import com.grupo01.digitalbooking.repository.UserRepository;
import com.grupo01.digitalbooking.service.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SignupServiceTest {

    @InjectMocks
    private SignupService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private NewUserDTO testInput;
    private String existingEmail;
    private String nonExistingEmail;

    @BeforeEach
    private void init(){
        this.testInput = new NewUserDTO();
        this.existingEmail = "existing";
        this.nonExistingEmail = "nonExisting";
        User saveReturn = new User(1L);
        saveReturn.setRole(new Role(1L));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encrypted");
        when(userRepository.findByEmail(existingEmail)).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());
        when(roleRepository.getById(1L)).thenReturn(new Role(1L,"USER"));
        when(userRepository.save(any(User.class))).thenReturn(saveReturn);
    }

    @Test
    public void createNewUserShouldThrowExceptionWhenEmailExists(){
        testInput.setFirstName("Matheus");
        testInput.setLastName("Andrade");
        testInput.setPassword("aA1@aa!2a");
        testInput.setEmail(existingEmail);
        assertThrows(BadRequestException.class,()->service.createNewUser(testInput));
    }

    @Test
    public void createNewUserShouldReturnUserDTOWhenValidInformation(){
        testInput.setFirstName("Matheus");
        testInput.setLastName("Andrade");
        testInput.setEmail(nonExistingEmail);
        testInput.setPassword("aA1@aa!2a");
        UserDTO result = service.createNewUser(testInput);
        assertEquals(1L,result.getId());
    }

    @Test
    public void setDefaultUserInfoShouldUpdateEntityInfo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = new User();
        user.setPassword("aaaaaa");
        assertFalse(user.isAccountNonExpired());
        assertFalse(user.isAccountNonLocked());
        assertFalse(user.isEnabled());
        assertFalse(user.isCredentialsNonExpired());

        Method setDefaultUserInfo = SignupService.class.getDeclaredMethod("setDefaultUserInfo", User.class);
        setDefaultUserInfo.setAccessible(true);
        setDefaultUserInfo.invoke(service,user);
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isEnabled());
        assertTrue(user.isCredentialsNonExpired());
        assertEquals("encrypted",user.getPassword());
    }

    @Test
    public void validateInitialInformationShouldNotThrowExceptionWhenValidInformation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method validateInitialInformation = SignupService.class.getDeclaredMethod("validateInitialInformation", NewUserDTO.class);
        validateInitialInformation.setAccessible(true);
        testInput.setFirstName("Matheus");
        testInput.setLastName("Andrade");
        testInput.setEmail("chucreandrade@gmail.com");
        testInput.setPassword("aaaaa");
        assertDoesNotThrow(()->validateInitialInformation.invoke(service,testInput));

    }

    @Test
    public void validateInitialInformationShouldThrowExceptionWhenValidInformation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method validateInitialInformation = SignupService.class.getDeclaredMethod("validateInitialInformation", NewUserDTO.class);
        validateInitialInformation.setAccessible(true);

        testInput.setFirstName(null);
        testInput.setLastName("Andrade");
        testInput.setEmail("chucreandrade@gmail.com");
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });
        testInput.setFirstName(" ");
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });
        testInput.setFirstName("Matheus");
        testInput.setLastName(null);
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });
        testInput.setLastName(" ");
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });
        testInput.setLastName("Andrade");
        testInput.setEmail(null);
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });
        testInput.setEmail(" ");
        assertThrows(BadRequestException.class,()->{
            try{
                validateInitialInformation.invoke(service,testInput);
            }catch(InvocationTargetException ex){
                throw ex.getCause();
            }
        });


    }



}