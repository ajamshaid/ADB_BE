package com.infotech.adb.util;

import com.infotech.adb.model.entity.AccountDetail;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TestMain {

    public static void main(String args[]) throws CsvException, FileNotFoundException {

      //  System.out.println(new BCryptPasswordEncoder().encode("admin1"));

        String fileName = "D:\\InfoTech\\ADBroker\\SilkBank\\TP Upload Sheet.csv";
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> System.out.println("row---"+Arrays.toString(x)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        List<AccountDetail> beans =
                new CsvToBeanBuilder<AccountDetail>(new FileReader(fileName))
                .withType(AccountDetail.class)
                .build().parse();

        beans.forEach(System.out::println);


    }
}
