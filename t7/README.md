# Java GitHub Analyzer
## Compilar e Executar
### Compilar
#### Windows
```
javac -cp .;gson-2.8.5.jar *.java
```
#### Linux
```
javac -cp .:gson-2.8.5.jar *.java
```
### Executar
#### Windows
##### GUI
```
java -cp .;gson-2.8.5.jar GitHubAnalyzerGUI
```
##### Cmd
```
java -cp .;gson-2.8.5.jar GitHubAnalyzerCmd seu_arquivo.txt
```
#### Linux
##### GUI
```
java -cp .:gson-2.8.5.jar GitHubAnalyzerGUI
```
##### Cmd
```
java -cp .:gson-2.8.5.jar GitHubAnalyzerCmd seu_arquivo.txt
```
## Checklist
- [x] Parte 1
- [x] fazer a leitura dos dados a partir de uma thread separada
- [x] Ver qual foi o commit mais recente e antigo
- [x] Controle de paginação da REST API
- [x] GitHubAnalyzerCmd
- [x] Personalizadas
  - [x] Gráfico de barras (número de commits por repositório)
  - [x] Informação detalhada de cada commit

## Observações
A lista de links que disponibilizei apenas possui 3 links, meu repositório, e os dois repositórios disponíveis do repositório da professora Andrea Schwertner Charão ([AndreaInfUFSM](https://github.com/AndreaInfUFSM)). Em visão geral, foi extremamente divertido ter as minhas requisições bloqueadas ao ler o gigantesco repositório do gson várias vezes, por não ter botado todas informações em um arquivo.
