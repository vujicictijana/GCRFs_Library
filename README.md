GCRFs Library
=====================
Java library for Gaussian Conditional Random Fields (GCRF) supports training and testing of GCRF methods on random generated datasets and real datasets.

Library supports two GCRF methods:
- Standard GCRF (GCRF) - structured regression model that incorporates the outputs of unstructured predictors (based on the given attributes values) and the correlation between output variables in order to achieve a higher prediction accuracy
- Directed GCRF (DirGCRF) -  method extends the GCRF method by considering asymmetric similarity

Use
=====================

Download <a href="https://github.com/vujicictijana/Library/blob/master/gcrfs.jar?raw=true">gcrfs.jar</a> and provide a reference to GCRFs Library jar file in your project.

Also, provide references to following jars:
- ojalgo-40.0.0.jar
- ojalgo-biz-40.0.0.jar
- ojalgo-ext-40.0.0.jar
- ujmp-core-0.3.0.jar

In Eclipse: right click on the project, then Build Path > Configure Build Path > Add JARs or Add External JARs, and choose jars
In NetBeans: right click on the project, then Properties > Libraries > Add JAR/Folder, and choose jars

See the <a href="http://htmlpreview.github.io/?https://github.com/vujicictijana/Library/blob/master/Library/api/index.html">GCRFs Library API documentation</a> for more.

Quickstart tutorial
=====================

<h2>Dataset </h2>

Required data:
- s - similarity matrix (double[][])
- r - outputs of unstructured predictor (double[])
- y - expected outputs (double[])
   
<b> Dataset from .txt files: </b>

```java
double[][] s = GraphReader.readGraph("data/s.txt", y.length);
double[] r = ArrayReader.readArray("data/r.txt");
double[] y = ArrayReader.readArray("data/y.txt");
Dataset dataset = new Dataset(s, r, y);
```

Required format for .txt file with similarity matrix:
- Format: from node, to node, weight
- Each edge should be in a separate line.
- Nodes are represented by ordinal numbers (numbers, from 1 to the number of nodes).

Required format for .txt files with outputs of unstructured predictor and expected outputs:
- Each output should be in a separate line.
- Output should be number.
- Order of outputs should be consistent with ordinal numbers of nodes in the file with similarity matrix.

<b> Random generated dataset: </b>

```java
double[][] s = GraphGenerator.generateDirectedGraph(200);
double[] r = ArrayGenerator.generateArray(200, 5);
```

The generated S and R should be used to calculate the actual value of output y for each node, in accordance to the method calculation rules, so y method from specific Calculations class should be used:

```java
CalculationsDirGCRF c = new CalculationsDirGCRF(s, r);
double[] y = c.y(1, 2, 0.05);
Dataset dataset = new Dataset(s, r, y);
```

<h2>Method</h2>

```java
// parameters for learning algorithm
double alpha = 1;
double beta = 1;
double lr = 0.0001;
int maxIter = 100;
Parameters p = new Parameters(alpha, beta, maxIter, lr);
		
DirGCRF g1 = new DirGCRF(p, dataset);
		
double[] predictedOutputs = g1.predictOutputs();
for (int i = 0; i < predictedOutputs.length; i++) {
	System.out.println(predictedOutputs[i]);
}
		
System.out.println("R^2: " + g1.rSquared());
```
