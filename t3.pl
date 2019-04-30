
% T3 de Paradigmas da Programação, feito por Leonardo Militz.

% ------------------- Auxiliares --------------------

even(N) :- not(odd(N)).

popLast([_], []).
popLast([H, Next|T], [H|NTail]):-
	popLast([Next|T], NTail).

reverse([H|T],RestTail,ReverseList) :-
     reverse(T,[H|RestTail],ReverseList).


% 1. Defina um predicado odd(N) que seja verdadeiro se N for um número ímpar. Exemplo de uso:

odd(N) :-
 	X is mod(N,2),
 	X =\= 0.

% 2. Defina um predicado recursivo hasN(L,N) que seja verdadeiro se L for uma lista de N elementos.

hasN([],N) :- N =< 0.
hasN([_|T],N) :-
	N > 0,
	N1 is N-1,
	hasN(T,N1).

% 3. Defina um predicado recursivo inc99(L1,L2), de forma que L2 seja uma lista com todos os 
%    elementos de L1 acrescidos da constante 99.

inc99([],[]).
inc99(L1,L2) :- 
	(L1 = [H1|T1], L2 = [H2|T2]),
	H2 is H1 + 99,
	inc99(T1,T2).

% 4. Defina um predicado recursivo incN(L1,L2,N), de forma que L2 seja uma lista com todos os 
%    elementos de L1 acrescidos da constante N.

incN([],[],_).
incN(L1,L2,N) :-
	(L1 = [H1|T1], L2 = [H2|T2]),
	H2 is H1 + N,
	incN(T1,T2,N).

% 5. Defina um predicado recursivo comment(L1,L2), de forma que L1 seja uma lista de strings e L2 
%    tenha todos os elementos de L1 concatenados com o prefixo "%%".

comment([],[]).
comment(L1,L2) :-
	(L1 = [H1|T1], L2 = [H2|T2]),
	string_concat("%% ",H1,H2),
	comment(T1,T2).

% 6. Defina um predicado recursivo onlyEven(L1,L2), de forma que L2 seja uma lista só com os elementos 
%    pares de L1. -- Arrumar

onlyEven([],[]).

onlyEven([H1|T1],L) :-
	odd(H1),
	onlyEven(T1,L).

onlyEven([H1|T1],[H2|T2]) :- 
	H1 = H2,
	even(H1),
	onlyEven(T1,T2).

% 7. Defina um predicado recursivo countdown(N,L), de forma que L seja uma lista 
%    com os números [N, N-1, N-2, .., 1], sendo N um número positivo.

countdown(0,[]).
countdown(N,[H|T]) :-
	N > 0,
	H is N,
	N1 is N - 1,
	countdown(N1,T).

% 8. Defina um predicado recursivo nRandoms(N,L), de forma que L seja uma lista 
%    com N números gerados aleatoriamente.

% OBSERVAÇÃO: Fiz um adição no predicado, para que o usuário possa escolher o "teto"
%             numérico para os números aleatórios, não se limitando ao valor default
%			  do exercício que era 100.

nRandoms(0,[],_).
nRandoms(N,[H|T],RandCeiling) :-
	N > 0,
	N1 is random(RandCeiling),
	N1 = H,
	N2 is N - 1,
	nRandoms(N2,T,RandCeiling).

% 9. Defina um predicado recursivo potN0(N,L), de forma que L seja uma lista de 
%    potências de 2, com expoentes de N a 0. 

potN0(-1,[]).
potN0(N,[H|T]) :-
	N >= 0,
	N1 is 2**N,
	N1 = H,
	N2 is N - 1,
	potN0(N2,T).

% 10. Defina um predicado recursivo zipmult(L1,L2,L3), de forma que cada elemento da 
%     lista L3 seja o produto dos elementos de L1 e L2 na mesma posição do elemento 
%     de L3.

zipmult([],[],[]).
zipmult(L1,L2,L3) :-
	L1 = [H1|T1],
	L2 = [H2|T2],
	L3 = [H3|T3],
	
	H3 is H1*H2,
	
	zipmult(T1,T2,T3).

% 11. Defina um predicado recursivo potencias(N,L), de forma que L seja uma lista com as N
%     primeiras potências de 2, sendo a primeira 2^0 e assim por diante. -- refazer, solução provisória.

potencias_aux(N,[],N).
potencias_aux(X,[H|T],N) :-
	X < N,
	H is 2**X,
	X1 is X + 1,
	potencias_aux(X1, T, N).

potencias(N,L) :-
	potencias_aux(0,L,N).

% 12. Defina um predicao recursivo cedulas(V,L1,L2), que receba um valor V e uma lista L1 de cédulas com 
%     valores em Reais (R$), em ordem decrescente, e obtenha a lista L2 decompondo o valor V em 0 ou mais cédulas de cada tipo.

cedulas(_,[],[]).
cedulas(V,[H1|T1],[H2|T2]) :-
    (V >= H1,
    ModResult is mod(V,H1),
    H2 is floor(V/H1),
    cedulas(ModResult,T1,T2));
    (V < H1, 
    H2 is 0,
    ModResult is mod(V,H1),
    cedulas(ModResult,T1,T2)).