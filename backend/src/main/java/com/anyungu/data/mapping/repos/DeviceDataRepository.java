package com.anyungu.data.mapping.repos;

import com.anyungu.data.mapping.entities.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceDataRepository  extends JpaRepository <DeviceData, Integer> {

    List<DeviceData> findByDeviceId(Integer id);
}
