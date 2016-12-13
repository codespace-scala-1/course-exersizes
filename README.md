Delete me
 training materials for codespace scala course

разбирайте задачки:

Задача о телефонных номерах 

http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.35.1423
http://page.mi.fu-berlin.de/prechelt/Biblio/jccpprtTR.pdf
http://page.mi.fu-berlin.de/prechelt/phonecode/

- кому нравится -- скажите

artuwok1986 

2.   Эмуляция  конструкции scope(exit) в  языке D

http://stackoverflow.com/questions/4711309/meaning-of-scope-in-d-for-a-parameter
 stackoverflow.com
Meaning of "scope" in D (for a parameter)
What does scope invoid foo(scope void* p) { } mean?(I'm not talking about scope(exit) or scope int x = 5;, but about scope as used inside a parameter list.) 
 
 
Есть еще хорошее видео Александреску на эту тему

https://www.youtube.com/watch?v=WjTrfoiB0MQ
 YouTube CppCon
CppCon 2015: Andrei Alexandrescu “Declarative Control Flow" 
 

 ```DScope {
f = new OutputStream(new File("f"))
 scope(exit){  f.close() }
f.write("somethjing")
}
```
(edited)

3.  Тесты к DScope

 
4. TickTackStrategy:  player

5. TickTackStragy:  Console

6. TickTack: View
  2 стратегии  и следить за их игрой

7.  Game of Life.  Conway

https://en.wikipedia.org/wiki/Conway's_Game_of_Life
 Wikipedia
Conway's Game of Life
The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.
The "game" is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input. One interacts with the Game of Life by creating an initial configuration and observing how it evolves, or, for advanced "players", by creating patterns with particular properties. (3KB)


8.  Binary Tree Library

- conformance to colleaction API is not needed
(except iterable)

9.    Calculator:  Interpretation of AST tree.
   +, -, *, ^, !, sqrt

10.   Parser Combinators:  Produsing AST Tree
 // ...

11.  Complex numbers
  
 // p-adic numbers

13. 
  https://en.wikipedia.org/wiki/Bargaining_problem

14. Bagraining:  Стратегия

15. Совсем простые --- parseInt
                       parseIp
                       parse Ethernet Addr
  Oб-ект с методами + тест

