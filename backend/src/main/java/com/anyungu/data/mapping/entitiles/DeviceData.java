package com.anyungu.data.mapping.entitiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DeviceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    private Float latitude;
    private Float longitude;
    private Float temp;
    private Float humidity;
    private LocalDateTime timestamp;
    private Integer deviceId;
    
}
