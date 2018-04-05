# ODBMS vs RDMBS performance

Recently during little research I've (spotknulsja) about new database
name: ObjectDB. Two thing intrigued me with this:
1. I never saw true ODMBS in a wild. It looked like industry widely
adopted RDMBS with JPA/Hibernate on client side (which makes RDMBS
interface look like ODBMS) and everyone is so happy about it that no
one challanges status quo
2. It shouts how performant it is strait in your face on the
[homepage](link) ([archived version](link) in case link breaks)

Now in this article we will try to compare modelling complexity and
performance of RDMBS, RDBMS+JPA and ODBMS. Theoretically ODMBS and
RDBMS+JPA must have same client implementation so it's good to know
performance impacts of implementations with same modelling complexity.
I added RDBMS in this list to find out if it can be better to trade
expressiveness of JPA in favor for SQL to make queries faster.

## O-, P-, Q-, RDBMS?

The difference between ODBMS and RDBMS is the way how data is
stored and treated inside database.

RDBMS represents data entry as column values, which might have
connected data in other database tables (hence title Relational).
Relations between entries are modeled with entry values. RDBMS are
very user-friendly in table-like structured data (the one you would
put into spreadsheet to aggregate and analyze). It's easy to develop
in case of static schema, simple data and relations.

ODBMS represents data as an object (hence title Object, who could have
guessed?). Relations between entries are expressed with classes
which helps easily express complex relationships. ODMBS interface is
more fit to model data that you would express in a block diagram style
to analyze. It's easy to develop with changing schema and naturally
expresses complex relations (no joins).

## Simple numerical data case

TODO - benchmark code time, database time and network time
