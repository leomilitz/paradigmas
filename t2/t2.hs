-------------------------------------------------------
-- Autor:        Leonardo Militz                     --
-- Disciplina:   Paradigmas da Programação           --
-- Professor:    Andrea Schwertner Charão            --
-- Créditos:     Andrea Schwertner Charão            --
-------------------------------------------------------

import Text.Printf

-------------------------------------------------------------------------------
-- Types
-------------------------------------------------------------------------------

type Point   = (Float,Float)
type Rect    = (Point,Float,Float)
type Circle  = (Point,Float)

-------------------------------------------------------------------------------
-- Palettes
-------------------------------------------------------------------------------

rgbPalette :: Int -> [(Int,Int,Int)]
rgbPalette n     = take n $ cycle [(255,0,0),(0,255,0),(0,0,255)]

raimbowPalette :: Int -> [(Int,Int,Int)]
raimbowPalette n = [(255-var*i, 0, var*i) | i <- [0..n `div` 3]] ++ 
                   [(0, var*i, 255-var*i) | i <- [0..n `div` 3]] ++
                   [(var*i, 255 - var*i,0) | i <- [0..n `div` 3]]
    where var = 255 `div` n*3

customPalette :: Int -> [(Int, Int, Int)]
customPalette n  = [(255 - var*i, 0, var*i) | i <- [0..div n 2]] ++
                   [(90, var*i, 255 - var*i) | i <- [0..div n 2]]
    where var = 255 `div` n*2

redPalette :: Int -> [(Int,Int,Int)]
redPalette n     = [(255 - var*i, 0, 0) | i <- [n, n-1..0]]
    where var = 255 `div` n

greenPalette :: Int -> [(Int,Int,Int)]
greenPalette n   = [(0, 255 - var*i, 0) | i <- [n,n-1..0]]
    where var = 255 `div` n

bluePalette :: Int -> [(Int,Int,Int)]
bluePalette n    = [(0, 0, 255 - var*i) | i <- [n, n-1..0]]
    where var = 255 `div` n

-------------------------------------------------------------------------------
--  SVG
-------------------------------------------------------------------------------


svgBegin :: Float -> Float -> String
svgBegin width height = printf "<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n" width height

svgEnd :: String
svgEnd = "</svg>"

svgRect :: Rect -> String -> String 
svgRect ((x,y),w,h) style = 
    printf "<rect x='%.3f' y='%.3f' width='%.2f' height='%.2f' style='%s' />\n" x y w h style

svgCircle :: Circle -> String -> String
svgCircle ((x,y), r) style =
    printf "<circle cx='%.3f' cy='%.3f' r='%.3f' style='%s' />\n" x y r style

svgStyle :: (Int,Int,Int) -> String
svgStyle (r,g,b) = printf "fill:rgb(%d,%d,%d); mix-blend-mode: screen;" r g b

svgElements :: (a -> String -> String) -> [a] -> [String] -> String
svgElements func elements styles = concat $ zipWith func elements styles


-----------------------------------------------------------------------------
--                                Case 1
-----------------------------------------------------------------------------

genRects :: Int -> Int -> [Rect]
genRects l c = [((m*(w+gap), n*(h + 2*gap)),w,h) | m <- [0..fromIntegral (c-1)], n <- [0..fromIntegral(l-1)]]
    where gap   = 5
          (w,h) = (50,50) -- Lado e altura do retângulo

genCase1 :: IO ()
genCase1 = do
    writeFile "case1.svg" $ svgstrs1
    where svgstrs1 = svgBegin width height ++ svgfigs ++ svgEnd
          svgfigs  = svgElements svgRect rects (map svgStyle palette)
          rects    = genRects l c
          l        = 12  -- Quantidade de linhas da matriz
          c        = 20 -- Quantidade de colunas da matriz
          palette  = customPalette (l*c)
          (width,height) = (fromIntegral (60*c), fromIntegral (70*l))

-----------------------------------------------------------------------------
--                                Case 2
-----------------------------------------------------------------------------

degreeToRad :: Float -> Float
degreeToRad n = n*pi/180

