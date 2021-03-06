package org.web3j.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.web3j.generated.HumanStandardToken;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.web3j.generated.HumanStandardToken.ApprovalEventResponse;
import static org.web3j.generated.HumanStandardToken.TransferEventResponse;
import static org.web3j.generated.HumanStandardToken.deploy;

/**
 * Generated HumanStandardToken integration test for all supported scenarios.
 */
public class HumanStandardTokenGeneratedIT extends Scenario {

    @Test
    public void testContract() throws Exception {
        BigInteger aliceQty = BigInteger.valueOf(1_000_000);
        final String aliceAddress = ALICE.getAddress();
        final String bobAddress = BOB.getAddress();

        HumanStandardToken contract = deploy(parity, ALICE,
                GAS_PRICE, GAS_LIMIT,
                BigInteger.ZERO,
                aliceQty, "web3j tokens",
                BigInteger.valueOf(18), "w3j$");

        assertTrue(contract.isValid());

        assertThat(contract.totalSupply(), equalTo(aliceQty));

        assertThat(contract.balanceOf(ALICE.getAddress()),
                equalTo(aliceQty));

        // transfer tokens
        BigInteger transferQuantity = BigInteger.valueOf(100_000);

        TransactionReceipt aliceTransferReceipt = contract.transfer(
                BOB.getAddress(), transferQuantity);

        TransferEventResponse aliceTransferEventValues =
                contract.getTransferEvents(aliceTransferReceipt).get(0);

        assertThat(aliceTransferEventValues._from,
                equalTo(aliceAddress));
        assertThat(aliceTransferEventValues._to,
                equalTo(bobAddress));
        assertThat(aliceTransferEventValues._value,
                equalTo(transferQuantity));

        aliceQty = aliceQty.subtract(transferQuantity);

        BigInteger bobQty = BigInteger.ZERO;
        bobQty = bobQty.add(transferQuantity);

        assertThat(contract.balanceOf(ALICE.getAddress()),
                equalTo(aliceQty));
        assertThat(contract.balanceOf(BOB.getAddress()),
                equalTo(bobQty));

        // set an allowance
        assertThat(contract.allowance(
                aliceAddress, bobAddress),
                equalTo(BigInteger.ZERO));

        transferQuantity = BigInteger.valueOf(50);
        TransactionReceipt approveReceipt = contract.approve(
                BOB.getAddress(), transferQuantity);

        ApprovalEventResponse approvalEventValues =
                contract.getApprovalEvents(approveReceipt).get(0);

        assertThat(approvalEventValues._owner,
                equalTo(aliceAddress));
        assertThat(approvalEventValues._spender,
                equalTo(bobAddress));
        assertThat(approvalEventValues._value,
                equalTo(transferQuantity));

        assertThat(contract.allowance(
                aliceAddress, bobAddress),
                equalTo(transferQuantity));

        // perform a transfer as Bob
        transferQuantity = BigInteger.valueOf(25);

        // Bob requires his own contract instance
        HumanStandardToken bobsContract = HumanStandardToken.load(
                contract.getContractAddress(), parity, BOB, GAS_PRICE, GAS_LIMIT);

        TransactionReceipt bobTransferReceipt = bobsContract.transferFrom(
                aliceAddress,
                bobAddress,
                transferQuantity);

        TransferEventResponse bobTransferEventValues =
                contract.getTransferEvents(bobTransferReceipt).get(0);
        assertThat(bobTransferEventValues._from,
                equalTo(aliceAddress));
        assertThat(bobTransferEventValues._to,
                equalTo(bobAddress));
        assertThat(bobTransferEventValues._value,
                equalTo(transferQuantity));

        aliceQty = aliceQty.subtract(transferQuantity);
        bobQty = bobQty.add(transferQuantity);

        assertThat(contract.balanceOf(aliceAddress),
                equalTo(aliceQty));
        assertThat(contract.balanceOf(bobAddress),
                equalTo(bobQty));
    }

    @Test
    public void transferTokens() throws Exception {
        HumanStandardToken contract = HumanStandardToken.load(
                "0x04886d741D9C20e594d1343F8f6eD4eFE01F18C0", parity, ALICE, GAS_PRICE, GAS_LIMIT);

        contract.transfer(BOB.getAddress(), BigInteger.valueOf(1000));
    }
}
