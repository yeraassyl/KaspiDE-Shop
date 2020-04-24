package com.company.record;

import com.company.util.Logger;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class RecordList {
    private final List<Record> records;
    static Logger LOG = Logger.getInstance();

    public RecordList(){
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record){
        records.add(record);
    }

    public List<Record> getRecords(){
        return records;
    }

    public Map<String, Long> groupByActressOscar(){
        return records.stream().collect(groupingBy(Record::getName, counting()));
    }

    public Map<String, Optional<Integer>> groupByActressAge(){
        return records.stream()
                .collect(groupingBy(Record::getName, mapping(Record::getAge, reducing((a, b) -> a >b ? a: b))));
    }

    public void olderThan(int age){
        LOG.log("Older then " + age);
        groupByActressAge().entrySet().stream()
                .filter(entry ->entry.getValue().orElse(0) > age)
                .forEach(entry -> LOG.log(entry.getKey()));
    }

    public void gotOscarMoreThan(int count){
        LOG.log("Got more oscar than " + count);
        groupByActressOscar().entrySet().stream()
                .filter(entry -> entry.getValue() > count)
                .forEach(entry -> LOG.log(entry.getKey()));
    }

    public void sortByNameAndMovie(){
        LOG.log("Sort by name the movie: ");
        records.stream()
                .sorted(Comparator.comparing(Record::getName))
                .sorted(Comparator.comparing(Record::getMovie))
                .collect(Collectors.toList())
                .forEach(record -> LOG.log(record.toString()));
    }

    public void topNames(int top){
        LOG.log("Top " + top + " names");
        records.stream()
                .map(record -> record.getName().split(" ")[0])
                .sorted(String::compareTo)
                .limit(top)
                .forEach(s -> LOG.log(s));
    }

    public void sliceByDecades(){
        records.stream()
                .sorted(Comparator.comparing(Record::getYear))
                .collect(groupingBy(Record::getDecade))
                .forEach((key, value) -> {
                    LOG.log("Decade #" + key);
                    value.forEach(val -> LOG.log(val.toString()));
                });
    }

    public Integer oscarCommonAge(){
        return records.stream()
                .collect(groupingBy(Record::getAge, counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)).map(Map.Entry::getKey).orElse(0);
    }

    public static RecordList loadRecords(String filePath){
        RecordList records = new RecordList();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int index = Integer.parseInt(values[0].trim());
                Year year = Year.of(Integer.parseInt(values[1].trim()));
                int age = Integer.parseInt(values[2].trim());
                String name = values[3].trim();
                String movie = values[4].trim();
                records.addRecord(new Record(index, year, age, name, movie));
            }
        } catch (IOException exception) {
            Logger.getInstance().log("Something went wrong" + exception);
        }
        return records;
    }

}
