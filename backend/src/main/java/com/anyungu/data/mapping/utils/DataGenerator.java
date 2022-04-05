package com.anyungu.data.mapping.utils;

import com.anyungu.data.mapping.entitiles.Device;
import com.anyungu.data.mapping.entitiles.DeviceData;
import com.anyungu.data.mapping.repos.DeviceDataRepository;
import com.anyungu.data.mapping.repos.DeviceRepository;
// import com.anyungu.data.mapping.v1.controllers.AmqpSender;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataGenerator {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceDataRepository deviceDataRepository;

    public void generateDevices() {

        deviceRepository.deleteAll();

        try {
            Faker faker = new Faker();
            List<Device> emptyDevice = new ArrayList<>();
            for (int i = 0; i <= 150; i++) {
                Device device = new Device();
                device.setBatteryLevel(faker.number().numberBetween(0, 100));
                device.setName(faker.bothify("DEVICE-###-#?"));
                device.setSerialNumber(faker.regexify("[A-Z1-9]{15}"));
                emptyDevice.add(device);
                // amqpSender.send("topic.sample.device", device);

            }
            deviceRepository.saveAll(emptyDevice);
        } catch (Exception e) {

        }

    }

    @Scheduled(fixedRate = 30000, initialDelay = 3000)
    private void generateData() {
        try {
            Faker faker = new Faker();
            List<Device> devices = deviceRepository.findAll();
            List<DeviceData> deviceData = new ArrayList<>();

            for (Device device : devices) {
                DeviceData currentDeviceData = new DeviceData();
                currentDeviceData.setDeviceId(device.getDeviceUuid());
                currentDeviceData.setHumidity((float) faker.number().randomDouble(3, 0, 100));
                currentDeviceData.setLatitude((float) faker.number().numberBetween(-1168590, -1446608) / 1000000);
                currentDeviceData.setLongitude((float) faker.number().numberBetween(36651077, 37103577) / 1000000);
                currentDeviceData.setTemp((float) faker.number().randomDouble(3, 0, 100));
                currentDeviceData.setTimestamp(LocalDateTime.now());
                deviceData.add(currentDeviceData);
            }

            deviceDataRepository.saveAll(deviceData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Scheduled(fixedRate = 10800000, initialDelay = 10800000)
    private void deleteData() {
        try {

            deviceDataRepository.deleteAll();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
