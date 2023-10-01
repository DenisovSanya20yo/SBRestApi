package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testCreateNewUser() {
        User user = new User();
        user.setUserId("user123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(new Date());
        user.setPhoneNum("+3809543793");

        userService.createNewUser(user);

        ArrayList<User> allUsers = userService.getAllUsers();
        assertTrue(allUsers.contains(user));
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserId("user123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(new Date());
        user.setPhoneNum("+3809543793");

        userService.createNewUser(user);

        User updatedUser = new User();
        updatedUser.setUserId("user123");
        updatedUser.setFirstName("UpdatedName");
        updatedUser.setLastName("UpdatedLastName");
        updatedUser.setBirthDate(new Date());
        updatedUser.setPhoneNum("+3809543794");

        userService.updateUser(updatedUser);

        User retrievedUser = userService.getUserById("user123");
        assertEquals("UpdatedName", retrievedUser.getFirstName());
        assertEquals("UpdatedLastName", retrievedUser.getLastName());
        assertEquals("+3809543794", retrievedUser.getPhoneNum());
    }

    @Test
    void testPartialUpdateUser() {
        User user = new User();
        user.setUserId("user123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(new Date());
        user.setPhoneNum("+3809543793");

        userService.createNewUser(user);

        Map<String, Object> updates = new HashMap<>();
        updates.put("FirstName", "UpdatedName");
        updates.put("PhoneNum", "+3809543794");

        userService.PartialUpdateUser(user, updates);

        User retrievedUser = userService.getUserById("user123");
        assertEquals("UpdatedName", retrievedUser.getFirstName());
        assertEquals("+3809543794", retrievedUser.getPhoneNum());
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setUserId("user123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(new Date());
        user.setPhoneNum("+3809543793");

        userService.createNewUser(user);

        userService.DeleteUser(user);

        ArrayList<User> allUsers = userService.getAllUsers();
        assertFalse(allUsers.contains(user));
    }

    @Test
    void testFilterOfUser() {
        User user1 = new User();
        user1.setUserId("user1");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setBirthDate(new Date());
        user1.setPhoneNum("+3809543793");

        User user2 = new User();
        user2.setUserId("user2");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -30);
        Date thirtyYearsAgo = calendar.getTime();
        user2.setBirthDate(thirtyYearsAgo);
        user2.setPhoneNum("+3809543794");

        userService.createNewUser(user1);
        userService.createNewUser(user2);

        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.add(Calendar.YEAR, -40);
        Date startDate = startDateCalendar.getTime();

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.add(Calendar.YEAR, -20);
        Date endDate = endDateCalendar.getTime();

        ArrayList<User> filteredUsers = userService.FilterOfUser(startDate, endDate);

        assertEquals(1, filteredUsers.size());
        assertTrue(filteredUsers.contains(user2));
    }

    @Test
    void testCheckPhoneValid() {
        String validPhone = "+380954379309";
        assertTrue(userService.CheckPhone(validPhone));
    }

    @Test
    void testCheckPhoneInvalid() {
        String invalidPhone = "12345";
        assertFalse(userService.CheckPhone(invalidPhone));
    }

    @Test
    void testCheckBirthDateValid() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -30);
        Date thirtyYearsAgo = calendar.getTime();
        int ageLimit = 18;

        assertTrue(userService.CheckBirthDate(thirtyYearsAgo, ageLimit));
    }

    @Test
    void testCheckBirthDateInvalid() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date tenYearsAgo = calendar.getTime();
        int ageLimit = 18;

        assertFalse(userService.CheckBirthDate(tenYearsAgo, ageLimit));
    }
}