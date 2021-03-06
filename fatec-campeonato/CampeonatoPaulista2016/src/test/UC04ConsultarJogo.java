package test;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import model.Jogo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import persistence.CampeonatoDAO;
import persistence.CampeonatoDAOImpl;

public class UC04ConsultarJogo {
	
	static List<Jogo> resultadoEsperado;
	static List<Jogo> resultadoObtido;
	static CampeonatoDAO dao;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new CampeonatoDAOImpl();
		dao.geraJogos( new Date(), 1 );
		resultadoEsperado = dao.consultaJogos();
	}
	
	/**
	 * estabelece as pre-condicoes antes da execucao de cada teste
	 * @throws Exception
	 */
	@Before
	public void excluiJogos() throws Exception{
		dao.apagaJogos();
	}
	
	/**
	 * estabelece as pre-condicoes depois da execucao de cada teste
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao.apagaJogos();
	}
	
	/**
	 * consulta com sucesso todos os Jogos Existentes
	 * @throws SQLException 
	 */
	@Test
	public void CT01UC04ConsultarJogo_sucesso() throws SQLException{
		resultadoObtido = dao.consultaJogos();
		assertTrue(resultadoEsperado.equals( resultadoObtido ));
	}
	
	/**
	 * consulta com sucesso todos os Jogos Inexistentes
	 * @throws SQLException 
	 */
	@Test
	public void CT02UC04ConsultarJogo_sucesso() throws SQLException{
		dao.apagaJogos();
		resultadoObtido = dao.consultaJogos();
		assertTrue(resultadoEsperado.equals( resultadoObtido ));
	}
	
	/**
	 * consulta com sucesso os Jogos Existentes por uma data
	 * @throws SQLException 
	 */
	@Test
	public void CT03UC04ConsultarJogo_sucesso() throws SQLException{
		resultadoObtido = dao.consultaDataJogos( new Date() );
		assertTrue(resultadoEsperado.equals( resultadoObtido ));
	}
	
	/**
	 * consulta com sucesso os Jogos Existentes por uma data nula
	 * @throws SQLException 
	 */
	@Test
	public void CT04UC04ConsultarJogo_sucesso() throws SQLException{
		resultadoObtido = dao.consultaDataJogos( null );
		assertTrue(resultadoEsperado.equals( resultadoObtido ));
	}
	
	/**
	 * consulta com sucesso os Jogos Existentes por uma data inexistente
	 * @throws SQLException 

	 */
	@Test
	public void CT05UC04ConsultarJogo_sucesso() throws SQLException{
		Random rnd = new Random();
		long dt = -946771200000L + ( Math.abs( rnd.nextLong()) % ( 70L * 364 * 24 * 60 * 60 *1000));
		resultadoObtido = dao.consultaDataJogos( new Date( dt ) );
		assertTrue(resultadoEsperado.equals( resultadoObtido ));
	}
}