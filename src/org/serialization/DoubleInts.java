/**
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.serialization;

import java.util.Random;

/**
 * Example code to split double values into integers and to reconstruct double values from
 * resulting splits.
 *
 * @author pstegma
 */
public class DoubleInts {
	
	/**
	 * Splits double into two int values.
	 * 
	 * @param  double value to split
	 * @return array of ints with 32 least significant (<i>right half</i>) and 32 most significant (<i>left half</i>) bits of the double
	 */
	public static int[] split(double value) {
	    System.out.println("Splitting " + value);
	    long lvalue  = Double.doubleToRawLongBits(value);
            long intMask = 0xFFFFFFFFFFFFFFFFL >>> 32;
	    long least = lvalue & intMask;
	    long most = (lvalue >>> 32) & intMask;
            return new int[] {0x0 | (int)least, 
	                      0x0 | (int)most};
	}
	
	/**
	 * Joins two ints obtained by {@link #split(double)} into the original double value.
	 * 
	 * @param  array of ints with 32 least significant and 32 most significant bits of the double
	 * @return double value obtained by joining ints
	 */
	public static double ligate(int[] splits) {
	    System.out.println("Constructing double from " + splits[0] + " and " + splits[1]);
	    long most  = (long)splits[1] << 32;
	    long least = (long)splits[0];
	    least = least & (0xFFFFFFFFFFFFFFFFL >>> 32);
	    return Double.longBitsToDouble(most | least);
	}
	
	
	/**
	 * Tests {@link #split(double)} and {@link #ligate(int[])} with randomly sampled
	 * double values.
	 */
    public static void main() {
        double testValue;
    	Random rng = new Random();
        // Other number ranges can be tested by modifying rmin and rmax
        double rmin = -1.0;
        double rmax = 1.0;
        boolean correct;
        int ntests = 100000;
        for (int i = 1; i <= ntests; ++i) {
            testValue = rmin + (rmax - rmin) * rng.nextDouble();
            System.out.println("Test (" + i + "): " + testValue + " " + Double.toHexString(testValue));
            int[] splits = DoubleInts.split(testValue);
            double ligat = DoubleInts.ligate(splits);
            correct = Double.toHexString(testValue).contentEquals(Double.toHexString(ligat));
            if (!correct) {
                throw new RuntimeException("Result was not correct!");
            }
        }
        testValue = 0.0;
        System.out.println("Test (" + (ntests+1) + "): " + testValue + " " + Double.toHexString(testValue));
        int[] splits = DoubleInts.split(testValue);
        double ligat = DoubleInts.ligate(splits);
        if (!Double.toHexString(testValue).contentEquals(Double.toHexString(ligat))) {
           throw new RuntimeException("Result was not correct!");
        }
    }
}
