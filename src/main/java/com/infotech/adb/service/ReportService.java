package com.infotech.adb.service;

import com.infotech.adb.enums.PrintReportEnums;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Log4j2
public class ReportService {

    @Value("${spring.servlet.multipart.location}")
    private String tmpFilePath;
    @Autowired
   private DataSource dataSource;

    public ByteArrayInputStream buildGDPrint(Long gdId)
            throws IOException, JRException, SQLException {
        log.info("buildGDPrint method called..");

        Map<String, Object> map = new HashMap<>();
        map.put("gdId", gdId);
        map.put("reportName", PrintReportEnums.GD_REPORT );

        return this.generateGenericReport("ADB-GD", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildFTPrint(Long ftId)
            throws IOException, JRException, SQLException {
        log.info("buildFTPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("ftId", ftId);
        map.put("reportName", PrintReportEnums.FT_REPORT );

        return this.generateGenericReport("ft-export-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildBCAPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildBCAPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("reportName", PrintReportEnums.BCA_REPORT);

        return this.generateGenericReport("BCA1-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildFTImportPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildFTImportPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("ftId", id);
        map.put("reportName", PrintReportEnums.FT_IMPORT_REPORT);

        return this.generateGenericReport("ft-import-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildBDAPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildBDAPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("reportName", PrintReportEnums.BDA_REPORT);

        return this.generateGenericReport("BDA1-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildITRSPrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildITRSPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.ITRS_RPEORT);

        return this.generateGenericReportXLS("ITRS-import-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildBCARealizedPrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildBCARealizedPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.BCA_REALIZED_REPORT);

        return this.generateGenericReportXLS("bca-realized-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildExportOverduePrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildExportOverduePrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.EXPORT_OVERDUE_REPORT);

        return this.generateGenericReportXLS("export-overdue-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildFIRegisterPrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildFIRegisterPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.FI_REGISTER_REPORT);

        return this.generateGenericReportXLS("FI-register-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildBDARegisterPrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildBDARegisterPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.BDA_REGISTER_REPORT);

        return this.generateGenericReportXLS("BDA-register-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildShippedStatusPrint(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildShipmentStatusPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.SHIPPED_STATUS_REPORT);

        return this.generateGenericReportXLS("DAP-V11-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildSettlementOfFiPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildSettelmentOfFiPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("reportName", PrintReportEnums.SETTLEMENT_OF_FI_PRINT);

        return this.generateGenericReport("settkement-of-fi-print", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildSettlementOfFiReport(String fromDate, String toDate)
            throws IOException, JRException, SQLException {
        log.info("buildSettlementOfFiReport method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        ZonedDateTime zonedFromDate = null, zonedToDate = null;
        if (!AppUtility.isEmpty(fromDate)) {
            zonedFromDate = AppUtility.getZonedDateTimeFromFormattedString(fromDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        if (!AppUtility.isEmpty(toDate)) {
            zonedToDate = AppUtility.getZonedDateTimeFromFormattedString(toDate, AppConstants.DateFormats.DATE_FORMAT_ONE);
        }
        map.put("fromDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedFromDate));
        map.put("toDate", AppUtility.formatZonedDateTime(AppConstants.DateFormats.YEAR_MONTH_DATE, zonedToDate));
        map.put("reportName", PrintReportEnums.SETTLEMENT_OF_FI_REPORT);

        return this.generateGenericReportXLS("Settlement-of-fi-report", map, dataSource.getConnection() );
    }

    private ByteArrayInputStream generateGenericReport(String reportName, Map<String, Object> parameters, Connection connection)
            throws JRException, IOException, SQLException {
        String reportPath = getClass().getClassLoader().getResource("reports/" + reportName + ".jrxml").getPath();
        reportPath = URLDecoder.decode(reportPath, "UTF-8");

        String reportId = UUID.randomUUID().toString();
        String pdfPath = tmpFilePath + reportName + "_" + reportId + ".pdf";
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                connection);

        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        connection.close();
        return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
    }

    private ByteArrayInputStream generateGenericReportXLS(String reportName, Map<String, Object> parameters, Connection connection)
            throws JRException, IOException, SQLException {
        String reportPath = getClass().getClassLoader().getResource("reports/" + reportName + ".jrxml").getPath();
        reportPath = URLDecoder.decode(reportPath, "UTF-8");

        String reportId = UUID.randomUUID().toString();
        String pdfPath = tmpFilePath + reportName + "_" + reportId + ".xls";
        JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                connection);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, pdfPath);

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);

        exporter.exportReport();

        //JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
        connection.close();
        return new ByteArrayInputStream(os.toByteArray());
    }
}