package com.anyungu.data.mapping.v1.services;

import com.anyungu.data.mapping.entities.Device;
import com.anyungu.data.mapping.repos.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getDevices() {

       return  deviceRepository.findAll();

    }
}
