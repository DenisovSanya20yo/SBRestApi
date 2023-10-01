package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;
    UserService userService;

    @Test
    public void testCreateUserWithValidData() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date validBirthDate = dateFormat.parse("11.07.2000");

        String requestBody = "{ \"UserId\": \"user123\", \"FirstName\": \"John\", \"LastName\": \"Doe\", \"BirthDate\": \"11.07.2000\", \"PhoneNum\": \"+380954379309\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateUserWithValidData() throws Exception {
        User userToUpdate = new User();
        userToUpdate.setUserId("user123");
        userToUpdate.setFirstName("John");
        userToUpdate.setLastName("Doe");
        userToUpdate.setBirthDate(new Date());
        userToUpdate.setPhoneNum("+3809543793");

        userService.createNewUser(userToUpdate);

        String requestBody = "{ \"UserId\": \"user123\", \"FirstName\": \"UpdatedName\", \"LastName\": \"UpdatedLastName\", \"BirthDate\": \"11.07.2000\", \"PhoneNum\": \"+3809543793\" }";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/{UserId}", "user123")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testPartialUpdateUserWithValidData() throws Exception {
        User userToUpdate = new User();
        userToUpdate.setUserId("user123");
        userToUpdate.setFirstName("John");
        userToUpdate.setLastName("Doe");
        userToUpdate.setBirthDate(new Date());
        userToUpdate.setPhoneNum("+380954379309");

        String requestBody = "{ \"FirstName\": \"UpdatedName\" }";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/patch/{UserId}", "user123")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUserWithValidData() throws Exception {
        User userToDelete = new User();
        userToDelete.setFirstName("John");
        userToDelete.setLastName("Doe");
        userToDelete.setBirthDate(new Date());
        userToDelete.setPhoneNum("+3809543793");

        String requestBody = "{ \"FirstName\": \"John\", \"LastName\": \"Doe\", \"BirthDate\": \"11.07.2000\", \"PhoneNum\": \"+3809543793\" }";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUsersWithValidData() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date startDate = dateFormat.parse("01.01.1990");
        Date endDate = dateFormat.parse("31.12.2000");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/getuser")
                        .param("startDate", "01.01.1990")
                        .param("endDate", "31.12.2000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}