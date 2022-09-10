package com.techvg.vks.common;

import com.techvg.vks.society.model.SocietyConfigurationDto;
import com.techvg.vks.society.service.SocietyConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SocietyConfig implements CommandLineRunner  {

    private final static Map<String, Double> societyConfigMap = new HashMap<String, Double>();
    private final SocietyConfigurationService service;

    @Override
    public void run(String... args) throws Exception {
        loadSocietyConfig();
    }

    private void loadSocietyConfig(){
        List<SocietyConfigurationDto> dtoList = service.getAll();
        if(!dtoList.isEmpty()){
            dtoList.forEach(societyConf ->{
                societyConfigMap.put(societyConf.getConfigName(),societyConf.getValue());
            });
        }
        System.out.println("\n\n societyConfigMap  " + societyConfigMap.size());
    }

    public static Double getValue(String key){
        Double value = null;
        value = societyConfigMap.get(key);
        return value;
    }
}
