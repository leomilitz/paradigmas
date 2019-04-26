
% T3 de Paradigmas da Programação, feito por Leonardo Militz.

% 1. Defina um predicado odd(N) que seja verdadeiro se N for um número ímpar. Exemplo de uso:

odd(N) :-
 	X is mod(N,2),
 	X =\= 0.

% 2. Defina um predicado recursivo hasN(L,N) que seja verdadeiro se L for uma lista de N elementos.

hasN([],N) :- N =< 0.
hasN([H|T],N) :-
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

incN([],[],N).
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
%    pares de L1, conforme o exemplo abaixo: -- incompleta

even(N) :- not(odd(N)).

onlyEven([],[]).
onlyEven(L1,L2) :- 
	(L1 = [H1|T1], L2 = [H2|T2]),
	even(H1), H2 is H1,
	onlyEven(T1,T2).

onlyEven(L1,L2) :-
	(L1 = [H1|T1], L2 = [H2|T2]),
	odd(H1),
	onlyEven(T1, L2).

% 7. Defina um predicado recursivo countdown(N,L), de forma que L seja uma lista 
%    com os números [N, N-1, N-2, .., 1], sendo N um número positivo.

countdown(0,[]).
countdown(N,[H|T]) :-
	N>0,
	H is N,
	N1 is N - 1,
	countdown(N1,T).

% 8. Defina um predicado recursivo nRandoms(N,L), de forma que L seja uma lista 
%    com N números gerados aleatoriamente.

% OBSERVAÇÃO: Fiz um adição no predicado, para que o usuário possa escolher o "teto"
%             numérico para os números aleatórios, não se limitando ao valor default
%			  do exercício que era 100.

nRandoms(0,[],RandCeiling).
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
%     primeiras potências de 2, sendo a primeira 2^0 e assim por diante. --refazer

reverse([H|T],RestTail,ReverseList) :-
     reverse(T,[H|RestTail],ReverseList).
	
potencias(N,L) :-
	potN0(N,L2),
	reverse(L2,L).