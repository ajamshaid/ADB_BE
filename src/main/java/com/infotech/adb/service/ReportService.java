package com.infotech.adb.service;

import com.infotech.adb.enums.PrintReportEnums;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
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
        log.info("buildSADPrint method called..");
;
        Map<String, Object> map = new HashMap<>();
        map.put("gdId", gdId);
        map.put("reportName", PrintReportEnums.GD_REPORT );

        return this.generateGenericReport("ADB-GD", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildFTPrint(Long ftId)
            throws IOException, JRException, SQLException {
        log.info("buildSADPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("ftId", ftId);
        map.put("reportName", PrintReportEnums.FT_REPORT );

        return this.generateGenericReport("ft-export-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildBCAPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildSADPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("reportName", PrintReportEnums.BCA_REPORT);

        return this.generateGenericReport("BCA1-report", map, dataSource.getConnection() );
    }

    public ByteArrayInputStream buildFTImportPrint(Long id)
            throws IOException, JRException, SQLException {
        log.info("buildSADPrint method called..");
        ;
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("reportName", PrintReportEnums.FT_IMPORT_REPORT);

        return this.generateGenericReport("ft-import-report", map, dataSource.getConnection() );
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
}
