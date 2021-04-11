# Serialization of Java doubles by integer splits

It is often necessary to store floating point numbers that were derived computationally in a non-binary format without loss of information, e.g. in
(JSON) text files. The Java (Double class)[https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html] provides
a way to obtain an exact presentation of a `double` value using the [toHexString method](https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html#toHexString(double)).
Java can also deserialize the produced hex-strings to a `double` value with the [valueOf method](https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html#valueOf(java.lang.String)).
The [R](https://www.r-project.org/) base package function `as.double` can also parse hex-strings as produced by Java's `toHexString` method.

```R
# R version 4.0.5 (2021-03-31) -- "Shake and Throw"
#
# Some examples of R's as.double parsing hex-strings produced by Java's Double.toHexString
#
> as.double("0x1.b1f64064f69bp-1")
[1] 0.8475819
> as.double("-0x1.82baa2799d15p-3")
[1] -0.1888325
> as.double("0x1.ac62e23c42p-12")
[1] 0.000408541
```

In [Python](https://www.python.org/), the class method [float.fromhex](https://docs.python.org/3/library/stdtypes.html#float.fromhex) can
parse hex-strings.

```Python
# Python 3.8.5 (default, Jan 27 2021, 15:41:15) 
# [GCC 9.3.0] on linux
#
# Some examples of Python's float.fromhex parsing hex-strings produced by Java's Double.toHexString
#
>>> float.fromhex("0x1.b1f64064f69bp-1")
0.8475818751569957
>>> float.fromhex("-0x1.82baa2799d15p-3")
-0.18883253987918147
>>> float.fromhex("0x1.ac62e23c42p-12")
0.00040854097802922595
```

As another programming language example, [C++](https://isocpp.org/) provides the standard library function `std::stod` that converts
hex-strings to `double`s.

```Cpp
#include <iostream>
#include <string>
int main() {
  
    std::string ha = "0x1.b1f64064f69bp-1",
                hb = "-0x1.82baa2799d15p-3",
                hc = "0x1.ac62e23c42p-12";
    
    std::cout << ha << " " << std::stod(ha) << std::endl
              << hb << " " << std::stod(hb) << std::endl
              << hc << " " << std::stod(hc) << std::endl;
}
//
// Output:
// 0x1.b1f64064f69bp-1 0.847582
// -0x1.82baa2799d15p-3 -0.188833
// 0x1.ac62e23c42p-12 0.000408541
```

Operating with hex-string presentation of floating point values is well supported by several widely used programming languages.
For cases in which string serialization is not an option but conversion to other number types can work, an example solution may be found in the Java file of this
repository. The Java class provides functions to split a `double` value into `int`s as well as to restore the `double` from the result of splitting.
Use cases for conversion between `long` and `double` types are also already covered by the Java methods [doubleToLongBits](https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html#doubleToLongBits(double)), [doubleToRawLongBits](https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html#doubleToRawLongBits(double)) and [longBitsToDouble](https://docs.oracle.com/javase/10/docs/api/java/lang/Double.html#longBitsToDouble(long)).

