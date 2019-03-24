import Data.Char

-- 1.

isVowel :: Char -> Bool
isVowel r = length (filter (\x -> x == r) "aeiouAEIOU") > 0

-- 2.

addComma :: [String] -> [String]
addComma list = map (\c -> c ++ ",")list

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

botaTracosCL :: String -> String
botaTracosCL string = map (\c -> if c /= ' ' then '-' else c)string

-- Sem lambda

botaTracosSL :: String -> String
botaTracosSL string =  map botaTracosAux string


botaTracosAux :: Char -> Char
botaTracosAux c = if c /= ' ' then '-' else c

-- 6.

firstName :: String -> String
firstName r = takeWhile (/= ' ') r

-- 7.

isInt :: String -> Bool
isInt string = length (filter isDigit string) > 0

-- 8.

lastName :: String -> String
lastName  r = filter (/= ' ') (dropWhile (/= ' ') r)

-- 9.

userName :: String -> String
userName name = map toLower ([head name] ++ lastName name)   

-- 10.

encodeName :: String -> String
encodeName name = map encodeNameAux (map toLower name)

encodeNameAux :: Char -> Char
encodeNameAux c
    | c == 'a' = '4'
    | c == 'i' = '2'
    | c == 'e' = '3'
    | c == 'u' = '0'
    | c == 'o' = '1'
    | otherwise = c

betterEncodeNameAux :: Char -> String
betterEncodeNameAux c
    | c == 'a' = "4"
    | c == 'i' = "2"
    | c == 'e' = "3"
    | c == 'u' = "00"
    | c == 'o' = "1"
    | otherwise = [c]

-- 11.

betterEncodeName :: String -> String
betterEncodeName name = concatMap betterEncodeNameAux (map toLower name)

-- 12.

limitTenAux :: String -> String
limitTenAux str = take 10 (str ++ "..........")

limitTen :: [String] -> [String]
limitTen string = map limitTenAux string