genCircles1 :: Int -> Float -> [Circle]
genCircles1 n r = [((xc + radium*(cos (degreeToRad (m*angle))), yc + radium*(sin (degreeToRad (m*angle)))), r) | m <- [0..fromIntegral (n-1)]]
    where xc     = w / 2
          yc     = h / 2
          radium = fromIntegral(n*4)
          angle  = fromIntegral (360 `div` n)
          (w,h)  = (fromIntegral (10*n),fromIntegral (12*n))

genCase2 :: IO ()
genCase2 = do
    writeFile "case2.svg" $ svgstrs2
    where svgstrs2 = svgBegin width height ++ svgfigs ++ svgEnd
          svgfigs  = svgElements svgCircle circles (map svgStyle palette)
          circles  = genCircles1 n radium
          radium   = 10 -- Raio dos círculos menores
          n        = 40 -- Número de círculos desejados
          palette  = raimbowPalette n
          (width,height) = (fromIntegral (10*n),fromIntegral (12*n))

-----------------------------------------------------------------------------
--                                Case 3
-----------------------------------------------------------------------------

gen3Circles :: Float -> Float -> Float -> [Circle]
gen3Circles xc yc r = [if m /= 0.5 then ((xc + m*r,yc), r) else ((xc + m*r,yc - r), r) | m <- [0, 0.5, 1]]

gen3CirclesMatrix :: Int -> Int -> Float -> [Circle]
gen3CirclesMatrix c l r = concat [gen3Circles (pos*gap) (gap*m) r | pos <- [1.. fromIntegral c], m <- [1..fromIntegral l]]
    where gap = r*3

genCase3 :: IO ()
genCase3 = do
    writeFile "case3.svg" $ svgstrs3
    where svgstrs3 = svgBegin width height ++ svgFigs ++ svgEnd
          svgFigs  = svgElements svgCircle circles (map svgStyle palette)
          circles  = gen3CirclesMatrix c l radium
          l        = 4    --  Número de linhas da matriz desejado
          c        = 10   -- Número de colunas da matriz desejado
          radium   = 50   -- Raio de cada círculo
          radiumCopy = 50 -- Cópia do raio de cada círculo, usado para não ter conflito de tipos ao definir o tamanho e largura da tela. 
          palette  = rgbPalette (l*c*3)
          (width, height) = (fromIntegral (radiumCopy*4*c),fromIntegral (radiumCopy*4*l))

-----------------------------------------------------------------------------
--                                Case 4
-----------------------------------------------------------------------------

circleSinusoid :: Int -> String -> Float -> [Circle]
circleSinusoid n color r = [((xc + 1.5*r*y, yc + 1.5*r*sin (degreeToRad (y*angle))), r) | y <- [0..fromIntegral(n-1)]]
    where xc    = 50
          yc
            | color == "red"   = 100
            | color == "green" = 200
            | otherwise        = 300
          angle = 40

genCase4 :: IO ()
genCase4 = do
    writeFile "case4.svg" $ svgstrs4
    where svgstrs4  = svgBegin width height ++ svgFigsR ++ svgFigsG ++ svgFigsB ++ svgEnd
          svgFigsR  = svgElements svgCircle circlesR (map svgStyle paletteR)
          svgFigsG  = svgElements svgCircle circlesG (map svgStyle paletteG)
          svgFigsB  = svgElements svgCircle circlesB (map svgStyle paletteB)
          circlesR  = circleSinusoid c "red"   radium
          circlesG  = circleSinusoid c "green" radium
          circlesB  = circleSinusoid c "blue"  radium
          c         = 30 -- Quantidade  de círculos em cada senóide
          radium    = 20 -- Raio de cada círculo da senóide
          radiumCopy= 20 -- Cópia do raio de cada círculo da senóide, visando não acontecer conflito de tipos na definição da largura e altura da tela
          paletteR  = redPalette c
          paletteG  = greenPalette c
          paletteB  = bluePalette c
          (width, height) = (fromIntegral  c*radiumCopy*2, 400)