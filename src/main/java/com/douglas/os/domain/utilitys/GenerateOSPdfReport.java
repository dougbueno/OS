package com.douglas.os.domain.utilitys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.douglas.os.domain.entity.OS;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateOSPdfReport {
	
	public static ByteArrayInputStream osReport(List<OS> osDTOS) {
		Document document = new Document(PageSize.LEGAL.rotate());
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 1, 2, 2, 2, 2, 2, 2,2 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Técnico", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cliente", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Data de Abertura", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Data de Fechamento", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Prioridade", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Status", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Observações", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			for (OS osDTO : osDTOS) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(osDTO.getId().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				
				cell = new PdfPCell(new Phrase(osDTO.getTecnico().getNome()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(osDTO.getCliente().getNome()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(osDTO.getDataAbertura())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(osDTO.getDataFechamento())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(osDTO.getPrioridade())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(osDTO.getStatus())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(osDTO.getObservacoes())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(new Phrase
			("Relatório de Ordens de Serviço",
					FontFactory.getFont(
					FontFactory.HELVETICA_BOLD,
					20				)));
			document.add(table);
			document.close();

		} catch (DocumentException ex) {

			Logger.getLogger(GenerateTecnicoPdfReport.class.getName()).log(Level.SEVERE, null, ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}
