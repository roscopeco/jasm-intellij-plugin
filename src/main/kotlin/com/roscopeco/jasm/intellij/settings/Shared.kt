package com.roscopeco.jasm.intellij.settings

object Shared {
    val SAMPLE_CODE = """
        /*
        * Sample block comment for my awesome example class
        */
        public class com/example/JasmClass extends OtherClass implements Interface {
            public static final CONST_STR java/lang/String = "Some String"
            public static final CONST_INT I = 42
            public static final CONST_FLOAT F = -39.5
        
            // The main method
            public static main([java/lang/String)V {
                getstatic java/lang/System.out
                ldc "Hello, World"
                invokevirtual java/io/PrintStream.println(java/lang/String)V
                return
            }
        
            public final addInts(int, int) int {
                iload 1
                iload 2
                iadd
                ireturn
            }
        
            /* Another block comment */
            public useInvokeDynamic()java/lang/String {
                invokedynamic get()java/util/function/Supplier {
                    invokestatic java/lang/invoke/LambdaMetafactory.metafactory(
                        java/lang/invoke/MethodHandles${"$"}Lookup,
                        java/lang/String,
                        java/lang/invoke/MethodType,
                        java/lang/invoke/MethodType,
                        java/lang/invoke/MethodHandle,
                        java/lang/invoke/MethodType,
                    )java/lang/invoke/CallSite
                    [
                        ()java/lang/Object,
                        invokestatic com/example/Bootstraps.lambdaGetImpl()java/lang/String,
                        ()java/lang/String
                    ]
                }
        
                invokeinterface java/util/function/Supplier.get()java/lang/Object
                checkcast java/lang/String
                areturn
            }
        }
    """.trimIndent()
}
