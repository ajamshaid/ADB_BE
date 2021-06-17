package com.infotech.adb.jms;

import com.infotech.adb.model.entity.BDA;
import com.infotech.adb.model.entity.FinancialTransaction;
import com.infotech.adb.model.entity.ItemInformation;
import com.infotech.adb.model.entity.PaymentInformation;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class MQMessageParser {
    public FinancialTransaction parseAndBuildFTExport(String data) {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setType(AppConstants.TYPE_EXPORT);
        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MqUtility.DELIMETER_DATA);
            ft.setNtn(ftDetailsAry[0]);

            ft.setName(ftDetailsAry[1]);
            ft.setIban(ftDetailsAry[2]);
            ft.setModeOfPayment(ftDetailsAry[3]);
            ft.setFinInsUniqueNumber(ftDetailsAry[4]);

            PaymentInformation pi = new PaymentInformation();
            pi.setDeliveryTerm(ftDetailsAry[5]);
            pi.setFinancialInstrumentCurrency(ftDetailsAry[6]);

            if (AppUtility.isBigDecimal(ftDetailsAry[7])) {
                pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[7]));
            }

            if (AppUtility.isDate(ftDetailsAry[8])) {
                ft.setFinInsExpiryDate(new Date(ftDetailsAry[8]));
            }

            String[] hsCodeAry = ftDetailsAry.length >= 10 ? ftDetailsAry[9].split(MqUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};
            String[] qtyAry = ftDetailsAry.length >= 11 ? ftDetailsAry[10].split(MqUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};
            String[] descAry = ftDetailsAry.length >= 12 ? ftDetailsAry[11].split(MqUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};


            Set<ItemInformation> itemInformationSet = new HashSet<>(hsCodeAry.length);
            for (int index = 0; index < hsCodeAry.length; index++) {

                ItemInformation itemInfo = new ItemInformation();
                itemInfo.setHsCode(hsCodeAry[index]);

                if (qtyAry.length > index && AppUtility.isBigDecimal(qtyAry[index])) {
                    itemInfo.setQuantity(new BigDecimal(qtyAry[index]));
                }

                if (descAry.length > index) {
                    itemInfo.setGoodsDescription(descAry[index].length() > 999 ? descAry[index].substring(0, 996) + "..." : descAry[index]);
                }

                itemInfo.setFinancialTransaction(ft);

                itemInformationSet.add(itemInfo);

            }
            pi.setFinancialTransaction(ft);
            ft.setPaymentInformation(pi);
            ft.setItemInformationSet(itemInformationSet);

            System.out.println("FT-<>" + ft);
        }
        return ft;
    }

    public FinancialTransaction parseAndBuildFTImport(String data) {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setType(AppConstants.TYPE_IMPORT);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MqUtility.DELIMETER_DATA);
            ft.setNtn(ftDetailsAry[0]);

            ft.setName(ftDetailsAry[1]);
            ft.setIban(ftDetailsAry[2]);
            ft.setModeOfPayment(ftDetailsAry[3]);
            ft.setFinInsUniqueNumber(ftDetailsAry[4]);

            PaymentInformation pi = new PaymentInformation();
            pi.setBeneficiaryName(ftDetailsAry[5]);
            pi.setBeneficiaryAddress(ftDetailsAry[6]);
            pi.setPortOfShipment(ftDetailsAry[7]);
            if (AppUtility.isBigDecimal(ftDetailsAry[8])) {
                pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[8]));
            }
            pi.setFinancialInstrumentCurrency(ftDetailsAry[9]);
            if (AppUtility.isBigDecimal(ftDetailsAry[10])) {
                pi.setExchangeRate(new BigDecimal(ftDetailsAry[10]));
            }
            pi.setLcContractNo(ftDetailsAry[11]);


            String[] hsCodeAry = ftDetailsAry[12].split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] qtyAry = ftDetailsAry[13].split(MqUtility.DELIMETER_MULTIPLE_DATA);
            String[] descAry = ftDetailsAry[14].split(MqUtility.DELIMETER_MULTIPLE_DATA);

            Set<ItemInformation> itemInformationSet = new HashSet<>(hsCodeAry.length);
            for (int index = 0; index < hsCodeAry.length; index++) {

                ItemInformation itemInfo = new ItemInformation();
                itemInfo.setHsCode(hsCodeAry[index]);

                if (qtyAry.length > index && AppUtility.isBigDecimal(qtyAry[index])) {
                    itemInfo.setQuantity(new BigDecimal(qtyAry[index]));
                }

                if (descAry.length > index) {
                    itemInfo.setGoodsDescription(descAry[index].length() > 999 ? descAry[index].substring(0, 996) + "..." : descAry[index]);
                }

                itemInfo.setFinancialTransaction(ft);

                itemInformationSet.add(itemInfo);

            }
            pi.setFinancialTransaction(ft);
            ft.setPaymentInformation(pi);
            ft.setItemInformationSet(itemInformationSet);

            System.out.println("FT-<>" + ft);
        }
        return ft;
    }

    public BDA parseAndBuildBDAInfoImport(String data) {
        //Sharing of BDA Information
        BDA bda = new BDA();
     //   ft.setType(AppConstants.TYPE_IMPORT);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MqUtility.DELIMETER_DATA);
            bda.setImporterNtn(ftDetailsAry[0]);
            bda.setImporterName(ftDetailsAry[1]);
            bda.setIban(ftDetailsAry[2]);
            bda.setFinInsUniqueNumber(ftDetailsAry[3]);
            bda.setTotalBdaAmountFcy(AppUtility.isBigDecimal(ftDetailsAry[4]) ? new BigDecimal(ftDetailsAry[4]): null);
            bda.setTotalBdaAmountCurrency(ftDetailsAry[5]);
            bda.setNetBdaAmountFcy(AppUtility.isBigDecimal(ftDetailsAry[6]) ? new BigDecimal(ftDetailsAry[6]): null);
            bda.setCurrencyFcy(ftDetailsAry[7]);
            bda.setModeOfPayment(ftDetailsAry[8]);
           // bda.setExchangeRateFcy(ftDetailsAry[10]);
            bda.setExchangeRateFcy(AppUtility.isBigDecimal(ftDetailsAry[10]) ? new BigDecimal(ftDetailsAry[10]): null);
            bda.setBdaAmountFcy(AppUtility.isBigDecimal(ftDetailsAry[11]) ? new BigDecimal(ftDetailsAry[11]): null);
            bda.setNetBdaAmountPkr(AppUtility.isBigDecimal(ftDetailsAry[12]) ? new BigDecimal(ftDetailsAry[12]): null);
            bda.setBdaDocumentRefNumber(ftDetailsAry[13]);
            bda.setOtherChargesPkr(AppUtility.isBigDecimal(ftDetailsAry[14]) ? new BigDecimal(ftDetailsAry[14]): null);


            System.out.println("BDA-<>" + bda);
        }
        return bda;
    }
}