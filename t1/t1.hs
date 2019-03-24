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
putHyphen2 string =  map botaTracosAux string

putHyphen2Aux :: Char -> Char
putHyphen2Aux c = if c /= ' ' then '-' else c
