public class EnadeTable {
	private String ano, acertosCurso, acertosRegiao,
			    acertosBrasil, acertosDif;

	private String tipoQuestao, idQuestao, objeto, prova;


	public EnadeTable() {
		this.ano = " ";
		this.acertosCurso = " ";
		this.acertosRegiao = " ";
		this.acertosDif = " ";
		this.acertosBrasil = " ";
		this.tipoQuestao = " ";
		this.idQuestao  = " ";
		this.objeto = " ";
		this.prova  = " ";
	}

//---------- getters -----------------
	public String getAno() {
		return this.ano;
	} 

	public String getAcertosCurso() {
		return this.acertosCurso;
	} 

	public String getAcertosRegiao() {
		return this.acertosRegiao;
	}

	public String getAcertosBrasil() {
		return this.acertosBrasil;
	}  

	public String getAcertosDif() {
		return this.acertosDif;
	} 

	public String getTipoQuestao() {
		return this.tipoQuestao;
	} 

	public String getIdQuestao() {
		return this.idQuestao;
	}

	public String getObjeto() {
		return this.objeto;
	}  

	public String getProva() {
		return this.prova;
	} 
// --------- setters -----------------

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setAcertosCurso(String acertosCurso) {
		this.acertosCurso = acertosCurso;
	}

	public void setAcertosRegiao(String acertosRegiao) {
		this.acertosRegiao = acertosRegiao;
	}

	public void setAcertosDif(String acertosDif) {
		this.acertosDif = acertosDif;
	}

	public void setAcertosBrasil(String acertosBrasil) {
		this.acertosBrasil = acertosBrasil;
	}

	public void setTipoQuestao(String tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public void setIdQuestao(String idQuestao) {
		this.idQuestao = idQuestao;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public void setProva(String prova) {
		this.prova = prova;
	}

}