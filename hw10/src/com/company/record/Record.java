package com.company.record;

import java.time.Year;
import java.util.Objects;

public class Record {
    private int index;
    private Year year;
    private int age;
    private String name;
    private String movie;

    public Record(int index, Year year, int age, String name, String movie) {
        this.index = index;
        this.year = year;
        this.age = age;
        this.name = name;
        this.movie = movie;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Year getYear() {
        return year;
    }

    public int getDecade(){
        return (year.getValue() % 10) + 1;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }


    @Override
    public String toString() {
        return "Record{" +
                "index=" + index +
                ", year=" + year +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", movie='" + movie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return index == record.index &&
                age == record.age &&
                year.equals(record.year) &&
                name.equals(record.name) &&
                movie.equals(record.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, year, age, name, movie);
    }


}
