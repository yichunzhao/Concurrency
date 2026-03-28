# Java Concurrency Learning Repository

This repository contains my practice and learning materials on the topic of Java Concurrency. The code is structured around various core concepts and advanced mechanisms provided by the Java concurrent API.

## Topics Covered

### 1. Threads Basics (`Threads/`)
- Creating and managing threads
- Daemon threads (`KitchenDemonDemo`)
- Data races and basic synchronization (`DataRaceDemo`)
- Atomic variables (`AtomicVariableDemo`)

### 2. Thread Pools and Executors (`ThreadPool/`)
- Using `ExecutorService` (`ExecutorServiceSingleThread`, `UsingExecutorService`)
- Exploring `Callable` and `Future` for async execution (`DemoUsingFutureCallable`)
- Scheduled thread pools (`ScheduledThreadPool`)
- Cached vs Fixed thread pools

### 3. Synchronization & Locks (`Lock/`, `synchronisation/`)
- **Explicit Locks**: `ReentrantLock` (`ReentrantLockDemo`), `ReadWriteLock` (`ReadWriteLockDemo`)
- Methods like `tryLock()` (`TryLockDemo`)
- Avoiding abandoned locks

### 4. Concurrent Collections (`ConcurrentCollection/`, `SyncCollections/`)
- **Thread-safe Collections**: `ConcurrentMap`, `CopyOnWrite` collections, `BlockingQueue`
- **Synchronized Collections**: `SyncMap`, `SyncSet`
- Avoiding `ConcurrentModificationException`

### 5. Race Conditions and Coordination (`racecondition/`)
- **CountDownLatch**: Synchronizing one or more threads to wait for a set of operations (`CountDownLatchDemo`)
- Barriers and Latches

### 6. The Producer-Consumer Pattern (`ProducerConsumer/`)
- Implementation using basic Mutexes
- Implementation using Semaphores

### 7. Liveness (`liveness/`)
- Understanding and simulating **Deadlocks**
- Dealing with abandoned locks

### 8. Asynchronous Tasks & Parallelism (`asynctasks/`, `parallel/`)
- Divide and Conquer approach (`divideconquer/`)
- Futures
- Parallel algorithms like Sequential Matrix Multiplier

### 9. Volatile & Atomic Variables (`VolatileAndAtomicVar/`)
- Core Java memory model concepts
- Ensuring visibility with `volatile`
- Lock-free thread-safety with `Atomic` classes

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

