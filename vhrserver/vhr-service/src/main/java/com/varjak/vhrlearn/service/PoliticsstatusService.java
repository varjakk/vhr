package com.varjak.vhrlearn.service;

import com.varjak.vhrlearn.mapper.PoliticsstatusMapper;
import com.varjak.vhrlearn.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsstatusService{
    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
