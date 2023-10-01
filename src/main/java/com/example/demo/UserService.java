package com.example.demo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {

    private ArrayList<User> AllUsers = new ArrayList<>();

    public ArrayList<User> getAllUsers ()
    {
        return AllUsers;
    }

    public User getUserById (String id)
    {
        for (User u : AllUsers)
        {
            if (u.getUserId().equals(id))
            {
                return u;
            }
        }
        return null;
    }

    public void createNewUser (User user)
    {
        AllUsers.add(user);
    }

    public void updateUser (User user)
    {
        User user1 = getUserById(user.getUserId());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setBirthDate(user.getBirthDate());
        user1.setPhoneNum(user.getPhoneNum());
    }

    public void PartialUpdateUser (User user, Map<String, Object> updates)
    {
        for (Map.Entry<String, Object> entry : updates.entrySet())
        {
            String field = entry.getKey();
            Object value = entry.getValue();

            switch (field)
            {
                case "FirstName":
                    user.setFirstName((String) value);
                    break;
                case "LastName":
                    user.setLastName((String) value);
                    break;
                case "BirthDate":
                    user.setBirthDate((Date) value);
                    break;
                case "PhoneNum":
                    user.setPhoneNum((String) value);
                    break;
            }
        }
    }

    public void DeleteUser (User user)
    {
        for (User u : AllUsers)
        {
            if (u.getUserId().equals(user.getUserId()))
            {
                AllUsers.remove(u);
                break;
            }
        }
    }

    public ArrayList<User> FilterOfUser (Date startDate, Date endDate)
    {
        ArrayList<User> newUsers = new ArrayList<>();

        if (startDate.before(endDate))
        {
            for (User u : AllUsers)
            {
                if (u.getBirthDate().after(startDate) && u.getBirthDate().before(endDate))
                {
                    newUsers.add(u);
                }
            }

            return newUsers;
        }
        else
        {
            return null;
        }
    }

    //перевірка телофана на відповідність формату
    public boolean CheckPhone (String newUser_PhoneNum)
    {
        if (newUser_PhoneNum != null && newUser_PhoneNum.matches("\\+380.*") && newUser_PhoneNum.length() == 13)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //перевірка користувача на відповідність віковому ліміту
    public boolean CheckBirthDate (Date newUser_BirthDate, int ageLimit)
    {
        LocalDate birthDate = newUser_BirthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate limitDate = LocalDate.now().minusYears(ageLimit);

        if (birthDate.isBefore(limitDate))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
