package com.anyungu.data.mapping.v1.services;

import com.anyungu.data.mapping.entitiles.DeviceData;
import com.anyungu.data.mapping.repos.DeviceDataRepository;
import com.anyungu.data.mapping.repos.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceDataService {

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    public List<DeviceData> getDevicesData() {

        Page<DeviceData> page = deviceDataRepository.findAll(
                PageRequest.of(0, 1500, Direction.DESC, "timestamp"));
        return page.getContent();
    }

    public List<DeviceData> getDeviceData(Integer id) {
        return deviceDataRepository.findByDeviceId(id);
    }
}
