package com.anyungu.data.mapping.v1.controllers;

import com.anyungu.data.mapping.entities.Device;
import com.anyungu.data.mapping.v1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/device")
@CrossOrigin(origins = "*", allowedHeaders ="*")
public class DeviceController extends MasterController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(path = "/all",  produces = "application/json")
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }
}
