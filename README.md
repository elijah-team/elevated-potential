Elijah elevated-potential
==========================

Elijah is:

- ... a high-level language built to explore code generation and other interesting techniques.
- ... a historical curiosity.
- ... meant to be easy to use standalone or with existing projects.
- ... free software (LGPL) intended for use on all systems, aka Linux.
- ... philosophically opposed to semicolons.
- ... obsessed with curly braces and brackets.

`elevated-potential` is:

- ... implemented in Java (17, for now; 8 is great and Graal is you're paal)
- ... of the impression that build systems don't matter (tldr maven for now)
- ... mortal enemies of Scala and Kotlin
- ... philosophically excited about Clojure (and Xtend nad EMF/MBSE)
- ... also secretly jealous of Lombok and AOP


Instructions
-------------

[https://github.com/elijah-team/elevated-potential](https://github.com/elijah-team/elevated-potential)

```shell
git clone https://github.com/elijah-team/elevated-potential -b rolling
cd elevated-potential
sdk install mvnd
mvnd clean test
# or 
nix-shell -p maven gradle jdk17 --pure --command "mvn clean test"
```

See: https://gitlab.com/elijah-team/crft-elevated-potential.git

Goals
------

- Fiddle with Github `.workflows`
- Work on `almost`

Goals (2024)
-------------

1. [G6][6] Delete architecture with LCM

2. [G7][7] Clojure (Promesa, Missionary, M*)

3. [G1][1] Make progress towards results (cf `meson-demo`)

4. [G2][2] Make it "fun" to look at (`datalog-ts`, Glamorous Toolkit)

Lineage
--------

- `almost`

- ??


Revised Goals
--------------

- ...


[1]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G1.md
[2]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G2.md
[3]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G3.md
[4]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G4.md
[6]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G6.md
[7]: https://gitlab.com/elijah-team/documentation/petal-to-the-medal/-/blob/main/ginitiatives/G7.md
