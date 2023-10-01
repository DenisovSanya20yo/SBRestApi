package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class User
{
    @JsonProperty("UserId")
    private String UserId;
    @JsonProperty("FirsName")
    private String FirstName;
    @JsonProperty("LastName")
    private String LastName;
    @JsonProperty("BirthDate")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date BirthDate;
    @JsonProperty("PhoneNum")
    private String PhoneNum;

    public String getUserId ()
    {
        return UserId;
    }
    public void setUserId (String UserId)
    {
        this.UserId = UserId;
    }

    public String getFirstName ()
    {
        return FirstName;
    }
    public void setFirstName (String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName ()
    {
        return LastName;
    }
    public void setLastName (String LastName)
    {
        this.LastName = LastName;
    }

    public Date getBirthDate ()
    {
        return BirthDate;
    }
    public void setBirthDate (Date BirthDate)
    {
        this.BirthDate = BirthDate;
    }

    public String getPhoneNum ()
    {
        return PhoneNum;
    }
    public void setPhoneNum (String PhoneNum)
    {
        this.PhoneNum = PhoneNum;
    }
}
