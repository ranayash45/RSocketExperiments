package com.mycomp.rsocketapp.collections;

import com.mycomp.rsocketapp.model.Organization;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Data
@Component
public class OrganizationList {

    List<Organization> organizations;
    public OrganizationList(){
        try {
            File file = new File(".\\src\\main\\resources\\organizations-10000.csv");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            organizations = bufferedReader
                    .lines()
                    .skip(1)
                    .map(s -> {
                        String[] parts = s.split(",");
                        return new Organization(Integer.parseInt(parts[0]),
                                parts[2],
                                parts[3],
                                parts[4],
                                Integer.parseInt(parts[parts.length-1]));
                    }).toList();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
