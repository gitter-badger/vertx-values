package vertxval.performance.benchmarks;

import vertxval.performance.Module;
import io.vertx.core.Vertx;
import org.openjdk.jmh.annotations.*;

import static vertxval.performance.Functions.await20segForEnding;
import static vertxval.performance.Module.*;

public class MyBenchmark
{

  static
  {
    await20segForEnding(Vertx.vertx()
                             .deployVerticle(new Module()));
  }

  private final static int TIMES = 100;

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringOneVerticle() throws InterruptedException
  {
    await20segForEnding(countStringsOneVerticle.apply(TIMES));
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringMultiVerticle() throws InterruptedException
  {

    await20segForEnding(countStringsMultiVerticles.apply(TIMES));

  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(1)
  public void testCountStringProcesses() throws InterruptedException
  {

    await20segForEnding(countStringsMultiProcesses.apply(TIMES));


  }

}
