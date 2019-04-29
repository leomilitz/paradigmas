# Trabalho 4

## [Explicação do caso](https://github.com/AndreaInfUFSM/elc117-2019a/tree/master/trabalhos/t4)

## Culpado
  ```
   ?-  assassino(X).
   X = maria ;
   false.
  ```
  
  **Maria** é a culpada do crime.

## Motivo
```
?- motivo(maria).
true.

?- insanidade(maria).
true.

?- dinheiro(maria).
true.

?- ciumes(maria).
false.
```
Ou seja, é inferível que as motivações de maria foram **insanidade** e **dinheiro**, visto que Maria é pobre e insana, como provado:

```
?- pobre(maria), insano(maria).
true.
```

## Acesso

Além dos motivos, o protagonista do crime também precisa de acesso à cena do crime - e pode-se provar que Maria o possuia:

```
?- acesso(maria).
true ;
```

Para que ela possa ter acesso a cena do crime, ela precisaria ter a chave, a arma **e** estar presente nos dias do crime;
o que pode ser comprovado:
```
?- no_apartamento(maria), roubou_chave(maria), roubou_arma(maria).
true 
```
## [Resolução em Prolog](https://github.com/leomilitz/paradigmas/blob/master/t4/t4.pl)


