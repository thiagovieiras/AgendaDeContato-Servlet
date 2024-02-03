package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
	/* Módulo de conexão */
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/servlet_agenda?useTimezone=true&serverTimezone=UTC";
	private String user = "BDuser";
	private String password = "BDuser123";
	
	//Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user,  password);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* CRUD CREATE */
	public void inserir(JavaBeans contato) {
		String query = "INSERT INTO contatos (nome, fone, email) VALUES (?,?,?)";
		try {
			// Abrir conexão
			Connection con = conectar();
			// Prepara query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(query);
			// Substituir os paramentros (?) pelo conteúdo das variáveis JavaBeans
			pst.setString(1,  contato.getNome());
			pst.setString(2,  contato.getFone());
			pst.setString(3,  contato.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Encerrar a conexão com o banco de dados
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* CRUD UPDATE */
	public void selecionarContato(JavaBeans contato) {
		String query = "SELECT * FROM contatos WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setIdcon(rs.getLong(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(JavaBeans contato) {
		String query = "UPDATE contatos SET nome = ?, fone = ?, email = ? WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,  contato.getNome ());
			pst.setString(2,  contato.getFone ());
			pst.setString(3,  contato.getEmail());
			pst.setLong  (4,  contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* CRUD READ */
	public ArrayList<JavaBeans> selecionar() {
		// Criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList();
		String query = "SELECT * FROM contatos ORDER BY nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			// Laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				// Variáveis de apoio que recebem os dados do banco
				Long idcon = rs.getLong(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// Populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* CRUD DELETE */
	public void deletarContato(JavaBeans contato) {
		String query = "DELETE FROM contatos WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setLong(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
