
% Trabalho feito por Leonardo Militz, para a disciplina paradigmas da programação.

%------------------------- integrantes do caso e suas peculiaridades ------------------------------------

% Insanos:
    
    insano(adriano).
    insano(maria).
    
% Pobres:
    
    pobre(bia).
    pobre(pedro).
    pobre(maria).

% Ricos:
    
    rico(caren).
    rico(alice).
    rico(henrique).
    rico(adriano).

% Vitima:

    vitima(anita).

% Relacionamentos:
    
    relacionamento(anita, bernardo).
    relacionamento(bernardo, anita).

    relacionamento(bernardo, caren).
    relacionamento(caren, bernardo).

    relacionamento(anita, pedro).
    relacionamento(pedro, anita).

    relacionamento(pedro, alice).
    relacionamento(alice, pedro).

    relacionamento(henrique, alice).
    relacionamento(alice, henrique).

    relacionamento(henrique, maria).
    relacionamento(maria, henrique).

    relacionamento(maria, adriano).
    relacionamento(adriano, maria).

    relacionamento(adriano, caren).
    relacionamento(caren, adriano).

% Local e dia:

    estava_em(pedro, sm, segunda).
    estava_em(pedro, sm, terca).
    estava_em(pedro, poa, quarta).
    estava_em(pedro, sm, quinta).
    estava_em(pedro, apartamento, sexta).

    estava_em(caren, poa, segunda).
    estava_em(caren, poa, terca).
    estava_em(caren, poa, quarta).
    estava_em(caren, sm, quinta).
    estava_em(caren, apartamento, sexta).

    estava_em(henrique, apartamento, segunda).
    estava_em(henrique, poa, terca).
    estava_em(henrique, apartamento, quarta).
    estava_em(henrique, apartamento, quinta).
    estava_em(henrique, apartamento, sexta).

    estava_em(bia, apartamento, segunda).
    estava_em(bia, poa, terca).
    estava_em(bia, poa, quarta).
    estava_em(bia, sm, quinta).
    estava_em(bia, apartamento, sexta).

    estava_em(adriano, apartamento, segunda).
    estava_em(adriano, apartamento, terca).
    estava_em(adriano, sm, quarta).
    estava_em(adriano, apartamento, quinta).
    estava_em(adriano, apartamento, sexta).

    estava_em(alice, apartamento, segunda).
    estava_em(alice, poa, terca).
    estava_em(alice, poa, quarta).
    estava_em(alice, apartamento, quinta).
    estava_em(alice, apartamento, sexta).

    estava_em(bernardo, sm, segunda).
    estava_em(bernardo, sm, terca).
    estava_em(bernardo, poa, quarta).
    estava_em(bernardo, sm, quinta).
    estava_em(bernardo, apartamento, sexta).

    estava_em(maria, apartamento, segunda).
    estava_em(maria, sm, terca).
    estava_em(maria, sm, quarta).
    estava_em(maria, sm, quinta).
    estava_em(maria, apartamento, sexta).

% ----------------------------- Predicados para a solução do caso ---------------------------------------

% Estava no apartamento no momento do crime.

    no_apartamento(X) :- 
        estava_em(X, apartamento, quinta);
        estava_em(X, apartamento, sexta).

% pode ter roubado a arma

    roubou_arma(X) :- bastao(X) ; martelo(X).   
    
    % bastão
    bastao(X) :-
        estava_em(X, poa, quinta);
        estava_em(X, sm, quarta).
    
    % martelo
    martelo(X) :-
        estava_em(X, apartamento, quarta);
        estava_em(X, apartamento, quinta).

% pode ter roubado a chave
    roubou_chave(X) :-
        estava_em(X, sm, segunda);
        estava_em(X, poa, terca).

    roubou_chave(bia). % ela não necessariamente roubou, mas é um predicado para
                       % verificar se o culpado possuia chave. No caso, ela tem.

% -----------------------------------------------------------------------------

    acesso(X) :- 
        no_apartamento(X),
        roubou_arma(X),
        roubou_chave(X).

% -----------------------------------------------------------------------------
% motivos:
    ciumes(X) :-
        vitima(Z),
        relacionamento(X,Y),
        relacionamento(Y,Z).
    
    ciumes(X) :-
        vitima(Z),
        relacionamento(X,Z),
        relacionamento(Z,Y),
        Y \= X.
    
    insanidade(X) :-
        insano(X).

    dinheiro(X) :-
        pobre(X).

% ----------------------------------------------------------------------------
    
    motivo(X) :-
        dinheiro(X);
        insanidade(X);
        ciumes(X).

% ------------------------------------ Solução do caso ------------------------------------------------

    assassino(X) :- motivo(X), acesso(X), !.
