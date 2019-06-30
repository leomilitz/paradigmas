public class EnadeTable {
	private String ano, acertosCurso, acertosRegiao,
			    acertosBrasil, acertosDif, tipoQuestao,
			    idQuestao, objeto, prova, curso, 
			    objetoDetalhado, gabarito, texto,
			    imagem, urlProva, urlSintese, urlCurso;


	public EnadeTable() {
	}

//---------- getters -----------------
	public String getAno() {
		return this.ano;
	} 

	public String getCurso() {
		return this.curso;
	} 

	public String getUrlProva() {
		return this.urlProva;
	} 

	public String getUrlCurso() {
		return this.urlCurso;
	}

	public String getUrlSintese() {
		return this.urlSintese;
	} 

	public String getObjetoDetalhado() {
		return this.objetoDetalhado;
	} 

	public String getGabarito() {
		return this.gabarito;
	}

	public String getTexto() {
		return this.texto;
	}

	public String getImagem() {
		return this.imagem;
	}

	public String getAcertosCurso() {
		return this.acertosCurso.replaceAll("\"", "");
	} 

	public String getAcertosRegiao() {
		return this.acertosRegiao.replaceAll("\"", "");
	}

	public String getAcertosBrasil() {
		return this.acertosBrasil.replaceAll("\"", "");
	}  

	public String getAcertosDif() {
		return this.acertosDif.replaceAll("\"", "");
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

	public float getAcertosBrasilValue() {
		try {
      		String aux = this.getAcertosBrasil().replace(",", ".");
      		aux = aux.replaceAll("\"", "");
      		
      		float f = Float.parseFloat(aux);
      		
      		return f;
    	}
    	catch (NumberFormatException nfe) {
      		return -1;
    	}	
	}

	public float getAcertosCursoValue() {
		try {
      		String aux = this.getAcertosCurso().replace(",", ".");
      		aux = aux.replaceAll("\"", "");
      		
      		float f = Float.parseFloat(aux);
      		
      		return f;
    	}
    	catch (NumberFormatException nfe) {
      		return -1;
    	}	
	}

	public float getAcertosRegiaoValue() {
		try {
      		String aux = this.getAcertosRegiao().replace(",", ".");
      		aux = aux.replaceAll("\"", "");
      		
      		float f = Float.parseFloat(aux);
      		
      		return f;
    	}
    	catch (NumberFormatException nfe) {
      		return -1;
    	}	
	}

	public float getAcertosDifValue() {
		try {
      		String aux = this.getAcertosDif().replace(",", ".");
      		aux = aux.replaceAll("\"", "");
      		
      		float f = Float.parseFloat(aux);
      		
      		return f;
    	}
    	catch (NumberFormatException nfe) {
      		return 0;
    	}	
	}

// --------- setters -----------------

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public void setUrlProva(String urlProva) {
		this.urlProva = urlProva;
	}

	public void setUrlCurso(String urlCurso) {
		this.urlCurso = urlCurso;
	}

	public void setUrlSintese(String urlSintese) {
		this.urlSintese = urlSintese;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public void setObjetoDetalhado(String objetoDetalhado) {
		this.objetoDetalhado = objetoDetalhado;
	}

	public void setGabarito(String gabarito) {
		this.gabarito = gabarito;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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