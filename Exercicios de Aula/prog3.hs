--1

add10toall :: [Int] -> [Int]
add10toall list = [x + 10| x <- list]

--2

multN :: Int -> [Int] -> [Int]
multN n lista = [x*n | x <- lista]


--3
applyExpr :: [Int] -> [Int]
applyExpr lista = [3*x+2 | x <- lista]

--4
addSuffix :: String -> [String] -> [String]
addSuffix suffix strList = [x ++ suffix | x <- strList]

--5
selectgt5 :: [Int] -> [Int]
selectgt5 list = [x | x <- list, x > 5]

--6
sumOdds :: [Int] -> Int
sumOdds list = sum ([x | x <- list, (odd x) == True ])

--7

selectExpr :: [Int] -> [Int]
selectExpr list = [x | x <- list, x > 20, x < 50, (even x) == True]

--8

countShorts :: [String] -> Int
countShorts strList = length [x | x <- strList, length x < 5]

--9

calcExpr :: [Float] -> [Float]
calcExpr list = [y | y <- [x^2/2 | x <- list], y > 10]

--10

trSpaces :: String -> String
trSpaces str = [if x /= ' ' then '-' else x | x <- str]

--11 por fazer

--12

selectSnd :: [(Int,Int)] -> [Int]
selectSnd tupleList = [snd tuple | tuple <- tupleList]

--13

dotProd :: [Int] -> [Int] -> Int
dotProd list1 list2 = sum ([(\(a,b) -> a*b ) x | x <-(zip list1 list2)])

--14

genRects :: Int -> (Int,Int) -> [(Float,Float,Float,Float)]
genRects n tuple = [(fromIntegral(fst (x)) + 5.5 * (fromIntegral y), fromIntegral (snd (x)) + 5.5 * (fromIntegral y), 5.5, 5.5) | x <- [tuple], y <- [0 .. (n-1)]]

--15

dotProd' :: [Int] -> [Int] -> Int
dotProd' list1 list2 = sum (zipWith (*) [fst x | x <- (zip list1 list2)] [snd y | y <- (zip list1 list2)])