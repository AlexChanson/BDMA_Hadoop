package com.alexscode.bdma.hadoop.app;

import com.alexscode.bdma.hadoop.err.StudentNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class StudentController {
    @RequestMapping(value = "/aiwsbu/v1/students/{id}/transcripts/{year}", method = RequestMethod.GET)
    public Object task1(@PathVariable("id") Long id, @PathVariable("year") int year) throws StudentNotFoundException {

        //TODO this is a placeholder for demonstration purposes
        HashMap<String, Object> test = new HashMap<>();
        if (id != 42)
            throw new StudentNotFoundException("no student " + id);
        test.put("sucess", 1);
        test.put("id", id);
        test.put("year", year);
        return test;
    }
}
