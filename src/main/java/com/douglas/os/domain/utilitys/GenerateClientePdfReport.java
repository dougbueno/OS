package com.douglas.os.domain.utilitys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.douglas.os.domain.entity.Cliente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GenerateClientePdfReport {
	public static ByteArrayInputStream clienteReport(List<Cliente> clientes) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 1, 5, 2, 2 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Nome", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CPF", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			hcell = new PdfPCell(new Phrase("Celular", headFont));
			hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			for (Cliente cliente : clientes) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(cliente.getId().toString()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(cliente.getNome()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(cliente.getCpf())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Phrase(String.valueOf(cliente.getTelefone())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(new Phrase
			("Relatório de Clientes",
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
