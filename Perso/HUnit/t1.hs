import Data.Char
import HUnit.Test

-- 1. Crie uma função isVowel :: Char -> Bool que verifique se um caracter é uma vogal ou não.

isVowel :: Char -> Bool
isVowel r = length (filter (\x -> x == r) "aeiouAEIOU") > 0

-- 2. Escreva uma função addComma, que adicione uma vírgula no final de cada string contida numa lista.

addComma :: [String] -> [String]
addComma list = map (++ ",")list

-- 3. Crie uma função htmlListItems :: [String] -> [String], que receba uma lista de strings e retorne 
-- outra lista contendo as strings formatadas como itens de lista em HTML. Resolva este exercício COM 
-- e SEM funções anônimas (lambda). 

--com lambda
htmlListItems :: [String] -> [String]
htmlListItems list = map (\s -> "<LI>" ++ s ++ "</LI>")list


--sem lambda
htmlListItemsSL :: [String] -> [String]
htmlListItemsSL list = map htmlListItemsSLAux list

htmlListItemsSLAux :: String -> String
htmlListItemsSLAux str = "<LI>" ++ str ++ "</LI>"

-- 4. Defina uma função que receba uma string e produza outra retirando as vogais, conforme os exemplos
-- abaixo. Resolva este exercício COM e SEM funções anônimas (lambda).

-- Sem Lambda

noVowelSL :: String -> String
noVowelSL r = filter (`notElem` "aeiouAEIOU") r

-- Com lambda:

noVowelsCL :: String -> String
noVowelsCL str = filter (\r -> length (filter (\x -> x == r) "aeiouAEIOU") == 0) str  

-- 5. Defina uma função que receba uma string, possivelmente contendo espaços, e que retorne outra string
--  substituindo os demais caracteres por '-', mas mantendo os espaços. Resolva este exercício COM e SEM 
-- funções anônimas (lambda). 

-- Com lambda

putHyphen :: String -> String
putHyphen string = map (\c -> if c /= ' ' then '-' else c) string

-- Sem lambda

putHyphen2 :: String -> String
putHyphen2 string =  map putHyphen2Aux string

putHyphen2Aux :: Char -> Char
putHyphen2Aux c = if c /= ' ' then '-' else c

-- 6. Escreva uma função firstName :: String -> String que, dado o nome completo de uma pessoa, obtenha seu 
-- primeiro nome. Suponha que cada parte do nome seja separada por um espaço e que não existam espaços no 
-- início ou fim do nome. 

firstName :: String -> String
firstName str = takeWhile (/= ' ') str

-- 7. Escreva uma função isInt :: String -> Bool que verifique se uma dada string só contém dígitos de 0 a 9. 

isInt :: String -> Bool
isInt str = length (filter isDigit str) > 0

-- 8. Escreva uma função lastName :: String -> String que, dado o nome completo de uma pessoa, obtenha seu 
-- último sobrenome. Suponha que cada parte do nome seja separada por um espaço e que não existam espaços 
-- no início ou fim do nome.

lastName :: String -> String
lastName str = filter (/= ' ') (dropWhile (/= ' ') str)

-- 9. Escreva uma função userName :: String -> String que, dado o nome completo de uma pessoa, crie um nome de
--  usuário (login) da pessoa, formado por: primeira letra do nome seguida do sobrenome, tudo em minúsculas.

userName :: String -> String
userName name = map toLower ([head name] ++ lastName name) 

-- 10. Escreva uma função encodeName :: String -> String 
-- que substitua vogais em uma string, conforme o esquema a seguir:
--  a = 4, e = 3, i = 2, o = 1, u = 0.

encodeName :: String -> String 
encodeName str = map encodeName2 (map toLower str)

encodeName2 :: Char -> Char
encodeName2 c
    | c == 'a' = '4' 
    | c == 'e' = '3'
    | c == 'i' = '2'
    | c == 'o' = '1'
    | c == 'u' = '0'
    | otherwise = c

-- 11. Escreva uma função betterEncodeName :: String -> String que substitua 
-- vogais em uma string, conforme este esquema:
-- a = 4, e = 3, i = 1, o = 0, u = 00.

betterEncodeName :: String -> String
betterEncodeName str = concatMap betterEncodeName2 (map toLower str)

betterEncodeName2 :: Char -> String
betterEncodeName2 c
    | c == 'a' = "4" 
    | c == 'e' = "3"
    | c == 'i' = "2"
    | c == 'o' = "1"
    | c == 'u' = "00"
    | otherwise = [c]

-- 12. Dada uma lista de strings, produzir outra lista com strings de 10 caracteres, usando o seguinte esquema:
--  strings de entrada com mais de 10 caracteres são truncadas, strings com até 10 caracteres são completadas com
--  '.' até ficarem com 10 caracteres.

limitTen :: [String] -> [String]
limitTen list = map (take 10) (map (++ "..........") list)