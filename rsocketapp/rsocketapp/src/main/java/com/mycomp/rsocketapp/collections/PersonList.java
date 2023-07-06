package com.mycomp.rsocketapp.collections;

import com.mycomp.rsocketapp.model.Person;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.CharBuffer;
import java.util.List;

@Component
@Data
public class PersonList {
    List<Person> personList;
    PersonList(){
            personList = List.of(
              new Person("Yash","Rana","Surat","Gujarat"),
                    new Person("Jithendra","Ramesh","Bengaluru","Karnataka"),
                    new Person("Ankur","Mittal","Delhi","Central"),
                    new Person("Anubhav","Mukho","Delhi NCR","Central"),
                    new Person("Priya","Singh","Varansi","UP")
            );
    }
}
