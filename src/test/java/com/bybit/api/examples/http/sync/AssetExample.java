package com.bybit.api.examples.http.sync;

import com.bybit.api.client.constant.Util;
import com.bybit.api.client.domain.ProductType;
import com.bybit.api.client.domain.account.AccountType;
import com.bybit.api.client.domain.asset.request.*;
import com.bybit.api.client.service.BybitApiClientFactory;
import com.bybit.api.client.BybitApiRestClient;

public class AssetExample {
    public static void main(String[] args) {
        BybitApiClientFactory factory = BybitApiClientFactory.newInstance("8wYkmpLsMg10eNQyPm", "Ouxc34myDnXvei54XsBZgoQzfGxO4bkr2Zsj");
        BybitApiRestClient client = factory.newRestClient();

        // Get Coin Exchange Records
        var coinExchangeRecordsRequest = CoinExchangeRecordsRequest.builder().build();
        var coinExchangeRecords = client.getAssetCoinExchangeRecords(coinExchangeRecordsRequest);
        System.out.println(coinExchangeRecords);

        // Get Delivery Records
        var deliveryRecordsRequest = AssetDeliveryRecordsRequest.builder().category(ProductType.LINEAR).build();
        var deliveryRecords = client.getAssetDeliveryRecords(deliveryRecordsRequest);
        System.out.println(deliveryRecords);

        // Get USDC settlement
        var usdcSettlementRequest = USDCSessionSettlementRequest.builder().category(ProductType.LINEAR).build();
        var usdcSettlement = client.getAssetUSDCSettlementRecords(usdcSettlementRequest);
        System.out.println(usdcSettlement);

        // Get Asset Info
        var assetInfoRequest = AssetInfoRequest.builder().accountType(AccountType.SPOT).build();
        var assetInfo = client.getAssetInfo(assetInfoRequest);
        System.out.println(assetInfo);

        // Get All Coins Balance
        var allCoinsBalanceRequest = AssetCoinsBalanceRequest.builder().accountType(AccountType.UNIFIED).build();
        var allCoinsBalance = client.getAssetAllCoinsBalance(allCoinsBalanceRequest);
        System.out.println(allCoinsBalance);

        // Get Single Coin Balance
        var SingleCoinBalanceRequest = AssetSingleCoinBalanceRequest.builder().accountType(AccountType.UNIFIED).coin("USDT").build();
        var SingleCoinBalanc = client.getAssetSingleCoinBalance(SingleCoinBalanceRequest);
        System.out.println(SingleCoinBalanc);

        // Get Transferable Coin
        var transferableCoins = client.getAssetTransferableCoins(AccountType.UNIFIED,AccountType.FUND);
        System.out.println(transferableCoins);

        // Get Sub Uids
        var assetSubUids = client.getAssetTransferSubUidList();
        System.out.println(assetSubUids);

        // Create Internal Transfer
        var assetInternalTransferRequest = AssetInternalTransferRequest.builder()
                .transferId(Util.generateTransferID())
                .coin("USDT")
                .amount("100")
                .fromAccountType(AccountType.UNIFIED.getAccountTypeValue())
                .toAccountType(AccountType.FUND.getAccountTypeValue())
                .build();
        var internalTransfer = client.createAssetInternalTransfer(assetInternalTransferRequest);
        System.out.println(internalTransfer);

        // Create Universal Transfer
        var assetUniversalTransferRequest = AssetUniversalTransferRequest.builder()
                .transferId(Util.generateTransferID())
                .coin("USDT")
                .amount("100")
                .fromMemberId(1553904)
                .toMemberId(1635703)
                .fromAccountType(AccountType.UNIFIED.getAccountTypeValue())
                .toAccountType(AccountType.FUND.getAccountTypeValue())
                .build();
        var universalTransfer = client.createAssetUniversalTransfer(assetUniversalTransferRequest);
        System.out.println(universalTransfer);

        // Get Allowed Deposit Coin Info
        var allowedDepositCoinRequest = AssetAllowedDepositCoinRequest.builder().build();
        var allowedDepositCoinInfo = client.getAssetAllowedDepositCoinInfo(allowedDepositCoinRequest);
        System.out.println(allowedDepositCoinInfo);

        // Set Deposit Account
        var setDepositAccount = client.setAssetDepositAccount(AccountType.FUND);
        System.out.println(setDepositAccount);

        var depositRecordsRequest = AssetDepositRecordsRequest.builder();
        // Get Deposit Records (on-chain)
        var depositRecordsOnChain = client.getAssetDepositRecords(depositRecordsRequest.build());
        System.out.println(depositRecordsOnChain);

        // Get Sub Deposit Records (on-chain)
        var depositSubRecordsOnChain = client.getAssetSubMembersDepositRecords(depositRecordsRequest.subMemberId("1637192").build());
        System.out.println(depositSubRecordsOnChain);

        // Get Internal Deposit Records (off-chain)
        var internalDepositRecords = client.getAssetInternalDepositRecords(depositRecordsRequest.build());
        System.out.println(internalDepositRecords);

        // Get Internal Transfer Records
        var internalTransferRequest = AssetTransferRecordsRequest.builder().build();
        var internalTransferRecords = client.getAssetInternalTransferRecords(internalTransferRequest);
        System.out.println(internalTransferRecords);

        // Get Universal Transfer Records
        var universalTransferRequest = AssetTransferRecordsRequest.builder().build();
        var universalTransferRecords = client.getAssetUniversalTransferRecords(universalTransferRequest);
        System.out.println(universalTransferRecords);

        // Get Master Deposit Address
        var masterDepositRequest = AssetDepositRequest.builder().coin("USDT").build();
        var masterDeposit = client.getAssetMasterDepositAddress(masterDepositRequest);
        System.out.println(masterDeposit);

        // Get Sub Deposit Address
        var subDepositRequest = AssetDepositRequest.builder().coin("USDT").chainType("TRC20").subMemberId("1637192").build();
        var subDeposit = client.getAssetSubMemberDepositAddress(subDepositRequest);
        System.out.println(subDeposit);

        // Get coin info
        var coinInfo = client.getAssetCoinInfo("ETH");
        System.out.println(coinInfo);

        // Get Withdrawable Amount
        var withdrawalAmount = client.getAssetWithdrawalAmount("USDT");
        System.out.println(withdrawalAmount);

        // Get Withdraw Records
        var assetWithdrawRecordsRequest = AssetWithdrawRecordsRequest.builder().build();
        var withdrawRecords = client.getAssetWithdrawalRecords(assetWithdrawRecordsRequest);
        System.out.println(withdrawRecords);

        // Withdraw
        var assetWithdrawRequest = AssetWithdrawRequest.builder()
                .coin("USDT")
                .chain("ETH")
                .address("0xSampleWalletAddress")
                .amount("100")
                .timestamp(Util.generateTimestamp())
                .build();
        var assetWithdraw = client.createAssetWithdraw(assetWithdrawRequest);
        System.out.println(assetWithdraw);

        // Cancel Withdraw
        var cancelWithdraw = client.cancelAssetWithdraw("xxxxx");
        System.out.println(cancelWithdraw);
    }
}
