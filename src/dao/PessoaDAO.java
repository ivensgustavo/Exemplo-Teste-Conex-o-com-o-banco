package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.FabricaConexao;
import model.Pessoa;

public class PessoaDAO {
	
	private FabricaConexao fabricaConexao;
	private Connection conn;
	
	public PessoaDAO(FabricaConexao fabricaConexao) {
		this.fabricaConexao = fabricaConexao;
		this.conn = this.fabricaConexao.getConnection();
		System.out.println(conn);
	}
	
	public boolean adicionar(Pessoa pessoa) {
			
			PreparedStatement stmt = null;
			
			String sql = "INSERT INTO pessoas(nome, sobrenome) "
					+ "VALUES(?, ?)";
			
			try {
				stmt = conn.prepareStatement(sql);
				System.out.println(stmt);
				stmt.setString(1, pessoa.getNome());
				stmt.setString(2, pessoa.getSobrenome());
				stmt.executeUpdate();
				stmt.close();
				return true;
			} catch (SQLException e) {
				return false;
			} 
	}
	
	public boolean remover(Pessoa pessoa) {

		PreparedStatement stmt = null;
		
		String sql = "DELETE FROM pesspas WHERE nome = ? and sobrenome = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, pessoa.getNome());
			stmt.setString(2, pessoa.getSobrenome());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		} 
	}
	
	public Pessoa buscarPessoa(String nome, String sobrenome) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM pessoas WHERE nome = ? and sobrenome = ?";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setString(2, sobrenome);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setNome(rs.getString("nome"));
				pessoa.setSobrenome(rs.getString("sobrenome"));
				return pessoa;
			}else return null;
		} catch (SQLException e) {
			throw new RuntimeException("An SQL error occurred while fetching the player.");
		}
	}
}


