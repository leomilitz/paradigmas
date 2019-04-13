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
raimbowPalette n = take n $ cycle [(255,0,0), (255,130,0), (249,255,0), (119,255,0), 
                                   (0,255,10), (0,255,140), (0,239,255), (0,109,255), 
                                   (20,0,255), (150,0,255), (255,0,229), (255,0,99)]

redPalette :: Int -> [(Int,Int,Int)]
redPalette n     = [(80+i*4, 0, 0) | i <- [0..n]] 

greenPalette :: Int -> [(Int,Int,Int)]
greenPalette n   = [(0, 80+i*4, 0) | i <- [0..n]] 

bluePalette :: Int -> [(Int,Int,Int)]
bluePalette n    = [(0, 0, 80+i*4) | i <- [0..n]] 

purplePalette :: Int -> [(Int,Int,Int)]
purplePalette n  = [(80 +(i*n `div` n*3), 0, 80 + (i*n `div` n*3)) | i <- [0..n]]

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

genRectsInLine :: Int -> Int -> [Rect]
genRectsInLine l c = concat [[((m*(w+gap), n*(h + 2*gap)),w,h) | m <- [0..fromIntegral (c-1)]] | n <- [0..fromIntegral(l-1)]]
    where gap   = 10
          (w,h) = (50,50)

genCase1 :: IO ()
genCase1 = do
    writeFile "case1.svg" $ svgstrs1
    where svgstrs1 = svgBegin width height ++ svgfigs ++ svgEnd
          svgfigs  = svgElements svgRect rects (map svgStyle palette)
          rects    = genRectsInLine lrects crects --linhas colunas
          lrects   = 5
          crects   = 12
          palette  = purplePalette (lrects*crects)
          (width,height) = (fromIntegral (60*crects), fromIntegral (70*lrects))

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
          radium   = 10
          n        = 12
          palette  = raimbowPalette n
          (width,height) = (fromIntegral (10*n),fromIntegral (12*n))

-----------------------------------------------------------------------------
--                                Case 3
-----------------------------------------------------------------------------

gen3Circles :: Float -> Float -> Float -> [Circle]
gen3Circles xc yc r = [if m /= 0.5 then ((xc + m*r,yc), r) else ((xc + m*r,yc - r), r) | m <- [0, 0.5, 1]]

gen3CirclesMatrix :: Int -> Int -> Float -> [Circle]
gen3CirclesMatrix c l r = concat [(concat [gen3Circles (pos*gap) (gap*m) r | pos <- [1.. fromIntegral c]]) | m <- [1..fromIntegral l]]
    where gap = r*3

genCase3 :: IO ()
genCase3 = do
    writeFile "case3.svg" $ svgstrs3
    where svgstrs3 = svgBegin width height ++ svgFigs ++ svgEnd
          svgFigs  = svgElements svgCircle circles (map svgStyle palette)
          circles  = gen3CirclesMatrix c l radium
          l        = 4
          c        = 10
          radium   = 50
          radiumCopy = 50
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
          circlesR  = circleSinusoid c "red" radium -- red == 1
          circlesG  = circleSinusoid c "green" radium
          circlesB  = circleSinusoid c "blue" radium
          c         = 30
          radium    = 20
          radiumCopy= 20
          paletteR  = redPalette c
          paletteG  = greenPalette c
          paletteB  = bluePalette c
          (width, height) = (fromIntegral  c*radiumCopy*2, 400)