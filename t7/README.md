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
- [x] resto
- [ ] fazer a leitura dos dados a partir de uma thread separada
- [x] Ver qual foi o commit mais recente e antigo
- [ ] Controle de paginação da REST API
- [x] GitHubAnalyzerCmd
- [ ] Personalizadas
