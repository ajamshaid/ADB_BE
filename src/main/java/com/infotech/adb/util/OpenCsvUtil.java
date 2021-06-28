package com.infotech.adb.util;

import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.model.entity.AccountDetail;
import com.infotech.adb.model.entity.BankNegativeList;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class OpenCsvUtil {

    private static String csvExtension = "csv";

    public static List<AccountDetail> parseAccountDetailsFile(InputStream is) throws CustomException {
        String[] CSV_HEADER = { "IBAN","Account Status","AuthPM(IM)","AuthPM(EX)" };
        Reader fileReader = null;

        CsvToBean<AccountDetail> csvToBean = null;

        List<AccountDetail> acctBean = new ArrayList<AccountDetail>();

        try {
            fileReader = new InputStreamReader(is);

            csvToBean = new CsvToBeanBuilder<AccountDetail>(fileReader)
                    .withType(AccountDetail.class)
               //     .withMappingStrategy(mappingStrategy).withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true).build();

            acctBean = csvToBean.parse();

        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }
        }

        return acctBean;
    }



    public static List<BankNegativeList> parseBankNegListFile(InputStream is) throws CustomException {
        String[] CSV_HEADER = { "Bank Code","Restricted Countries(IM)","Restricted Countries(EX)","Restricted Commodities(IM)","Restricted Commodities(EX)","Restricted Suppliers(IM)","Restricted Suppliers(EX)" };
        Reader fileReader = null;
        CsvToBean<BankNegativeList> csvToBean = null;
        List<BankNegativeList> bean = new ArrayList<BankNegativeList>();

        try {
            fileReader = new InputStreamReader(is);

            csvToBean = new CsvToBeanBuilder<BankNegativeList>(fileReader)
                    .withType(BankNegativeList.class)
                    //     .withMappingStrategy(mappingStrategy).withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true).build();

            bean = csvToBean.parse();

        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
                throw new CustomException(e.getMessage());
            }
        }

        return bean;
    }

    public static boolean isCSVFile(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1];

        if(!extension.equals(csvExtension)) {
            return false;
        }

        return true;
    }
/*
    public static void customersToCsv(Writer writer, List<Customer> customers) {
        String[] CSV_HEADER = { "id", "name", "address", "age" };

        StatefulBeanToCsv<Customer> beanToCsv = null;

        try {
            // write List of Objects
            ColumnPositionMappingStrategy<Customer> mappingStrategy =
                    new ColumnPositionMappingStrategy<Customer>();

            mappingStrategy.setType(Customer.class);
            mappingStrategy.setColumnMapping(CSV_HEADER);

            beanToCsv = new StatefulBeanToCsvBuilder<Customer>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(customers);
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
        }
    }
 */
}
