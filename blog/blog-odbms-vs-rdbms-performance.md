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

Now if you want to compare modelling complexity with any approach or
see how to use PostgeSQL/ObjectDB/JPA or run benchmarks yourself, please
check out source code [here](linktorepo).

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

## Some notes on installation

gradle install task to install db's
not that objectdb is hosted on separate repo and does not include javax.persistence

## Test fixtures

TODO - actually implement all that below

Each test described below is run with following fixtures:
1. Database is prepopulated with 10M entries before each suite run and
dropped after run
2. Each test is run 1000 times to get good performance average
3. JIT compiler is off to avoid performance differences, depending on
when JVm decides to optimize/deoptimize code
4. Heap and GC is tuned to avoid any GC during test run. GC JMX bean is
monitored and test fail immediately if GC is triggered
5. Test and database processes iare set to run on specific core and OS
is restricted to run other processes on that core (core as in core, not
a hyper-thread). I would split test and database process as well, but
I have only 2 physical cores on all accessible machines. This avoids
performance differences cause by OS scheduler and other processes
running on test machine.
6. Database and test process are on the same machine so effectively
network roundtrip time is 0

## Simple numerical data case

