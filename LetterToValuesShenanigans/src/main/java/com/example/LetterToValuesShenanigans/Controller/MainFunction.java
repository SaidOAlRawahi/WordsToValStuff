package com.example.LetterToValuesShenanigans.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//http://localhost:8080/get_values_for?word_to_measure=ENTER_WORD
@RestController
public class MainFunction {
    @GetMapping("/get_values_for")

    public Integer[] doDaniyalsShenanigans(@RequestParam("word_to_measure") String str){
        List<Integer> collectedValues = new ArrayList<Integer>();
        if (isValidSeq(str)){
            boolean isNewNumber = true;
            boolean isValAfterZ = false;
            int totalZValues = 0;
            int rountLength = 0;
            int roundItr = 0;
            int roundTotal = 0;
            int charVal;
            for(int i=0; i<str.length(); i++){
                charVal = (int)str.charAt(i)-96;
                if (str.charAt(i) == '_'){
                    charVal = 0;
                    if (isValAfterZ){
                        charVal += totalZValues;
                        totalZValues = 0;
                        isValAfterZ = false;
                    }
                }
                else if (str.charAt(i) == 'z'){
                    isValAfterZ = true;
                    totalZValues += charVal;
                    continue;
                }
                else{
                    if (isValAfterZ){
                        charVal += totalZValues;
                        totalZValues = 0;
                        isValAfterZ = false;
                    }
                }
                if(!isNewNumber){
                    roundTotal+=charVal;
                    roundItr++;
                }
                else{
                    rountLength = charVal;
                    roundTotal = 0;
                    roundItr = 0;
                    isNewNumber = false;
                }
                if(roundItr == rountLength){
                    collectedValues.add(roundTotal);
                    isNewNumber = true;
                }
                if(i == str.length()-1 && roundItr != rountLength){
                    collectedValues.add(0);
                }
            }
        }
        return collectedValues.toArray(new Integer[collectedValues.size()]);
    }

    public boolean isValidSeq(String str){
        String pattern = "^[a-z_]+$";
        return Pattern.matches(pattern, str);
    }
}
