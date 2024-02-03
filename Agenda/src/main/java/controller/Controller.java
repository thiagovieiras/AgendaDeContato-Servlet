package controller;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;


@WebServlet(urlPatterns = {"/main", "/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DAO dao = new DAO();   
	JavaBeans contato = new JavaBeans();
    
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			selecionarContato(request, response);
		} else if (action.equals("/update")) {
			atualizarContato(request, response);
		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			System.out.println("Erro!! Nenhum parametro encontrado");
			response.sendRedirect("index.html");
		}
	}
	
	// Lista contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Criando um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.selecionar();
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	
	// Novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o método inserir passando objeto contato
		dao.inserir(contato);
		response.sendRedirect("main");
	}
	
	// Atualizar contato
	protected void selecionarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idconParam = request.getParameter("idcon");
		Long idcon = Long.parseLong(idconParam);
		contato.setIdcon(idcon);
		dao.selecionarContato(contato);
		
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
	
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	protected void atualizarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		contato.setIdcon(Long.parseLong(request.getParameter("idcon")));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.update(contato);
		response.sendRedirect("main");
	}
	
	// Deletar contato
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idconParam = request.getParameter("idcon");
		Long idcon = Long.parseLong(idconParam);
		contato.setIdcon(idcon);
		dao.deletarContato(contato);
	
		response.sendRedirect("main");
	}
	
	// Gerar Relatório
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
	        response.setContentType("application/pdf");
	        response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");

	        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
	        	 PdfDocument pdf = new PdfDocument(writer);
	        	 Document document = new Document(pdf)){
	        	
	        	document.add(new Paragraph("Lista de contatos: "));
	        	document.add(new Paragraph("\n"));
	        	
	        	Table table = new Table(3);

	        	table.addCell("Nome");
	        	table.addCell("Fone");
	        	table.addCell("Email");
	        	
	        	ArrayList<JavaBeans> lista = dao.selecionar();
	        	for (int i = 0; i < lista.size(); i++) {
	        		table.addCell(lista.get(i).getNome());
	        		table.addCell(lista.get(i).getFone());
	        		table.addCell(lista.get(i).getEmail());
	        	}
	        	
	        	document.add(table);
	        	document.close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
//	private void addCellWithBackground(Table table, String text, Color backgroundColor) {
//		Cell cell = new Cell();
//		cell.add(new Paragraph(text));
//		cell.setBackgroundColor(backgroundColor);
//		table.addCell(cell);
//	}
	
}
