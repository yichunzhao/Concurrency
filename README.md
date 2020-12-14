# Concurrency


## Parallel Programs

Taking a problem, and then how to design and develop it into a parallel solution

### How to design a parallel programming
A large program that is distributed across multiple physical systems. 

Parallel Design Stages: 

* Partitioning: breaking down a problem into discrete chunks that can be distributed as multiple tasks
  * Domain decomosition: dividing the data associating with the problem; block decomposition or cyclic decomposition; 
  * Functional decomposition: decomposimg a big task according to sub-steps to carry out it in a whole. 
  * Combination of the both above. 
* Communication: some task may be compeletly independent; however, sub-tasks may need to co-ordinate with other and need to communicate with other. 
  * Point-to-piont com
  * sync. blocking com
  * async. non-blocking com
  * Overhead: computer time/time spent on communication
  * Latency: time cost message traveling from A to B(ms)
  * Bandwidth: Amount to data com per sec(GB/s)
* Agglomeration(a mass or collection of things; an assemblage) 
  * Granularity = computation/communication 
  * a fine-grained parallelism: a big number of small tasks; a good load balancing; downside: increasing com. decreasing computation-to-communication ratio 
  * a corse-grained parallelism: a small number of large tasks; high computation-to-communication ratio;  downside: inefficient load balancing. 
* Mapping: it means to find a distributed system to perform your task. 

