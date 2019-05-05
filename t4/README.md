# Trabalho 4

## [Explicação do caso](https://github.com/AndreaInfUFSM/elc117-2019a/tree/master/trabalhos/t4)

## Culpado
  ```
   ?-  assassino(X).
   X = alice ;
   X = alice ;
   false.
  ```
  
  **Alice** é a culpada do crime. A arma utilizada foi um **martelo**, o motivo foi e **ciúmes**,
  o local de crime foi o **apartamento** e o crime ocorreu em uma **sexta-feira** ou em uma **quinta feira**.
  
## Motivo
```
?- motivo(alice).
true.

?- insanidade(alice).
false.

?- dinheiro(alice).
false.

?- ciumes(alice).
true ;
```
Ou seja, é inferível que a motivação de Alice foi **ciúmes**, visto que Alice namorou Pedro, que também namorou anita, como provado:

```
?- relacionamento(alice,pedro), relacionamento(pedro,anita).
true.
```

## Acesso

Além dos motivos, o protagonista do crime também precisa de acesso à cena do crime - e pode-se provar que Alice o possuia:

```
?- acesso(alice).
true ;
```

Para que ela possa ter acesso a cena do crime, ela precisaria ter a chave, a arma **e** estar presente nos dias do crime;
o que pode ser comprovado:
```
?- no_apartamento(alice), roubou_chave(alice), roubou_arma(alice).
true 
```

A arma usada foi o martelo da caixa de ferramentas:

```
?- bastao(alice).
false.

?- martelo(alice).
true.
```

## Dia

O crime ocorreu em uma sexta feira ou em uma quinta, como pode ser provado por:

```
?- no_apartamento(alice).
true ;

?- estava_em(alice,X,quinta).
X = apartamento.

?- estava_em(alice,X,sexta).
X = apartamento.
```

## [Resolução em Prolog](https://github.com/leomilitz/paradigmas/blob/master/t4/t4.pl)


