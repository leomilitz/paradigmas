# Java UFSM-ENADE Explorer
Serve para ver questões do ENADE de algum csv.
## Implentações Obrigatórias
- Ler de .csv para TableView com 9 colunas;
- Verifica se existe o arquivo a ser lido, caso não exista, baixa-o da planilha online;
- Janela que sobrepõe a principal para poder alterar a URL base do programa;
  - Tratamento para erros de URL;
- Item Reload que recarrega o arquivo a partir da tabela online;
- Item Exit que sai do programa;
- Item About que mostra informações do programador e nome do programa;
- Double Click nos itens da TableView abrem uma janela modal que mostra as informações detalhadas da questão, gabarito e imagem.
  - Verificação de url válido de imagem
  - Botão exit
  - Botão charts
## Personalizado
- Dentro da janela modal, existe um botão charts que abre outra janela modal que mostras as informações da questão via gráfico.
