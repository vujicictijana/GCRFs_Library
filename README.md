GCRFs Library
=====================
Java library for Gaussian Conditional Random Fields (GCRF) supports training and testing of GCRF methods on random generated datasets and real datasets.

<ul>
    <li> <a href="#m"> Methods</a></li>
    <li> <a href="#u"> Use</a></li>
    <li> <a href="#q"> Quickstart tutorial</a></li>
   <li> <a href="#c"> Class diagram</a></li>
    <li> <a href="#e"> How to extend?</a></li>
</ul>
	    
<a id="m" class="anchor" aria-hidden="true" href="#m"></a>
Methods
=====================
Library supports two GCRF methods:
- Standard GCRF (GCRF) - structured regression model that incorporates the outputs of unstructured predictors (based on the given attributes values) and the correlation between output variables in order to achieve a higher prediction accuracy
- Directed GCRF (DirGCRF) -  method extends the GCRF method by considering asymmetric similarity

<a id="u" class="anchor" aria-hidden="true" href="#u"></a>

Use
=====================

Download <a href="https://github.com/vujicictijana/Library/blob/master/gcrfs.jar?raw=true">gcrfs.jar</a> and provide a reference to GCRFs Library jar file in your project.

Also, provide references to following jar:
- <a href="https://github.com/vujicictijana/GCRFs_Library/blob/master/Library/resources/ojalgo-40.0.0.jar?raw=true">ojalgo-40.0.0.jar</a> 

In Eclipse: right click on the project, then Build Path > Configure Build Path > Add JARs or Add External JARs, and choose jars
In NetBeans: right click on the project, then Properties > Libraries > Add JAR/Folder, and choose jars

See the <a href="http://htmlpreview.github.io/?https://github.com/vujicictijana/Library/blob/master/Library/api/index.html">GCRFs Library API documentation</a> for more.

<a id="q" class="anchor" aria-hidden="true" href="#q"></a>
Quickstart tutorial
=====================

<h2>Dataset </h2>

Required data:
- s - similarity matrix (double[][])
- r - outputs of unstructured predictor (double[])
- y - expected outputs (double[])
   
<b> Dataset from .txt files: </b>

```java
double[] r = ArrayReader.readArray("data/r.txt");
double[] y = ArrayReader.readArray("data/y.txt");
double[][] s = GraphReader.readGraph("data/s.txt", y.length);
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

Use class of specific method that integrates learning algorithm and calculation rules:

```java
// parameters for learning algorithm
double alpha = 1;
double beta = 1;
double lr = 0.0001;
int maxIter = 100;
Parameters p = new Parameters(alpha, beta, maxIter, lr);
		
DirGCRF method = new DirGCRF(p, dataset);
		
double[] predictedOutputs = method.predictOutputs();
for (int i = 0; i < predictedOutputs.length; i++) {
	System.out.println(predictedOutputs[i]);
}
		
System.out.println("R^2 Train: " + method.rSquared());
```

The new method is trained and it should be tested:

```java
double[] yTest = ArrayReader.readArray("data/yTest.txt");
double[] rTest = ArrayReader.readArray("data/rTest.txt");
double[][] sTest = GraphReader.readGraph("data/sTest.txt", y.length);

double[] predictedOutputsTest = g1.predictOutputsForTest(sTest, rTest);
for (int i = 0; i < predictedOutputsTest.length; i++) {
	System.out.println(predictedOutputsTest[i]);
}
System.out.println("R^2 Test: " + g1.rSquaredForTest(predictedOutputsTest, yTest));
```

Another way is to Use Basic class and specify learning algorithm and calculation rules:


```java
// parameters for learning algorithm
double alpha = 1;
double beta = 1;
double lr = 0.0001;
int maxIter = 100;
Parameters p = new Parameters(alpha, beta, maxIter, lr);
		
// calculation rules
CalculationsDirGCRF c = new CalculationsDirGCRF(s, r);
		
// learning algorithm
GradientAscent g = new GradientAscent(p, c, y, false, null);
		
Basic method = new Basic(g, c, dataset);
		
double[] predictedOutputs = method.predictOutputs();
for (int i = 0; i < predictedOutputs.length; i++) {
	System.out.println(predictedOutputs[i]);
}
		
System.out.println("R^2 Train: " + method.rSquared());
```

<a id="c" class="anchor" aria-hidden="true" href="#c"></a>
Class diagram
=====================
Basic classes presented at the class diagram provide a general structure and logic components that are common for
GCRF based methods.

Full list of packages, classes and methods can be found in GCRFs Library <a href="http://htmlpreview.github.io/?https://github.com/vujicictijana/Library/blob/master/Library/api/index.html">GCRFs Library API documentation</a>.

<img src="/diagram.PNG" alt="diagram" style="max-width:100%;">


<a id="e" class="anchor" aria-hidden="true" href="#e"></a>
How to extend?
=====================

Library can be easily extended with new GCRF based methods.

Difference between various GCRF methods is reflected in the calculation rules, so first step is to
extend the CalculationsGCRF class and to override methods that implement specific calculations.

Second step is to pass object of the new class for calculations to the Basic class, or to override it.

Also, it is possible add new learning algorithm by implementing LearningRule interface.


