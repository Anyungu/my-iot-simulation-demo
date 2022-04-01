package com.anyungu.data.mapping.v1.controllers;

import com.anyungu.data.mapping.entitiles.DeviceData;
import com.anyungu.data.mapping.v1.services.DeviceDataService;
import com.anyungu.data.mapping.v1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("device-data")
public class DeviceDataController {

    @Autowired
    DeviceDataService deviceDataService;

    @GetMapping(path = "/all",  produces = "application/json")
    public List<DeviceData> getDevicesData() {
        return deviceDataService.getDevicesData();
    }

    @GetMapping(path = "/single",  produces = "application/json")
    public List<DeviceData> getSingleDevicesData(@RequestParam("id") int id) {
        return deviceDataService.getDeviceData(id);
    }
}
