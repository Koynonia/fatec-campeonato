package entity;

public class Estoque {
	private String isbn;
	private int qtd;
	private String dtCadastro;
	private String dtAlterado;
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public String getDtCadastro() {
		return dtCadastro;
	}
	public void setDtCadastro(String dtCadastro) {
		this.dtCadastro = dtCadastro;
	}
	public String getDtAlterado() {
		return dtAlterado;
	}
	public void setDtAlterado(String dtAlterado) {
		this.dtAlterado = dtAlterado;
	}
}