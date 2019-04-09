import Text.Printf

type Point   = (Float,Float)
type Rect    = (Point,Float,Float)
type Circle  = (Point,Float)

-------------------------------------------------------------------------------
-- Paletas
-------------------------------------------------------------------------------

rgbPalette :: Int -> [(Int,Int,Int)]
rgbPalette n = take n $ cycle [(255,0,0),(0,255,0),(0,0,255)]

raimbowPalette :: Int -> [(Int,Int,Int)]

purplePalette :: Int -> [(Int,Int,Int)]
purplePalette n = [(80+i*4 , 0, 80+i*4) | i <- [0..n] ]

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
--                                Caso 1
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
          crects   = 10
          palette  = purplePalette (lrects*crects)
          (width,height) = (1500,500)

-----------------------------------------------------------------------------
--                                Caso 2
-----------------------------------------------------------------------------

genCircles1 :: Int -> Float -> [Circle]
genCircles1 n r = [((xc + radium*cos m, yc + radium*sin m), r) | m <- [0,20..360]]
    where xc = 90
          yc = 80
          radium = 40 

genCase2 :: IO ()
genCase2 = do
    writeFile "case2.svg" $ svgstrs2
    where svgstrs2 = svgBegin width height ++ svgfigs ++ svgEnd
          svgfigs  = svgElements svgCircle circles (map svgStyle palette)
          circles  = genCircles1 n radium
          radium   = 10
          n        = 11
          palette  = rgbPalette n
          (width,height) = (1500,500)

-----------------------------------------------------------------------------
--                                Caso 3
-----------------------------------------------------------------------------

--genCase3 :: IO()
--genCase3 = do

-----------------------------------------------------------------------------
--                                Caso 4
-----------------------------------------------------------------------------

--genCase4 :: IO()
--genCase4 = do