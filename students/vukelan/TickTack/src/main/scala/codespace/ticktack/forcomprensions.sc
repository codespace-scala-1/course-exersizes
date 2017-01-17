val matrix = IndexedSeq(IndexedSeq(0, 0, 1),
  IndexedSeq(0,1,0),
  IndexedSeq(0,0,1))

val corners = for (i <- 0 to 2 if i%2==0; j <- 0 to 2 if j % 2 == 0) yield (i,j)

println(corners)

for (i <- 0 to 2; j <- 0 to 2 if j==i) print(s"$i-${2-j} ")

for (i <- 1 to 3; j <- 1 to 3) print(s"$i-$j ")

for (i <- 1 to 3) yield {
  for (j <- 3 to 5) yield j
}

for (i <- 1 to 3; j <- 1 to 3 if i == j) print(s"${4-i}-$j ")

for (i <- 1 to 3; j <- 1 to 3 if j==i) print(s"$i-$j ")

for (i <- 0 to 2) print(s"${2-i} ")

for (i <- 1 to 3; j <- 1 to 3) print(s"$i-$j ")