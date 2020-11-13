package com.babaev.service;

import com.babaev.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
    private GroupDataGenerator(){

    }

    public static List<Group> generateGroups(){
        List<Group> groups = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 10; i++) {
            Group group = new Group();
            group.setId(i + 1);
            sb.append(getRandomCharacter());
            sb.append(getRandomCharacter());
            sb.append('-');
            sb.append(getRandomDigit());
            sb.append(getRandomDigit());
            group.setName(sb.toString());
            groups.add(group);
            sb.setLength(0);
        }

        return groups;
    }
    private static char getRandomCharacter() {
        return (char) ('A' + Math.random() * ('Z' - 'A' + 1));
    }

    private static int getRandomDigit(){
        return (int)(Math.random() * 10);
    }

}
