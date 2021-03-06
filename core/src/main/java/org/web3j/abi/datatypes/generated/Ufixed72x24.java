package org.web3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.web3j.abi.datatypes.Ufixed;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Ufixed72x24 extends Ufixed {
    public static final Ufixed72x24 DEFAULT = new Ufixed72x24(BigInteger.ZERO);

    public Ufixed72x24(BigInteger value) {
        super(72, 24, value);
    }

    public Ufixed72x24(int mBitSize, int nBitSize, BigInteger m, BigInteger n) {
        super(72, 24, m, n);
    }
}
