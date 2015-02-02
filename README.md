# callstackcoverage

Java agent based call stack coverage recorder.

Some quick magic tips:

* Make sure that you export the java agent jar when you update things. Make sure to use the included eclipse jar export wizard configuration to make sure that the jar manifest gets built properly.
* When running a program and using the java agent, be sure to use the flag -javaagent:/path/to/coverageagent.jar AND include on the classpath the asm-all-5.0.3 jar file.
* If you instrument code to make it call some helper function, be sure that your helper function doesn't get instrumented also or else you'll end up in a recursive loop.

Helpful links:

* ASM tutorial <http://asm.ow2.org/current/asm-transformations.pdf> 