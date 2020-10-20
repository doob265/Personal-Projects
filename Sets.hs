--Mark Dubin
--Sets
--------------------------------------------------------
--imports list functions
import Data.List hiding (union)

--type Set that will be worked with
type Set a = [a]

--returns empty set (can't get this to print without using comand line)
empty :: Set a
empty = []

--helper function removes duplicates from sets
removeDup :: Eq a => [a] -> [a]
removeDup [] = []
removeDup (x:xs)    | x `elem` xs   = removeDup xs
                    | otherwise     = x : removeDup xs

--returns boolean of wether element is element of set or not
memberOfSet :: Eq a => Set a -> a -> Bool
memberOfSet set element = element `elem` set

--returns union of sets a and b
union :: Ord a => Set a -> Set a -> Set a
union a b = removeDup(a++b)

--sends sorted and duplicateless sets a and b to intersectionSorted
intersection  :: Ord a => Set a -> Set a -> Set a
intersection a b = intersectionSorted (removeDup(sort a)) (removeDup(sort b))

--finds and returns intersection of sets x and y
intersectionSorted :: Ord a => Set a -> Set a -> Set a
intersectionSorted (x:xs) (y:ys)
  |(x == y) = x : intersection xs ys
  |(x > y)  = intersection xs (y:ys)
  |(x < y)  = intersection (x:xs) ys
intersectionSorted _ _ = []

--returns difference of two sets
difference :: Ord a => Set a -> Set a -> Set a
difference x y = x \\ y

--sends sorted, duplicateless a and b to check lengths
equalSet :: Ord a => Set a -> Set a -> Bool
equalSet a b = equalSetLength (removeDup(sort a)) (removeDup(sort b))

--if lengths are equal, sends to check equality
equalSetLength :: Ord a => Set a -> Set a -> Bool
equalSetLength x y
  |((length x) == (length y)) = equalSetSorted x y
  |otherwise                  = False

--returns boolean denoting whether sets are equal
equalSetSorted :: Ord a => Set a -> Set a -> Bool
equalSetSorted (x:xs) (y:ys)
  |(x == y)   = equalSetSorted xs ys
  |otherwise  = False
equalSetSorted _ _ = True

--sends sorted, duplicateless a and b to check subset
subSet :: Ord a => Set a -> Set a -> Bool
subSet a b = subSetSorted (removeDup(sort a)) (removeDup(sort b))

--returns boolean denoting whether a is a subset of b 
subSetSorted :: Ord a => Set a -> Set a -> Bool
subSetSorted (x:xs) (y:ys)
  |(x == y)   = subSetSorted xs ys
  |otherwise  = False
subSetSorted _ _ = True

--maps operation a over subset x
mapSet :: Ord b => (a -> b) -> Set a -> Set b
mapSet a (x:xs) = a x : mapSet a xs
mapSet _ _ = []

--filters set x based on parameters of a
filterSet :: (a -> Bool) -> Set a -> Set a
filterSet a x = filter a x

--folds set x based on parameters of a
foldSet :: (a -> a -> a) -> a -> Set a -> a
foldSet a x = foldr a x

main = do
  --putStrLn "empty: could only get this to work when called from command line..."
  --print(empty)

  putStrLn "memberOfSet:"
  let bool = memberOfSet [1,2,5] 5
  putStrLn ("Is 5 a member of the set [1,2,5]? " ++ show(bool))
  let bool = memberOfSet [1,2,5] 4
  putStrLn ("Is 4 a member of the set [1,2,5]? " ++ show(bool))

  putStrLn "union:"
  let set = union [1,2,3] [4,5,6]
  putStrLn ("the union of [1,2,3] and [4,5,6] is " ++ show(set))
  let set = union [1,2,3] [1,2,3,4,5,6]
  putStrLn ("the union of [1,2,3] and [1,2,3,4,5,6] is " ++ show(set))
  let set = union [1] [1]
  putStrLn ("the union of [1] and [1] is " ++ show(set))

  putStrLn "intersection:"
  let set = intersection [1,2,3] [4,5,6]
  putStrLn ("the intersection of [1,2,3] and [4,5,6] is " ++ show(set))
  let set = intersection [1,2,3] [1,2,3,4,5,6]
  putStrLn ("the intersection of [1,2,3] and [1,2,3,4,5,6] is " ++ show(set))
  let set = intersection [1,2,3] [1,1,2,3,4,5,6]
  putStrLn ("the intersection of [1,2,3] and [1,1,2,3,4,5,6] is " ++ show(set))
  let set = intersection [1,2,3] [6,5,4,3,2,1]
  putStrLn ("the intersection of [1,2,3] and [6,5,4,3,2,1] is " ++ show(set))
  let set = intersection [1] [1]
  putStrLn ("the intersection of [1] and [1] is " ++ show(set))

  putStrLn "difference: "
  let set = difference [1,2,3] [1,2]
  putStrLn ("the difference between [1,2,3] and [1,2] is " ++ show(set))
  let set = difference [1,2,3] [4,5,6]
  putStrLn ("the difference between [1,2,3] and [4,5,6] is " ++ show(set))
  let set = difference [1,2,3] [1,2,3,4,5,6]
  putStrLn ("the difference between [1,2,3] and [1,2,3,4,5,6] is " ++ show(set))

  putStrLn "equalSet:"
  let bool = equalSet [1,2,3] [4,5,6]
  putStrLn ("Are [1,2,3] and [4,5,6] equal? " ++ show(bool))
  let bool = equalSet [1,2,3] [3,2,1]
  putStrLn ("Are [1,2,3] and [3,2,1] equal? " ++ show(bool))
  let bool = equalSet [1,2,3] [1,2,3]
  putStrLn ("Are [1,2,3] and [1,2,3] equal? " ++ show(bool))
  let bool = equalSet [1,2,3] [1]
  putStrLn ("Are [1,2,3] and [1] equal? " ++ show(bool))

  putStrLn "subSet:"
  let bool = subSet [1,2,3] [4,5,6]
  putStrLn ("Are [1,2,3] and [4,5,6] equal? " ++ show(bool))
  let bool = subSet [1,2,3] [3,2,1]
  putStrLn ("Are [1,2,3] and [3,2,1] equal? " ++ show(bool))
  let bool = subSet [1,2,3] [1,2,3]
  putStrLn ("Are [1,2,3] and [1,2,3] equal? " ++ show(bool))
  let bool = subSet [1,2,3] [1]
  putStrLn ("Are [1,2,3] and [1] equal? " ++ show(bool))
  print(subSet [1,2,3] [1])

  putStrLn "mapSet:"
  let set = mapSet sqrt [4,16,64]
  putStrLn ("Mapping sqrt over [4,16,64] gives " ++ show(set))
  putStrLn "filterSet:"
  let set = filterSet (<= 3) [1,2,3,4,5,6]
  putStrLn ("the values of [1,2,3,4,5,6] that are <= 3 are " ++ show(set))
  let set = filterSet (> 3) [1,2,3,4,5,6]
  putStrLn ("the values of [1,2,3,4,5,6] that are > 3 are " ++ show(set))

  putStrLn "foldSet:"
  let set = foldSet (+) 0 [1, 3, 4, 6]
  putStrLn ("Adding each value of [1, 3, 4, 6] = " ++ show(set))
  let set = foldSet (*) 1 [1, 3, 4, 6]
  putStrLn ("Multiplying each value of [1, 3, 4, 6] = " ++ show(set))