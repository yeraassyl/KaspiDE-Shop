package com.company;

import com.company.record.Record;
import com.company.record.RecordList;
import com.company.util.Logger;

public class Main {

    public static void main(String[] args) {
        Logger.setOutputTarget(Logger.Target.FILE);
        RecordList recordList = RecordList.loadRecords("oscar_age_female.csv");
        // TASK 1
        recordList.olderThan(35);
        // TASK 2
        recordList.gotOscarMoreThan(2);
        // TASK 3
        recordList.sortByNameAndMovie();
        // TASK 4
        recordList.topNames(5);
        // TASK 5
        recordList.sliceByDecades();
        // TASK 6
        int age = recordList.oscarCommonAge();
        Logger.getInstance().log("Most common age for Oscar: " + age);
    }
}
