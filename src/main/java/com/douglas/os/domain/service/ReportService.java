package com.douglas.os.domain.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douglas.os.domain.entity.Tecnico;
import com.douglas.os.domain.repository.TecnicoRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Service
public class ReportService {
	
	@Autowired
	private TecnicoRepository repository;

	public String exportReport() throws JRException{
		try {
			List<Tecnico> tecnico = repository.findAll();
			// load file and compile it
			InputStream inputStream
			  = getClass().getResourceAsStream("/FormularioTecnicos.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JRSaver.saveObject(jasperReport, "FormularioTecnicos.jasper");
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tecnico);
			Map<String, Object> parameters = new HashMap<>();
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(
			  new SimpleOutputStreamExporterOutput("funcionarios.pdf"));

			SimplePdfReportConfiguration reportConfig
			  = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig
			  = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("Douglas Bueno");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");

			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();
			
			return "Gerou";
		} catch (JRException e) {
			return e.getMessage();
		}
	}
}
