package com.infotech.adb.silkbank.jms;

import com.infotech.adb.model.entity.*;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Log4j2
@Component
public class MQMessageParser {
    public FinancialTransaction parseAndBuildFTExport(String data) {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setType(AppConstants.TYPE_EXPORT);
        ft.setStatus(AppConstants.RecordStatuses.CREATED_BY_MQ);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);
            ft.setNtn(ftDetailsAry[0]);

            ft.setName(ftDetailsAry[1]);
            ft.setIban(ftDetailsAry[2]);
            ft.setModeOfPayment(ftDetailsAry[3]);
        //    ft.setFinInsUniqueNumber(ftDetailsAry[4]);
            ft.setFinInsUniqueNumberCore(ftDetailsAry[4]);

            PaymentInformation pi = new PaymentInformation();
            pi.setDeliveryTerm(ftDetailsAry[5]);
            pi.setFinancialInstrumentCurrency(ftDetailsAry[6]);

            if (AppUtility.isBigDecimal(ftDetailsAry[7])) {
                pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[7]));
            }

            if (AppUtility.isDate(ftDetailsAry[8])) {
                pi.setFinancialInstrumenExpiryDate(new Date(ftDetailsAry[8]));
            }

            String[] hsCodeAry = ftDetailsAry.length >= 10 ? ftDetailsAry[9].split(MQUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};
            String[] qtyAry = ftDetailsAry.length >= 11 ? ftDetailsAry[10].split(MQUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};
            String[] descAry = ftDetailsAry.length >= 12 ? ftDetailsAry[11].split(MQUtility.DELIMETER_MULTIPLE_DATA) : new String[]{};


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

            log.info("\n-----FT-<>" + ft);
        }
        return ft;
    }


    /**
     * Header:
     *  MSG.TYPE!UNIQUE.ID!
     *  Importer NTN|Importer Name|Importer IBAN|Payment Mode|Financial Instrument Unique No|Beneficiary Name
     *  |Beneficiary Address|Port of Shipment|Instrument Value|Instrument Currency|Exchange Rate|Financial Instrument Unique No
     *  |HS Code|Quantity|Goods Description
     *  |Beneficiary Country|Exporter Name|Exporter Address|Exporter Country|Delivery Terms|Last Shipment Date
     *  |Expiry Date
     * @param data
     * @return
     */
    public FinancialTransaction parseAndBuildFTImport(String data) {
        FinancialTransaction ft = new FinancialTransaction();
        ft.setType(AppConstants.TYPE_IMPORT);
        ft.setStatus(AppConstants.RecordStatuses.CREATED_BY_MQ);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);
            ft.setNtn(ftDetailsAry[0]);

            ft.setName(ftDetailsAry[1]);
            ft.setIban(ftDetailsAry[2]);
            ft.setModeOfPayment(ftDetailsAry[3]);
        //    ft.setFinInsUniqueNumber(ftDetailsAry[4]);
            ft.setFinInsUniqueNumberCore(ftDetailsAry[4]);

            PaymentInformation pi = new PaymentInformation();
            if (!AppUtility.isEmpty(ftDetailsAry[5])) {
                pi.setBeneficiaryName(ftDetailsAry[5].length() < 100 ? ftDetailsAry[5] : ftDetailsAry[5].substring(0,98) );
            }
            pi.setBeneficiaryAddress(ftDetailsAry[6]);
            pi.setPortOfShipment(ftDetailsAry[7]);
            if (AppUtility.isBigDecimal(ftDetailsAry[8])) {
                pi.setFinancialInstrumentValue(new BigDecimal(ftDetailsAry[8]));
            }
            if (!AppUtility.isEmpty(ftDetailsAry[9])) {
                pi.setFinancialInstrumentCurrency(ftDetailsAry[9]);
            }

            if (AppUtility.isBigDecimal(ftDetailsAry[10])) {
                pi.setExchangeRate(new BigDecimal(ftDetailsAry[10]));
            }
            if (!AppUtility.isEmpty(ftDetailsAry[11])) {
                pi.setLcContractNo(ftDetailsAry[11]);
            }

            if (!AppUtility.isEmpty(ftDetailsAry[15])) {
                pi.setBeneficiaryCountry(ftDetailsAry[15]);
            }
            if (!AppUtility.isEmpty(ftDetailsAry[16])) {
                pi.setExporterName(ftDetailsAry[16]);
            }
            if (!AppUtility.isEmpty(ftDetailsAry[17])) {
                pi.setExporterAddress(ftDetailsAry[17]);
            }
            if (!AppUtility.isEmpty(ftDetailsAry[18])) {
                pi.setExporterCountry(ftDetailsAry[18]);
            }
            if (!AppUtility.isEmpty(ftDetailsAry[19])) {
                pi.setDeliveryTerm(ftDetailsAry[19]);
            }
            if (!AppUtility.isEmpty(ftDetailsAry[20])) {
                ft.setFinalDateOfShipment(AppUtility.convertDateFromString(ftDetailsAry[20]));
            }
            if (!AppUtility.isEmpty(ftDetailsAry[21])) {
                ft.setFinInsExpiryDate(AppUtility.convertDateFromString(ftDetailsAry[21]));
            }
            Set<ItemInformation> itemInformationSet = null ;

            if (!AppUtility.isEmpty(ftDetailsAry[12])) {
                String[] hsCodeAry = ftDetailsAry[12].split(MQUtility.DELIMETER_MULTIPLE_DATA);

                String[] qtyAry = ftDetailsAry[13].split(MQUtility.DELIMETER_MULTIPLE_DATA);
                String[] descAry = ftDetailsAry[14].split(MQUtility.DELIMETER_MULTIPLE_DATA);

                itemInformationSet = new HashSet<>(hsCodeAry.length);

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
            }
            pi.setFinancialTransaction(ft);
            ft.setPaymentInformation(pi);
            ft.setItemInformationSet(itemInformationSet);

             log.info("\n-----FT-<>" + ft);
        }
        return ft;
    }

    public BDA parseAndBuildBDAInfoImport(String data) {
        //Sharing of BDA Information
        BDA bda = new BDA();
        bda.setStatus(AppConstants.RecordStatuses.CREATED_BY_MQ);
     //   ft.setType(AppConstants.TYPE_IMPORT);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);
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


             log.info("\n-----BDA-<>" + bda);
        }
        return bda;
    }

    public BCA parseAndBuildBCAExport(String data) {
        //Sharing of Export BCA Information
        BCA bca = new BCA();
        bca.setStatus(AppConstants.RecordStatuses.CREATED_BY_MQ);

        //   ft.setType(AppConstants.TYPE_IMPORT);

        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);
            bca.setExporterNtn(ftDetailsAry[0]);
            bca.setExporterName(ftDetailsAry[1]);
            bca.setIban(ftDetailsAry[2]);
            bca.setModeOfPayment(ftDetailsAry[3]);
            bca.setFinInsUniqueNumber(ftDetailsAry[4]);
            bca.setBillNumber(ftDetailsAry[5]);
            if (AppUtility.isDate(ftDetailsAry[6])) {
                bca.setBillDated(new Date(ftDetailsAry[6]));
            }

            bca.setInvoiceNumber(ftDetailsAry[7]);

            if (AppUtility.isDate(ftDetailsAry[8])) {
                bca.setInvoiceDate(new Date(ftDetailsAry[8]));
            }
            //bca.setInvoiceAmount(AppUtility.isBigDecimal(ftDetailsAry[9]) ? new BigDecimal(ftDetailsAry[9]): null);
            bca.setBillAmount(AppUtility.isBigDecimal(ftDetailsAry[9]) ? new BigDecimal(ftDetailsAry[9]): null);
            bca.setAgentCommissionFcy(AppUtility.isBigDecimal(ftDetailsAry[10]) ? new BigDecimal(ftDetailsAry[10]): null);
            bca.setWithholdingTaxPkr(AppUtility.isBigDecimal(ftDetailsAry[11]) ? new BigDecimal(ftDetailsAry[11]): null);
            bca.setBcaAmountFcy(AppUtility.isBigDecimal(ftDetailsAry[12]) ? new BigDecimal(ftDetailsAry[12]): null);
            bca.setBcaPkr(AppUtility.isBigDecimal(ftDetailsAry[13]) ? new BigDecimal(ftDetailsAry[13]): null);

             log.info("\n-----BCA-<>" + bca);
        }
        return bca;
    }

    public CancellationOfFT parseAndBuildCFT(String data) {
        //Sharing of Cancellation of FT
        CancellationOfFT cft = new CancellationOfFT();
        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);
            cft.setTradeType(ftDetailsAry[0]);
            cft.setTraderNTN(ftDetailsAry[1]);
            cft.setTraderName(ftDetailsAry[2]);
            cft.setIban(ftDetailsAry[3]);
            cft.setFinInsUniqueNumber(ftDetailsAry[4]);

             log.info("\n-----CFT-<>" + cft);
        }
        return cft;
    }

    public  ReversalOfBdaBca parseAndBuildRevBDABCA(String data) {
        // Reversal of BDA/BCA
        ReversalOfBdaBca entity = new ReversalOfBdaBca();
        if (!AppUtility.isEmpty(data)) {
            String[] ftDetailsAry = data.split(MQUtility.DELIMETER_DATA,-1);

            entity.setTradeType(ftDetailsAry[0]);
            entity.setTraderNTN(ftDetailsAry[1]);
            entity.setTraderName(ftDetailsAry[2]);
            entity.setIban(ftDetailsAry[3]);
            entity.setFinInsUniqueNumber(ftDetailsAry[4]);
            entity.setBcaBdaUniqueIdNumber(ftDetailsAry[5]);

             log.info("\n-----Entity-<>" + entity);
        }
        return entity;
    }

}
