val non = None
non.isEmpty


val cells = for (i <- 0 to 2 if i % 2 == 0; j <- 0 to 2 if j % 2 == 0) yield {
  (i, j)
}

def mustBeVector(cells: IndexedSeq[(Int,Int)]):IndexedSeq[(Int,Int)] = {
  for (i <- cells if i._1 == 0) yield i
  }

mustBeVector(cells)


val corners = for (i <- 0 to 2 if i % 2 == 0; j <- 0 to 2 if j % 2 == 0) yield {
  (i, j)
}

for (i <- corners) print(s"$i")


for (i <- 0 to 2 if i % 2 == 0; j <- 0 to 2 if j % 2 == 0) yield {
  (i, j)
}
