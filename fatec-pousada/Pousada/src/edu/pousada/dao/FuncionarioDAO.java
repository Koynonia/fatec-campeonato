/**
 * @author FERNANDO MORAES OLIVEIRA
 * Matéria Engenharia de Software 2
 * FATEC ZL 5º ADS - Tarde
 * 28/10/2016
 */

package edu.pousada.dao;

import java.sql.SQLException;
import java.util.List;

import edu.pousada.entity.Funcionario;

public interface FuncionarioDAO {

	public void adicionar(Funcionario obj) throws SQLException;
	public void alterar(Funcionario obj) throws SQLException;
	public void excluir(Funcionario obj) throws SQLException;
	public Funcionario consultar(Funcionario obj) throws SQLException;
	public List<Funcionario> todos() throws SQLException;	
}