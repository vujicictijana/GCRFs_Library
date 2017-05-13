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

Required data:
- s - similarity matrix (double[][])
- r - outputs of unstructured predictor (double[])
- y - expected outputs (double[])
   
Read dataset from .txt files:

```double[][] s = GraphReader.readGraph("data/s.txt", y.length);
double[] r = ArrayReader.readArray("data/r.txt");
double[] y = ArrayReader.readArray("data/y.txt");
Dataset d = new Dataset(s, r, y);
```

Or generate dataset:
```double[][] s = GraphGenerator.generateDirectedGraph(200);
double[] r = ArrayGenerator.generateArray(200, 5);
CalculationsDirGCRF c = new CalculationsDirGCRF(s, r);
double[] y = c.y(1, 2, 0.05);
Dataset d = new Dataset(s, r, y);
```
