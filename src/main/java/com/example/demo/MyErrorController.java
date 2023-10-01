package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/error")
public class MyErrorController implements ErrorController
{
    public ResponseEntity<String> handleError ()
    {
        String ErrorMassage = "NOT found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMassage);
    }
}
