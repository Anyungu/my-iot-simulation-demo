package com.anyungu.data.mapping.configs;

import com.anyungu.data.mapping.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;



@Component
public class ApplicationStartUpListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DataGenerator dataGenerator;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        this.dataGenerator.generateDevices();

    }
}
