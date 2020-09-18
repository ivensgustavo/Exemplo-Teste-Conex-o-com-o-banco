package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import conexao.FabricaConexao;
import model.Pessoa;

class PessoaDAOTest {

	@Mock
	private FabricaConexao fabrica = Mockito.mock(FabricaConexao.class);
	@Mock
	private Connection conn = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	private Pessoa pessoa = new Pessoa("Ivens", "Gustavo");
	private Pessoa pessoa2 = new Pessoa("Gustavo", "Ivens");
	
	private static List<Pessoa> pessoas = new ArrayList<Pessoa>(); 
	
	
	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		Mockito.when(fabrica.getConnection()).thenReturn(conn);
		Mockito.when(conn.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeUpdate()).thenReturn(1);
		Mockito.when(stmt.executeQuery()).thenReturn(rs);
		Mockito.when(rs.next()).thenReturn(true);
		Mockito.when(rs.getString("nome")).thenReturn(pessoa2.getNome());
		Mockito.when(rs.getString("sobrenome")).thenReturn(pessoa2.getSobrenome());
		Mockito.doNothing().when(stmt).close();
		pessoas.add(pessoa2);
	}
	
	@Test
	void deveriaAdicionarUmaPessoa() throws SQLException {
		this.setUp();
		
		PessoaDAO pessoaDAO = new PessoaDAO(fabrica);		
		boolean res = pessoaDAO.adicionar(pessoa);
		pessoas.add(pessoa);
		
		Mockito.verify(stmt, Mockito.times(1)).executeUpdate();
		assertTrue(res);
	}
	
	@Test
	void deveriaRemoverUmaPessoa() throws SQLException {
		this.setUp();
		
		PessoaDAO pessoaDAO = new PessoaDAO(fabrica);
		boolean res = pessoaDAO.remover(pessoa);
		pessoas.remove(pessoa);
		
		Mockito.verify(stmt, Mockito.times(1)).executeUpdate();
		assertTrue(res);
	}
	
	@Test
	public void deveriaPegarUmaPessoa() throws SQLException {
		this.setUp();
		
		Pessoa pessoaEncontrada = new PessoaDAO(fabrica).buscarPessoa("Gustavo", "Ivens");
		
		System.out.println(pessoas.size());
		Mockito.verify(rs, Mockito.times(1)).getString("nome");
		Mockito.verify(rs, Mockito.times(1)).getString("sobrenome");
		assertEquals(pessoaEncontrada.getNome(), pessoa2.getNome());
		assertEquals(pessoaEncontrada.getSobrenome(), pessoa2.getSobrenome());
	}

}
