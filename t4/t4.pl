% arma:     bastão de baseball , martelo
% dia:      quinta, quarta, sexta
% lugares:  SM, POA
% motivo:   dinheiro, ciúme ou insanidade.
% vítima:   anita

% 1. O bastão de baseball que foi roubado do amigo pobre de Anita, Bernardo, na quinta-feira em 
%    Porto Alegre ou na quarta-feira em Santa Maria; OU

% 2. O martelo que foi roubado da caixa de ferramentas do apartamento na quarta ou na quinta-feira.

% 3. o agressor devia ser alguém que dividia o apartamento com Anita.

% 4. o crime aconteceu na sexta ou na quinta-feira



%------------------------- integrantes do caso e suas peculiaridades ------------------------------------

% Insanos:
    
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

% Relacionamentos:
    
    relacionamento(bernardo, bia).
    relacionamento(bia, bernardo).

    ex_relacionamento(bernardo, caren).
    ex_relacionamento(caren, bernardo).

    ex_relacionamento(anita, pedro).
    ex_relacionamento(pedro, anita).

    ex_relacionamento(pedro, alice).
    ex_relacionamento(alice, pedro).

    ex_relacionamento(henrique, alice).
    ex_relacionamento(alice, henrique).

    ex_relacionamento(henrique, maria).
    ex_relacionamento(maria, henrique).

    ex_relacionamento(maria, adriano).
    ex_relacionamento(adriano, maria).

    ex_relacionamento(adriano, caren).
    ex_relacionamento(caren, adriano).

% ----------------------------- Predicados para a solução do caso ---------------------------------------

% acesso(X) :- 
    % X Pode ter roubado a arma.
    % X Pode ter roubado a chave.
    % X Estava no apartamento no momento do crime.

% ------------------------------------ Solução do caso ------------------------------------------------

assassino(X) :- motivo(X), acesso(X).
