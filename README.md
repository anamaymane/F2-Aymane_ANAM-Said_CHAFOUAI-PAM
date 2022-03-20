# Binôme

- Aymane ANAM [F2]
- Said CHAFOUAI [F2]

# API utilisée

L'API utilisée permet de localiser les articles et les gros titres de l'actualité à partir de sources d'actualités et de blogs sur le Web. L'API expose ces données sous format JSON.

Lien de l'api: [https://newsapi.org/?ref=apilist.fun](https://newsapi.org/?ref=apilist.fun)

# Fonctionnalités implémentées

- Notre application contient deux pages principales, qui sont la liste des items et la page détail. Pour l'affichage des deux pages, on a utilisé la fonctionnalité Navigation avec fragments.

- Récupération des données via l'API des news de manière asynchrones, et cela en utilisant la bibliothèque _ktor_. Afin d'éviter d'avoir des appels bloquants lors de l'interrogation de l'API, on a utilisé le principe du LiveData pour l'affichage des données au niveau du recycler view.

- Afin de garder les données en mémoire et éviter de faire des appels inutiles de l'API lors d'une rotation par exemple, on a utilisé le concept du ViewModel qui permet de garder les données en mémoire indépendamment du cycle de vie de l'activité.

- Pour faire en sorte que notre application soit utilisable lorsqu'il n'y a pas d'accès internet, on persiste à chaque appel de l'API les nouvelles données reçues dans la base de données locale. Ces données seront affichées à l'utilisateur lorsqu'il est hors ligne. À noter que pour faciliter l'utilisation de la base de données SQLite, on a travaillé avec l'ORM Room.