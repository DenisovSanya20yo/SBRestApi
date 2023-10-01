package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {

    private final UserConfig userConfig;
    private final UserService userService;

    @Autowired
    public AppController(UserConfig userConfig, UserService userService)
    {
        this.userConfig = userConfig;
        this.userService = userService;
    }

    //отримання користувача за заданим діапазоном
    @GetMapping("/getuser")
    public ResponseEntity<ArrayList<User>> getUsers (@RequestParam("startDate")@DateTimeFormat(pattern = "dd.MM.yyyy")
                                                       Date startDate,
                                                       @RequestParam("endDate")@DateTimeFormat(pattern = "dd.MM.yyyy")
                                                       Date endDate)
    {
        ArrayList<User> filteredUser = userService.FilterOfUser(startDate, endDate);

        if (filteredUser == null)
        {
            return ResponseEntity.badRequest().body(filteredUser);
        }
        else
        {
            return ResponseEntity.ok(filteredUser);
        }
    }

    //створення користувача
    @PostMapping("/register")
    public ResponseEntity<String> createUser (@RequestBody User user)
    {
        //встановлення вікового ліміту
        int ageLimit = userConfig.getUserAgeLimit();

        //валідація данних
        if (userService.CheckPhone(user.getPhoneNum()) == true && userService.CheckBirthDate(user.getBirthDate(), ageLimit) == true)
        {
            userService.createNewUser(user);
            return ResponseEntity.ok("Successful request");
        }
        else
        {
            return ResponseEntity.badRequest().body("Invalid data");
        }
    }

    //оновлення даних користувача
    @PutMapping("/{UserId}")
    public ResponseEntity<String> updateUser (@PathVariable String UserId, @RequestBody User updateUser)
    {
        //отримання користувача за id
        User userToUpdate = userService.getUserById(UserId);
        //отримання вікового обмеження з файлу properties
        int ageLimit = userConfig.getUserAgeLimit();

        if (userToUpdate != null)
        {
            //оновлення даних користувача
            userToUpdate.setFirstName(updateUser.getFirstName());
            userToUpdate.setLastName(updateUser.getLastName());
            if (userService.CheckBirthDate(updateUser.getBirthDate(), ageLimit) == true)
            {
                userToUpdate.setBirthDate(updateUser.getBirthDate());
            }
            else
            {
                return ResponseEntity.badRequest().body("Invalid data");
            }

            if (userService.CheckPhone(updateUser.getPhoneNum()) == true)
            {
                userToUpdate.setPhoneNum(updateUser.getPhoneNum());
            }
            else
            {
                return ResponseEntity.badRequest().body("Invalid data");
            }

            userService.updateUser(userToUpdate);

            return ResponseEntity.ok("Successful update");
        }
        else
        {
            return ResponseEntity.badRequest().body("Invalid data");
        }
    }

    //часткове оновлення даних користувача
    @PatchMapping("/patch/{UserId}")
    public ResponseEntity<String> patchUserUpdate (@PathVariable String UserId, @RequestBody Map<String, Object> updates)
    {
        User userToUpdate = userService.getUserById(UserId);

        if (userToUpdate != null)
        {
            userService.PartialUpdateUser(userToUpdate, updates);
            userService.updateUser(userToUpdate);

            return ResponseEntity.ok("Successful patch update");
        }
        else
        {
            return ResponseEntity.badRequest().body("Invalid data");
        }
    }

    //видалення користувача
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser (@RequestBody User deleteUser)
    {
        if (deleteUser != null)
        {
            userService.DeleteUser(deleteUser);

            return ResponseEntity.ok("Succesful removing");
        }
        else
        {
            return ResponseEntity.badRequest().body("Successful removing");
        }
    }
}