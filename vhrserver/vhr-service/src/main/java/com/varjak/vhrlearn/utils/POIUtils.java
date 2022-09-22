package com.varjak.vhrlearn.utils;

import com.varjak.vhrlearn.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class POIUtils {
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        return new ResponseEntity<byte[]>(byteOutputStream.toByteArray(), HttpStatus.CREATED);
    }
}
