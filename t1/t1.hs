import Data.Char

-- 1.

isVowel :: Char -> Bool
isVowel r = length (filter (\x -> x == r) "aeiouAEIOU") > 0

-- 2.

addComma :: [String] -> [String]
addComma list = map (++ ",")list

-- 3.

--com lambda
htmlListItems :: [String] -> [String]
htmlListItems list = map (\s -> "<LI>" ++ s ++ "</LI>")list


--sem lambda
htmlListItemsSL :: [String] -> [String]
htmlListItemsSL list = map htmlListItemsSLAux list

htmlListItemsSLAux :: String -> String
htmlListItemsSLAux str = "<LI>" ++ str ++ "</LI>"


-- 4.

-- Sem Lambda

noVowelSL :: String -> String
noVowelSL r = filter (`notElem` "aeiouAEIOU") r

-- Com lambda:

noVowelsCL :: String -> String
noVowelsCL str = filter (\r -> length (filter (\x -> x == r) "aeiouAEIOU") == 0) str  

-- 5.

-- Com lambda

putHyphen :: String -> String
putHyphen string = map (\c -> if c /= ' ' then '-' else c) string

-- Sem lambda

putHyphen2 :: String -> String
putHyphen2 string =  map putHyphen2Aux string

putHyphen2Aux :: Char -> Char
putHyphen2Aux c = if c /= ' ' then '-' else c

-- 6.

firstName :: String -> String
firstName str = takeWhile (/= ' ') str

-- 7. 

isInt :: String -> Bool
isInt str = length (filter isDigit str) > 0

-- 8.

lastName :: String -> String
lastName str = filter (/= ' ') (dropWhile (/= ' ') str)

-- 9.

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