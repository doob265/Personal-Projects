--Mark Dubin
--Calculator
--------------------------------------------------------
--new data type Expr for Values (Ints), Operations consisting of two Exprs
data Expr       = Val Int | Add Expr Expr | Sub Expr Expr | Mult Expr Expr | Div Expr Expr | Mod Expr Expr | Pow Expr Expr

--new data type Op to evaluate Operations
data Op         = EVALADD Expr | EVALSUB Expr | EVALMULT Expr | EVALDIV Expr | EVALMOD Expr | EVALPOW Expr | ADD Int | SUB Int | MULT Int | DIV Int | MOD Int | POW Int

--new type for control stack, list of Ops
type Cont       = [Op]

--takes expr, returns int by calling eval with empty stack
value           :: Expr -> Int
value e         = eval e[]

--evalutes expr based on given Op
eval              :: Expr -> Cont -> Int
eval (Val n) c    = exec c n
eval (Add x y) c  = eval x (EVALADD y : c)
eval (Sub x y) c  = eval x (EVALSUB y : c)
eval (Mult x y) c  = eval x (EVALMULT y : c)
eval (Div x y) c  = eval x (EVALDIV y : c)
eval (Mod x y) c  = eval x (EVALMOD y : c)
eval (Pow x y) c  = eval x (EVALPOW y : c)

--exectures evaluation based on control stack
exec                      :: Cont -> Int -> Int
exec[]n                   = n   
exec(EVALADD y : c) n     = eval y(ADD n : c)
exec(EVALSUB y : c) n     = eval y(SUB n : c)
exec(EVALMULT y : c) n    = eval y(MULT n : c)
exec(EVALDIV y : c) n    = eval y(DIV n : c)
exec(EVALMOD y : c) n    = eval y(MOD n : c)
exec(EVALPOW y : c) n    = eval y(POW n : c)
exec(ADD n : c) m         = exec c(n + m)
exec(SUB n : c) m         = exec c(n - m)
exec(MULT n : c) m        = exec c(n * m)
exec(POW n : c) m        = exec c(n ^ m)

--these two need special cases when m is 0, for this implementation, / by 0 == 0, % by 0 == -1
exec(DIV n : c) m
  |m == 0     = 0
  |otherwise  = exec c(n `div` m)
exec(MOD n : c) m
  |m == 0     = -1
  |otherwise  = exec c (n `mod` m)

main = do
    --add test
    let res = value(Add(Add(Val 2)(Val 3))(Val 4))
    putStrLn ("((2 + 3) + 4) = " ++ show(res))

    --sub test
    let res = value(Sub(Sub(Val 2)(Val 3))(Val 4))
    putStrLn ("((2 - 3) - 4) = " ++ show(res))

    --mult test
    let res = value(Mult(Mult(Val 2)(Val 3))(Val 4))
    putStrLn ("((2 * 3) * 4) = " ++ show(res))

    --pow test
    let res = value(Pow(Pow(Val 2)(Val 3))(Val 4))
    putStrLn ("((2 ^ 3) ^ 4) = " ++ show(res))

    --div test
    let res = value(Div(Div(Val 8)(Val 2))(Val 2))
    putStrLn ("((8 / 2) / 4) = " ++ show(res))

    --mod test
    let res = value(Mod(Mod(Val 46)(Val 22))(Val 4))
    putStrLn ("((46 % 22) % 4) = " ++ show(res))

    --combined test
    let res = value(Mod(Pow(Div(Mult(Sub(Val 5)(Val 4))(Val 10))(Val 2))(Val 3))(Val 2))
    putStrLn ("(((((5 - 4) * 10) / 2) ^ 3) % 4) = " ++ show(res))
