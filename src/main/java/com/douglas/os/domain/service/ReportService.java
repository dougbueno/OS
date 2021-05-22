package com.douglas.os.domain.service;

import java.io.IOException;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {
	
	@Autowired
	private TecnicoRepository repository;

	public String exportReport() throws JRException, IOException {
		try {
			List<Tecnico> tecnico = repository.findAll();
			// load file and compile it
			InputStream inputStream = getClass().getResourceAsStream( "/FormularioTecnicos.jrxml" );
//			InputStream file = new FileInputStream(new File("FormularioTecnicos.jrxml"));
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tecnico);
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("createBy", "Douglas Bueno");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JasperPrintManager.printReport(jasperPrint, false);
			return "Gerou";
		} catch (JRException e) {
			return e.getMessage();
//		} catch (IOException e) {
//			return e.getMessage();
		}
	}
}
