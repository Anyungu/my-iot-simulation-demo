package com.anyungu.data.mapping.entitiles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.util.UUID;

@Entity(name = "device")
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "device_uuid", updatable = false, nullable = false, unique = true)
    private UUID deviceUuid;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "battery_level", nullable = false)
    private Integer batteryLevel;


}
