package com.anyungu.data.mapping.repos;

import com.anyungu.data.mapping.entitiles.Device;
import com.anyungu.data.mapping.entitiles.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository <Device, Integer> {

}
