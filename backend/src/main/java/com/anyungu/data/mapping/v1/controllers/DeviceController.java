package com.anyungu.data.mapping.v1.controllers;

import com.anyungu.data.mapping.entitiles.Device;
import com.anyungu.data.mapping.entitiles.DeviceData;
import com.anyungu.data.mapping.v1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping(path = "/single",  produces = "application/json")
    public List<Device> getDevices() {
        return deviceService.getDevices();
    }
}
