# Random Picker
## Sobre o programa
É um aplicativo java que embaralha uma lista de nomes.
## Compilar e executar
Em uma linha de comando, faça os seguintes comandos:
#### Compilar
```
javac *.java
```
#### Executar
*RandomPickerCmd:*
```
java -cp . RandomPickerCmd seu_arquivo.txt
```
*RandomPickerGUI:*
```
java -cp . RandomPickerGUI
```
## Checklist
#### Geral
- [x] Embaralhamento offline
- [x] Embaralhamento online
#### Random Picker Cmd
- [x] Resto
- [x] Realizar a escolha automática do embaralhamento online ou offline
#### Random Picker GUI
- [x] Criar a menubar
- [x] Criar os menuitems
- [x] Menuitem open
- [x] Menuitem exit
- [x] Menuitem about
- [x] TextArea ao abrir o MenuItem open
- [x] TextArea permissiva
- [x] Botão Shuffle que embaralha a lista
- [x] Mostrar os items da lista um de cada vez (botão next)
- [x] Organizar o código

## Observações
- O programa não irá embaralhar apenas um item.
- O botão next mostra os elementos da lista um por um, e o botão shuffle só irá ser habilitado apósa apresentação dos nomes acabar. Porém,
  a lista **também** é mostrada como um todo na text área, por questão de tornar o programa útil.
