package com.varjak.vhrlearn.service;

import com.varjak.vhrlearn.mapper.NationMapper;
import com.varjak.vhrlearn.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;

    public List<Nation> getAllNations(){
        return nationMapper.getAllNations();
    }
}
