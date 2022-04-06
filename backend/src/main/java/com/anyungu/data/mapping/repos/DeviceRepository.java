package com.anyungu.data.mapping.repos;

import com.anyungu.data.mapping.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository <Device, Integer> {

}
