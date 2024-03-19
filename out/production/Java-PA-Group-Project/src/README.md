# Descriere solutie

## Etapa 1



Avem o matrice M x N. Plecand dintr-un punct oarecarare,
dorind sa luam `ownership` asupra fiecarei pozitii din matrice,
adica sa parcurgem matricea in intregime, intr-un numar cat mai mic de pasi,
profitand de faptul ca putem muta mai multe piese in acelasi timp.


Vom muta piesele de la marginea `insulitei` create

ex:

init
0 0 0 0
0 0 0 0
0 0 1 0
0 0 0 0

pas1
0 0 0 0
0 0 0 0
0 1 1 0
0 0 0 0

pas2
0 0 0 0
0 1 1 0
0 1 1 0
0 0 0 0


pas3
0 0 0 0
1 1 1 1
1 1 1 1
0 0 0 0


pas3
1 1 1 1
1 1 1 1
1 1 1 1
1 1 1 1


`
Bacteria, de la una o pornim
Bacteria, si apoi doua devenim
Si din doua inca doua
Pana la 99
`

La fiecare pas, vom pleca din elementele care au cei mai putini vecini nevizitati.

O idee ar fi: daca lungime insulitei este mai mare ca inaltimea ei, sa mergem sus/jos