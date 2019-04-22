% ----------------------------------- predicados ---------------------------------------

ao_lado(X, Y, L) :-
	nextto(X,Y,L) ;
	nextto(Y,X,L).

head(X, [H|T]) :- X = H.

um_entre(X, Y, L) :-
  	nth0(A,L,X), nth0(B,L,Y),
  	Dif is A-B,
  	(Dif =:= -2 ; Dif =:= 2).

a_direita(A,X,L) :-
	nth0(PosA, L, A), nth0(PosX, L, X),
	PosA > PosX.

a_esquerda(A,X,L) :-
	nth0(PosA, L, A), nth0(PosX, L, X),
	PosA < PosX.

esta_entre(E,X,Y,L) :- % obs: pro exercicio X sempre será menor que Y.
	nth0(A,L,X), nth0(B,L,Y), nth0(C,L,E),
	(C > A, C < B).

backto(X,Y,L) :-
	nth0(A,L,X), nth0(B,L,Y),
	Dif is B-A,
	Dif == 1.

nas_pontas(X,L) :- head(X,L) ; last(L,X). 

% ----------------------------------- Solução ----------------------------------------

% Exemplo: carro (cor, ano, montadora, dono, placa, km) 
% link do problema: https://rachacuca.com.br/logica/problemas/carros-antigos/

solucao(Carros) :-
	Carros = [_, _, _, _, _],
	esta_entre(carro(_,_,_,_,c3,_),  carro(branco,_,_,_,_,_), carro(_,_,_,_,d4,_), Carros),
	nextto(carro(_,_,_,_,_,km140), carro(_,_,_,glenn,_,_), Carros),
	member(carro(_,ano1960,_,_,_,km140), Carros),
	nas_pontas(carro(_,_,_,harley,_,_), Carros),
	esta_entre(carro(branco,_,_,_,_,_), carro(_,_,ford,_,_,_), carro(_,ano1970,_,_,_,_), Carros),
	backto(carro(_,_,_,_,a1,_), carro(_,ano1955,_,_,_,_), Carros),
	a_esquerda(carro(amarelo,_,_,_,_,_),carro(_,_,_,_,_,km140), Carros),
	[_,_,carro(verde,_,_,_,_,_), _,_] = Carros,
	backto(carro(_,ano1955,_,_,_,_), carro(_,_,_,_,b2,_), Carros),
	a_direita(carro(_,_,volkswagen,_,_,_), carro(vermelho,_,_,_,_,_), Carros),
	ao_lado(carro(_,_,chevrolet,_,_,_), carro(_,_,_,_,_,km140), Carros),
	nas_pontas(carro(_,_,_,_,_,km210), Carros),
	esta_entre(carro(branco,_,_,_,_,_), carro(_,_,_,_,_,km190), carro(_,_,porsche,_,_,_), Carros),
	backto(carro(_,_,_,_,a1,_), carro(_,_,mercedes,_,_,_), Carros),
	a_esquerda(carro(branco,_,_,_,_,_), carro(_,_,_,_,_,km100), Carros),
	member(carro(_,_,_,ponce,e5,_), Carros),
	a_direita(carro(_,_,_,_,_,km140), carro(branco,_,_,_,_,_), Carros),
	esta_entre(carro(_,_,_,thales,_,_), carro(_,ano1970,_,_,_,_), carro(_,_,_,glenn,_,_), Carros),
	a_direita(carro(_,_,chevrolet,_,_,_), carro(_,ano1965,_,_,_,_), Carros),
	member(carro(azul,ano1950,_,_,_,_), Carros),
	member(carro(_,_,_,aguiar,_,_), Carros),
	member(carro(_,_,_,_,_,km80), Carros).

